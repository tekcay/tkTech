package tkcy.tktech.api.logic.light;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import tkcy.tktech.api.utils.WorldInteractionsHelper;

public non-sealed interface IRequiresNoLightRecipeLogicMachine extends ILightRecipeLogicMachine {

    /**
     * @return the center of the cube to check InWorld light value.
     */
    @Nullable
    BlockPos scanCenterBlockPos();

    /**
     * @return the side of the cube to check InWorld light value.
     */
    int scanRadius();

    /**
     * @return whether {@link World#getLight(BlockPos)} returns {@code 0} in the {@link #scanRadius()}^3
     *         {@link #scanCenterBlockPos()} centered cube.
     */
    default boolean hasNoLight(World world) {
        if (scanCenterBlockPos() == null || scanRadius() <= 0) return false;
        return WorldInteractionsHelper.isInTheDark(world, scanCenterBlockPos(), scanRadius());
    }
}
