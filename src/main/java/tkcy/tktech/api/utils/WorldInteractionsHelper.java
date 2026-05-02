package tkcy.tktech.api.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

public class WorldInteractionsHelper {

    /**
     * Spawns items in world
     */
    public static void spawnStacks(MetaTileEntity metaTileEntity, List<ItemStack> itemStacks) {
        BlockPos metaTileEntityBlockPos = metaTileEntity.getPos();
        World world = metaTileEntity.getWorld();
        spawnStacks(world, metaTileEntityBlockPos, itemStacks);
    }

    /**
     * Spawns items in world
     */
    public static void spawnStacks(World world, BlockPos blockPos, List<ItemStack> itemStacks) {
        itemStacks.forEach(itemStack -> Block.spawnAsEntity(world, blockPos, itemStack));
    }

    /**
     * Spawns items in world
     */
    public static void spawnStacks(World world, BlockPos blockPos, ItemStack itemStack) {
        Block.spawnAsEntity(world, blockPos, itemStack);
    }

    @Nullable
    public static ItemStack getInWorldInputStack(@NotNull MetaTileEntity metaTileEntity, @NotNull BlockPos blockPos) {
        World world = metaTileEntity.getWorld();
        return getInWorldInputStack(world, blockPos);
    }

    @Nullable
    public static ItemStack getInWorldInputStack(@NotNull World world, @NotNull BlockPos blockPos) {
        Block scannedBlock = BlockStateHelper.getBlockAtBlockPos(blockPos, world);
        if (scannedBlock != null) {
            Item foundItem = Item.getItemFromBlock(scannedBlock);
            return foundItem.getDefaultInstance();
        }
        return null;
    }

    public static boolean canPlaceBlockInWorld(@NotNull World world, @NotNull BlockPos blockPos) {
        return BlockStateHelper.getBlockAtBlockPos(blockPos, world) == null;
    }

    public static void placeBlockInWorld(@NotNull IBlockState block, @NotNull World world, @NotNull BlockPos blockPos) {
        world.setBlockState(blockPos, block);
    }

    /**
     * @return {@code true} if it worked.
     */
    public static boolean placeBlockInWorld(@Nullable ItemStack itemStack, @NotNull World world,
                                            @NotNull BlockPos blockPos,
                                            boolean forceReplace) {
        if (itemStack == null) return false;
        IBlockState blockState = BlockStateHelper.itemStackToBlockState(itemStack);
        if (blockState != null && (canPlaceBlockInWorld(world, blockPos) || forceReplace)) {
            world.setBlockState(blockPos, blockState);
            return true;
        }
        return false;
    }

    public static void removeBlockInWorld(@NotNull World world, @NotNull BlockPos blockPos) {
        placeBlockInWorld(Blocks.AIR.getDefaultState(), world, blockPos);
    }

    /**
     * @return all the blocks inside a {@code sideLength}^3 cube centered
     *         around a {@code center} BlockPos.
     */
    public static Set<BlockPos> getAllBlockPosAround(BlockPos center, int sideLength) {
        Set<BlockPos> blockPosSet = new HashSet<>();
        int r = sideLength / 2;
        for (int x = -r; x < r + 1; x++) {
            for (int y = -r; y < r + 1; y++) {
                for (int z = -r; z < r + 1; z++) {
                    blockPosSet.add(center.add(x, y, z));
                }
            }
        }
        return blockPosSet;
    }

    /**
     * This scans in a {@code sideLength}^3 cube centered around a provided {@code blockPos}.
     * 
     * @return {@code true} if light in each block is {@code 0}.
     */
    public static boolean isInTheDark(World world, BlockPos center, int sideLength) {
        return getAllBlockPosAround(center, sideLength)
                .stream()
                .mapToInt(world::getLight)
                .allMatch(light -> light == 0);
    }

    /**
     * This scans in a {@code sideLength}^3 cube centered around a provided {@code metaTileEntity}.
     *
     * @return {@code true} if light in each block is {@code 0}.
     */
    public static boolean isInTheDark(@NotNull MetaTileEntity metaTileEntity, int sideLength) {
        return isInTheDark(metaTileEntity.getWorld(), metaTileEntity.getPos(), sideLength);
    }
}
