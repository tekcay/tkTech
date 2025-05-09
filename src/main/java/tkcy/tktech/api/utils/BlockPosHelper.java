package tkcy.tktech.api.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.Nullable;

import tkcy.tktech.modules.NBTLabel;

public class BlockPosHelper {

    public static NBTTagCompound serializeBlockPos(@Nullable BlockPos blockPos) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        if (blockPos != null) {
            tagCompound.setInteger(NBTLabel.BLOCK_POS_X.toString(), blockPos.getX());
            tagCompound.setInteger(NBTLabel.BLOCK_POS_Y.toString(), blockPos.getY());
            tagCompound.setInteger(NBTLabel.BLOCK_POS_Z.toString(), blockPos.getZ());
        }
        return tagCompound;
    }

    @Nullable
    public static BlockPos deserializeBlockPos(NBTTagCompound blockPosTagCompound) {
        if (blockPosTagCompound.isEmpty()) return null;
        int x = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_X.toString());
        int y = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_Y.toString());
        int z = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_Z.toString());
        return new BlockPos(x, y, z);
    }

    @Nullable
    public static BlockPos deserializeBlockPos(NBTTagCompound tagCompound, String key) {
        if (tagCompound.isEmpty()) return null;
        NBTTagCompound blockPosTagCompound = tagCompound.getCompoundTag(key);
        if (blockPosTagCompound.isEmpty()) return null;
        int x = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_X.toString());
        int y = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_Y.toString());
        int z = blockPosTagCompound.getInteger(NBTLabel.BLOCK_POS_Z.toString());
        return new BlockPos(x, y, z);
    }
}
