package tkcy.tktech.api.metatileentities;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

public interface RepetitiveSide {

    int getMinSideLength();

    int getMaxSideLength();

    IBlockState getSideBlockBlockState();

    MetaTileEntity getMetatileEntity();

    /**
     * The amount of extra layers needed to increase the maxParallelValue.
     * Set to 1 to increase the maxParallel value per additional layer
     */
    int getLayersPerParallel();

    default int getParallelNumber() {
        return getHeight() / getLayersPerParallel();
    }

    default void addParallelTooltip(@NotNull List<String> tooltip) {
        tooltip.add(I18n.format("tktech.machine.parallel.tooltip", getLayersPerParallel()));
    }

    /**
     * Example :
     * <br>
     * <br>
     * To check from below to top : {@code pos -> this.getPos().up(pos)}
     * <br>
     * <br>
     */
    Function<Integer, BlockPos> getRepetitiveDirection();

    static String getHeightMarker() {
        return "height";
    }

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
