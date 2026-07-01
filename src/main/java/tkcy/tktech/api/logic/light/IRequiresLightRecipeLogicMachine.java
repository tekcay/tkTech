package tkcy.tktech.api.logic.light;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import gregtech.common.blocks.BlockLamp;

import tkcy.tktech.api.utils.BlockStateHelper;

public interface IRequiresLightRecipeLogicMachine extends ILightRecipeLogicMachine {

    /**
     * @return the {@code BlockPos} that requires a GT {@link BlockLamp} for a recipe to run.
     */
    @Nullable
    BlockPos gtLampPos();

    default boolean hasLamp(World world, EnumDyeColor lightColor) {
        if (gtLampPos() == null) return false;
        Block block = BlockStateHelper.getBlockAtBlockPos(gtLampPos(), world);
        if (block instanceof BlockLamp blockLamp) {
            return blockLamp.isLightEnabled(blockLamp.blockState.getBaseState()) && blockLamp.color == lightColor;
        } else return false;
    }
}
