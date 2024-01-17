package tkcy.simpleaddon.api.metatileentities;

import java.util.function.Function;
import java.util.stream.IntStream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import gregtech.api.metatileentity.MetaTileEntity;

public interface RepetitiveSide {

    int getMinSideLength();

    int getMaxSideLength();

    IBlockState getSideBlockBlockState();

    MetaTileEntity getMetatileEntity();

    int getParallelNumber();

    Function<Integer, BlockPos> getRepetitiveDirection();

    default boolean stopMatchSideBlock(int pos) {
        return !this.getMetatileEntity().getWorld()
                .getBlockState(this.getRepetitiveDirection().apply(pos))
                .equals(this.getSideBlockBlockState());
    }

    default int getHeight() {
        return IntStream.range(this.getMinSideLength(), this.getMaxSideLength())
                .boxed()
                .filter(this::stopMatchSideBlock)
                .findFirst()
                .orElseGet(this::getMaxSideLength);
    }
}
