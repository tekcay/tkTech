package tkcy.tktech.api.utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockStateHelper {

    /**
     * @return {@code null} if {@link Blocks#AIR} is found.
     */
    @Nullable
    public static IBlockState itemStackToBlockState(@NotNull ItemStack blockStack) {
        Block block = Block.getBlockFromItem(blockStack.getItem());
        return block.equals(Blocks.AIR) ? null : block.getDefaultState();
    }

    /**
     * @return {@code null} if {@link Blocks#AIR} is found.
     */
    @Nullable
    public static ItemStack blockStateToItemStack(@NotNull IBlockState blockState) {
        Block block = blockState.getBlock();
        if (block == Blocks.AIR) return null;
        return Item.getItemFromBlock(blockState.getBlock()).getDefaultInstance();
    }

    /**
     * @return {@code null} if {@link Blocks#AIR} is found.
     */
    @Nullable
    public static Block getBlockAtBlockPos(@NotNull BlockPos blockPos, @NotNull World world) {
        Block foundBlock = world.getBlockState(blockPos).getBlock();
        return foundBlock.equals(Blocks.AIR) ? null : foundBlock;
    }
}
