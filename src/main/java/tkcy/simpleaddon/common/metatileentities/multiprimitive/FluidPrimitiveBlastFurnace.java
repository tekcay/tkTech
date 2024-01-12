package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbility;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class FluidPrimitiveBlastFurnace extends RecipeMapPrimitiveMultiblockController {

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
        // this.recipeMapWorkable = new NoEnergyLogic(this, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    @Override
    public FluidPrimitiveBlastFurnace createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    public static TraceabilityPredicate brick() {
        return states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS));
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AAA", "XXX", "XXX", "BBB")
                .aisle("AAA", "XXX", "X#X", "BBB")
                .aisle("AAA", "XYX", "XXX", "BBB")
                .where('A', brick().or(abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS)
                        .setMaxGlobalLimited(2)
                        .setPreviewCount(1)))
                .where('B', brick().or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS)
                        .setMaxGlobalLimited(2)
                        .setPreviewCount(1)))
                .where('X', brick())
                // .or(abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS).setPreviewCount(1).setMaxGlobalLimited(2))
                // .or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS).setPreviewCount(2).setMaxGlobalLimited(2)))
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

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
