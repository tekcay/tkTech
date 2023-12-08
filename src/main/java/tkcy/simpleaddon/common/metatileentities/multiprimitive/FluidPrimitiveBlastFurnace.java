package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.MetaTileEntityPrimitiveBlastFurnace;

import tkcy.simpleaddon.api.machines.BrickMultiblock;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class FluidPrimitiveBlastFurnace extends MetaTileEntityPrimitiveBlastFurnace implements BrickMultiblock {

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.recipeMapWorkable = new PrimitiveRecipeLogic(this, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX", "XXX")
                .aisle("XXX", "XXX", "X#X", "X#X")
                .aisle("XXX", "XYX", "XXX", "XXX")
                .where('X', states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS))
                        .or(autoAbilities()))
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }
}
