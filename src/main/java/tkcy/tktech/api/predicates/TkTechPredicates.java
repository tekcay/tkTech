package tkcy.tktech.api.predicates;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.*;
import static tkcy.tktech.common.metatileentities.TkTechMetaTileEntities.*;

import net.minecraft.block.state.IBlockState;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TkTechPredicates {

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

    public static TraceabilityPredicate isAir(String matchContext) {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (air().test(blockWorldState)) {
                blockWorldState.getMatchContext().increment(matchContext, 1);
                return true;
            } else
                return false;
        });
    }

    public static TraceabilityPredicate heightIndicatorPredicate(TraceabilityPredicate predicate, String matchContext,
                                                                 int incrementValue) {
        return new TraceabilityPredicate((blockWorldState) -> {
            if (predicate.test(blockWorldState)) {
                blockWorldState.getMatchContext().increment(matchContext, incrementValue);
                return true;
            } else
                return false;
        });
    }

    public <T extends MetaTileEntity> TraceabilityPredicate metaTileEntityPredicate(TraceabilityPredicate traceabilityPredicate,
                                                                                    T metaTileEntityToDetect) {
        return traceabilityPredicate.or(metaTileEntities(metaTileEntityToDetect));
    }

    public <T extends MetaTileEntity> TraceabilityPredicate metaTileEntityPredicate(T metaTileEntityToDetect) {
        return metaTileEntities(metaTileEntityToDetect);
    }

    public TraceabilityPredicate iBlockStatePredicate(IBlockState blockToPredicate) {
        return states(blockToPredicate);
    }
}
