package tkcy.tktech.common.metatileentities.primitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.machines.IRightClickItemTransfer;
import tkcy.tktech.api.machines.IUnificationToolMachine;
import tkcy.tktech.api.machines.ToolLogicMetaTileEntity;
import tkcy.tktech.api.recipes.logic.IHideEnergyRecipeLogic;
import tkcy.tktech.api.recipes.logic.IRecipeLogicContainer;
import tkcy.tktech.api.recipes.logic.IToolRecipeLogic;
import tkcy.tktech.api.recipes.logic.OnBlockRecipeLogic;
import tkcy.tktech.api.recipes.logic.impl.InWorldRecipeLogic;
import tkcy.tktech.api.recipes.logic.impl.RecipeLogicsContainer;
import tkcy.tktech.api.recipes.logic.impl.ToolLogic;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.modules.toolmodule.ToolsModule;

public class AnvilMetatileEntity extends ToolLogicMetaTileEntity
                                 implements IUnificationToolMachine, IRightClickItemTransfer {

    public AnvilMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected List<ToolsModule.GtTool> getWorkingGtTool() {
        return Collections.singletonList(ToolsModule.GtTool.HARD_HAMMER);
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
        return new AnvilMetatileEntity(metaTileEntityId);
    }

    @Override
    public void onAnyToolClick(ToolsModule.GtTool tool, boolean isPlayerSneaking) {}

    @Override
    public boolean onHardHammerClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     CuboidRayTraceResult hitResult) {
        if (!playerIn.isSneaking()) return false;
        getLogic().runToolRecipeLogic(ToolsModule.GtTool.HARD_HAMMER);
        return true;
    }

    @Override
    public @NotNull List<OrePrefix> getPartsOrePrefixes() {
        return new ArrayList<>() {

            {
                add(OrePrefix.plate);
                add(OrePrefix.plateDouble);
            }
        };
    }

    @Override
    protected OnBlockRecipeLogic initRecipeLogic() {
        return new Logic(this, null, TkTechRecipeMaps.ANVIL_RECIPES);
    }

    @Override
    protected void addExtraTooltip(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tktech.tool_machine.parts.tooltip", addPartsOrePrefixInformation()));
    }

    @Override
    public boolean doesTransferHandStackToInput() {
        return true;
    }

    @Override
    public boolean doesTransferInputToPlayer() {
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
            InWorldRecipeLogic inWorldRecipeLogic = new InWorldRecipeLogic.Builder(this)
                    .doesSpawnOutputItems()
                    .build();
            return new RecipeLogicsContainer(this, new ToolLogic(this), inWorldRecipeLogic);
        }
    }
}
