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

import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.StreamHelper;
import tkcy.tktech.api.utils.WorldInteractionsHelper;

public class PipePlacerLogic {

    private final MetaTileEntity pipePlacer;

    public PipePlacerLogic(MetaTileEntity pipePlacer) {
        this.pipePlacer = pipePlacer;
    }

    private boolean isPipeStack(@NotNull ItemStack itemStack) {
        UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);

        return BooleanHelper.and(
                unificationEntry != null,
                unificationEntry.material != null,
                unificationEntry.orePrefix.name().contains("pipe"));
    }

    @Nullable
    public ItemStack getPipeStack() {
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

    private boolean canPlacePipe(int offset) {
        return WorldInteractionsHelper.canPlaceBlockInWorld(pipePlacer.getWorld(), getBlockPos(offset));
    }

    public void placePipes() {
        for (int slotIndex = 0; slotIndex < pipePlacer.getImportItems().getSlots(); slotIndex++) {
            ItemStack pipeStack = pipePlacer.getImportItems().getStackInSlot(slotIndex);
            if (pipeStack.isEmpty()) continue;
            if (isPipeStack(pipeStack) && canPlacePipe(slotIndex)) {
                boolean didPlace = placePipe(pipeStack, getBlockPos(slotIndex));
                if (didPlace) {
                    pipeStack.shrink(1);
                } else return;
            }
        }
    }

    private boolean placePipe(@NotNull ItemStack pipeStack, BlockPos blockPos) {
        ItemBlockPipe<?, ?> block = (ItemBlockPipe<?, ?>) pipeStack.getItem();

        IBlockState blockState = block.getBlock().getStateForPlacement(
                pipePlacer.getWorld(),
                blockPos,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                pipeStack.getMetadata(),
                null,
                null);

        return block.placeBlockAt(
                pipeStack,
                null,
                pipePlacer.getWorld(),
                blockPos,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                blockState);
    }
}
