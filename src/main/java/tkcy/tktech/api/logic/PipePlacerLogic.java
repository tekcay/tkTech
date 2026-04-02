package tkcy.tktech.api.logic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.stack.UnificationEntry;

import tkcy.tktech.api.utils.StreamHelper;
import tkcy.tktech.api.utils.WorldInteractionsHelper;

public class PipePlacerLogic {

    private final MetaTileEntity pipePlacer;
    private BlockPos blockPosToPlace;
    private final int maxRange;

    public PipePlacerLogic(MetaTileEntity pipePlacer, int maxRange) {
        this.pipePlacer = pipePlacer;
        this.maxRange = maxRange;
    }

    private boolean isPipeStack(@NotNull ItemStack itemStack) {
        UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);
        if (unificationEntry == null) return false;
        if (unificationEntry.material == null) return false;
        return unificationEntry.orePrefix.name().contains("pipe");
    }

    private void setBlockPosToPlace() {
        blockPosToPlace = StreamHelper.initIntStream(1, maxRange)
                .map(this::getBlockPos)
                .filter(blockPos -> WorldInteractionsHelper.canPlaceBlockInWorld(pipePlacer.getWorld(), blockPos))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private ItemStack getPipeStack() {
        return StreamHelper.initIntStream(pipePlacer.getImportItems().getSlots())
                .map(pipePlacer.getImportItems()::getStackInSlot)
                .filter(itemStack -> !itemStack.isEmpty())
                .filter(this::isPipeStack)
                .findFirst()
                .orElse(null);
    }

    private BlockPos getBlockPos(int offset) {
        return pipePlacer.getPos().offset(pipePlacer.getFrontFacing(), offset);
    }

    public boolean placePipe() {
        ItemStack pipeStack = getPipeStack();
        if (pipeStack == null) return false;
        setBlockPosToPlace();
        if (blockPosToPlace == null) return false;
        boolean didPlace = placePipe(pipeStack);
        if (didPlace) {
            pipeStack.shrink(1);
            return true;
        } else return false;
    }

    private boolean placePipe(@NotNull ItemStack pipeStack) {
        ItemBlockPipe<?, ?> block = (ItemBlockPipe<?, ?>) pipeStack.getItem();

        IBlockState blockState = block.getBlock().getStateForPlacement(
                pipePlacer.getWorld(),
                blockPosToPlace,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                pipeStack.getMetadata(),
                null,
                null);

        return block.placeBlockAt(
                pipeStack,
                null,
                pipePlacer.getWorld(),
                blockPosToPlace,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                blockState);
    }
}
