package tkcy.tktech.common.metatileentities.primitive;

import static tkcy.tktech.api.utils.GuiJeiUtils.FONT_HEIGHT;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.machines.IRightClickItemTransfer;
import tkcy.tktech.api.machines.ToolLogicMetaTileEntity;
import tkcy.tktech.api.recipes.logic.IToolRecipeLogic;
import tkcy.tktech.api.recipes.logic.OnBlockRecipeLogic;
import tkcy.tktech.api.recipes.logic.containers.IRecipeLogicContainer;
import tkcy.tktech.api.recipes.logic.containers.RecipeLogicsContainer;
import tkcy.tktech.api.recipes.logic.containers.ToolLogic;
import tkcy.tktech.api.recipes.logic.markers.IHideEnergyRecipeLogic;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.modules.toolmodule.ToolsModule;

public class MTeBasicElectronic extends ToolLogicMetaTileEntity
                                implements IRightClickItemTransfer {

    public MTeBasicElectronic(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected List<ToolsModule.GtTool> getWorkingGtTool() {
        return Collections.singletonList(ToolsModule.GtTool.SOLDERING_IRON);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 9, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new GTItemStackHandler(this, 1);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
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
        return new MTeBasicElectronic(this.metaTileEntityId);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return true;
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return getRecipeMap().getRecipeMapUI()
                .createUITemplate(getLogic()::getProgressPercent, importItems, exportItems, importFluids, exportFluids,
                        FONT_HEIGHT)
                .widget(new LabelWidget(5, 5, getMetaFullName()))
                .widget(new ClickButtonWidget(30, 60, 30, 20, "T",
                        clickData -> getLogic().runToolRecipeLogic(ToolsModule.GtTool.SOLDERING_IRON, EnumFacing.UP)))
                .bindPlayerInventory(entityPlayer.inventory, 92);
    }

    @Override
    protected OnBlockRecipeLogic initRecipeLogic() {
        return new Logic(this, null, TkTechRecipeMaps.BASIC_ELECTRONIC_RECIPES);
    }

    @Override
    public boolean doesTransferOutputToPlayer() {
        return true;
    }

    @Override
    public boolean showSpecialRightClickTooltips() {
        return true;
    }

    private static class Logic extends OnBlockRecipeLogic implements IToolRecipeLogic, IHideEnergyRecipeLogic {

        public Logic(MetaTileEntity tileEntity, Supplier<IEnergyContainer> energyContainer,
                     RecipeMap<?>... recipeMaps) {
            super(tileEntity, energyContainer, recipeMaps);
        }

        @Override
        public boolean consumesEnergy() {
            return false;
        }

        @Override
        public @NotNull IRecipeLogicContainer setRecipeLogicContainer() {
            return new RecipeLogicsContainer(this, new ToolLogic(this));
        }
    }
}
