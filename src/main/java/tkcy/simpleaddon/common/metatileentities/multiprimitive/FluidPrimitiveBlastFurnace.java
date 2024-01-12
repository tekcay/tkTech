package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import static tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbility.*;
import static tkcy.simpleaddon.api.predicates.Predicates.brick;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import tkcy.simpleaddon.api.logic.NoEnergyLogic;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class FluidPrimitiveBlastFurnace extends RecipeMapMultiblockController {

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
        this.recipeMapWorkable = new NoEnergyLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AAA", "XXX", "XXX", "BBB")
                .aisle("AAA", "XXX", "X#X", "BCB")
                .aisle("AAA", "XYX", "XXX", "BBB")
                .where('A', brick().or(BRICK_FLUIDS_OUTPUT_PREDICATE
                        .setMaxGlobalLimited(2)
                        .setPreviewCount(1)))
                .where('B', brick().or(BRICK_ITEMS_INPUT_PREDICATE
                        .setMaxGlobalLimited(2)
                        .setPreviewCount(1)))
                .where('C', brick().or(BRICK_FLUIDS_INPUT_PREDICATE))
                .where('X', brick())
                // .or(abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS).setPreviewCount(1).setMaxGlobalLimited(2))
                // .or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS).setPreviewCount(2).setMaxGlobalLimited(2)))
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    /*
     * @Override
     * public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
     * super.renderMetaTileEntity(renderState, translation, pipeline);
     * getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
     * recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
     * }
     * 
     */

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.PRIMITIVE_BRICKS;
    }
}
