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

public class PrimitiveRoastingOven extends RecipeMapPrimitiveMultiblockController {

    public PrimitiveRoastingOven(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.PRIMITIVE_ROASTING);
        // this.recipeMapWorkable = new NoEnergyLogic(this, TKCYSARecipeMaps.PRIMITIVE_ROASTING);
    }

    @Override
    public PrimitiveRoastingOven createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new PrimitiveRoastingOven(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "-X-")
                .aisle("XXX", "X#X", "-X-")
                .aisle("XXX", "XYX", "-X-")
                .where('X',
                        states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.COKE_BRICKS))
                                .or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS).setExactLimit(3).setPreviewCount(3)))
                .where('#', air())
                .where('-', any())
                .where('Y', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.COKE_OVEN_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.COKE_BRICKS;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
                recipeMapWorkable.isActive(), recipeMapWorkable.isWorkingEnabled());
    }

    @Override
    public TraceabilityPredicate autoAbilities(boolean checkMaintenance, boolean checkMuffler) {
        TraceabilityPredicate predicate = new TraceabilityPredicate();
        predicate = predicate
                .or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS).setMaxGlobalLimited(2))
                .or(abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS).setMaxGlobalLimited(2));
        return predicate;
    }
}
