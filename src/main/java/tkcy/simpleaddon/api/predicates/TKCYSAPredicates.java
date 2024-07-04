package tkcy.simpleaddon.api.predicates;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.metaTileEntities;
import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.states;
import static tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities.*;

import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TKCYSAPredicates {

    public static TraceabilityPredicate fireBrick() {
        return states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS));
    }

    public static TraceabilityPredicate cokeBrick() {
        return states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.COKE_BRICKS));
    }

    public static TraceabilityPredicate brickFluidHatch(boolean isExport) {
        return isExport ? metaTileEntities(BRICK_FLUID_HATCH[1]) : metaTileEntities(BRICK_FLUID_HATCH[0]);
    }

    public static TraceabilityPredicate brickFluidHatch(boolean isExport, int amount) {
        return brickFluidHatch(isExport)
                .setExactLimit(amount)
                .setPreviewCount(amount);
    }

    public static TraceabilityPredicate brickItemBus(boolean isExport) {
        return isExport ? metaTileEntities(BRICK_ITEM_BUS[1]) : metaTileEntities(BRICK_ITEM_BUS[0]);
    }

    public static TraceabilityPredicate brickItemBus(boolean isExport, int amount) {
        return brickItemBus(isExport)
                .setExactLimit(amount)
                .setPreviewCount(amount);
    }
}
