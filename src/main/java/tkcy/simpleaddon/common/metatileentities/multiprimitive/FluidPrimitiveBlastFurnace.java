package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

public class FluidPrimitiveBlastFurnace extends RecipeMapMultiblockController {

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
        this.recipeMapWorkable = new PrimitiveRecipeLogic(this, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX", "XXB")
                .aisle("XXX", "XXX", "X#X", "XXA")
                .aisle("XXX", "XYX", "XXX", "XXX")
                .where('A', metaTileEntities(TKCYSAMetaTileEntities.BRICK_FLUID_HATCH[1])) // abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS))
                .where('B', metaTileEntities(TKCYSAMetaTileEntities.BRICK_ITEM_BUS[0]))
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS)))
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

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return null;
    }

    /*
     * @Override
     * public TraceabilityPredicate autoAbilities(boolean checkMaintenance, boolean checkMuffler) {
     * TraceabilityPredicate predicate = new TraceabilityPredicate();
     * predicate = predicate
     * .or(abilities(TKCYSAMultiblockAbility.BRICK_ITEMS).setPreviewCount(2).setMinGlobalLimited(2))
     * .or(abilities(TKCYSAMultiblockAbility.BRICK_FLUIDS).setPreviewCount(1).setExactLimit(2));
     * return predicate;
     * }
     * 
     */
}
