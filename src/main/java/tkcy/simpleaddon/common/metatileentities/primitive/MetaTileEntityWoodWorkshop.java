package tkcy.simpleaddon.common.metatileentities.primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import lombok.Setter;
import tkcy.simpleaddon.api.machines.*;
import tkcy.simpleaddon.api.recipes.logic.IInWorldRecipeLogic;
import tkcy.simpleaddon.api.recipes.logic.IToolRecipeLogic;
import tkcy.simpleaddon.api.recipes.logic.OnBlockRecipeLogic;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public class MetaTileEntityWoodWorkshop extends ToolLogicMetaTileEntity
                                        implements IUnificationToolMachine, IOnAnyToolClick {

    private final Logic logic;

    public MetaTileEntityWoodWorkshop(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.WOOD_WORKSHOP_RECIPES, true);
        this.logic = new Logic(this, null, TKCYSARecipeMaps.WOOD_WORKSHOP_RECIPES);
    }

    @Override
    protected ToolsModule.GtTool getWorkingGtTool() {
        return ToolsModule.GtTool.AXE;
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 2);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 2, this, false);
    }

    @Override
    protected SimpleOverlayRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        IVertexOperation[] colouredPipeline = ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
        getBaseRenderer().render(renderState, translation, colouredPipeline);
        ColourMultiplier multiplier = new ColourMultiplier(
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
        colouredPipeline = ArrayUtils.add(pipeline, multiplier);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.SOUTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.NORTH);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.EAST);
        Textures.CRAFTING_TABLE.renderOriented(renderState, translation, pipeline, EnumFacing.WEST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityWoodWorkshop(metaTileEntityId);
    }

    @Override
    public @NotNull List<OrePrefix> getPartsOrePrefixes() {
        return new ArrayList<>() {

            {
                add(TKCYSAOrePrefix.strippedWood);
                add(OrePrefix.plank);
            }
        };
    }

    @Override
    public boolean onAnyToolClick(ToolsModule.GtTool tool, boolean isPlayerSneaking) {
        if (!isPlayerSneaking) return false;
        this.logic.runToolRecipeLogic(tool);
        return true;
    }

    @Override
    public void onAnyToolClickTooltip(List<String> tooltips) {
        tooltips.add(I18n.format("tkcysa.metatileentity.on_any_tool_click.sneak.invalidate.tooltip"));
    }

    @Override
    public boolean showAnyToolClickTooltip() {
        return true;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return true;
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.PRIMITIVE_BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .slot(this.importItems, 0, 60, 30, GuiTextures.PRIMITIVE_SLOT)
                .slot(this.importItems, 1, 30, 30, GuiTextures.PRIMITIVE_SLOT)
                .widget(new ClickButtonWidget(30, 60, 30, 20, "T",
                        clickData -> logic.runToolRecipeLogic(ToolsModule.GtTool.AXE)))
                .widget(new ClickButtonWidget(90, 60, 30, 20, "E", clickData -> entityPlayer.jump()))
                .progressBar(this.logic::getProgressPercent, 100, 30, 18, 18, GuiTextures.PROGRESS_BAR_BENDING,
                        ProgressWidget.MoveType.HORIZONTAL, this.recipeMap)
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    protected void addExtraTooltip(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tkcya.tool_machine.parts.tooltip", addPartsOrePrefixInformation()));
    }

    @Getter
    @Setter
    private class Logic extends OnBlockRecipeLogic implements IInWorldRecipeLogic, IToolRecipeLogic {

        private int toolUses;
        private ToolsModule.GtTool currentTool;
        private ToolsModule.GtTool recipeTool;
        private ItemStack inputRecipeInWorldBlockStack;
        private ItemStack outputRecipeInWorldBlockStack;

        public Logic(MetaTileEntity tileEntity, Supplier<IEnergyContainer> energyContainer,
                     RecipeMap<?>... recipeMaps) {
            super(tileEntity, energyContainer, recipeMaps);
        }

        @Override
        public boolean doesSpawnOutputItems() {
            return true;
        }

        @Override
        public boolean doesRemoveBlock() {
            return true;
        }

        @Nullable
        public BlockPos getInputBlockPos() {
            return getPos().up();
        }

        @Nullable
        public BlockPos getOutputBlockPos() {
            return getInputBlockPos();
        }

        @Override
        public boolean consumesEnergy() {
            return false;
        }
    }
}
