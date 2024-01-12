package tkcy.simpleaddon.api.predicates;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.states;

import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

public class Predicates {

    public static TraceabilityPredicate brick() {
        return states(MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS));
    }
}
