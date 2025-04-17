package tkcy.simpleaddon.api.utils;

import java.util.List;

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
        return BlockStateHelper.getBlockAtBlockPos(blockPos, world) != null;
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
}
