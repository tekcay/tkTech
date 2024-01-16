package tkcy.simpleaddon.api.metatileentities;

import java.util.stream.IntStream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

import gregtech.api.metatileentity.MetaTileEntity;

public interface HeightMetaTileEntity {

    int getMinHeight();

    int getMaxHeight();

    IBlockState getBlockStateToTest();

    MetaTileEntity getMetatileEntity();

    void setParallelNumber();

    default boolean isNext(int pos, IBlockState blockState) {
        return getMetatileEntity().getWorld().getBlockState(BlockPos.ORIGIN.up(pos)).equals(blockState);
    }

    default int getHeight() {
        return IntStream.range(getMinHeight(), getMaxHeight())
                .boxed()
                .filter(height -> !isNext(height, getBlockStateToTest()))
                .findFirst()
                .orElseGet(this::getMaxHeight);
    }
}
