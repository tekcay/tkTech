package tkcy.simpleaddon.api.machines;

import java.util.List;
import java.util.stream.IntStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;

import gregtech.api.util.GTTransferUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A metatileentity that can transfer an itemStack from the player hand by right-clicking.
 */
public interface IRightClickItemTransfer {

    default boolean doesTransferHandStackToInput() {
        return false;
    }

    default boolean doesTransferOutputToPlayer() {
        return false;
    }

    default boolean doesTransferInputToPlayer() {
        return false;
    }

    /**
     * @return {@code true} if a transfer happened
     */
    default boolean tryTransferHandStackToInput(@NotNull EntityPlayer player, EnumHand hand, @NotNull IItemHandler machineInput) {
        ItemStack heldStack = player.getHeldItem(hand).copy();
        ItemStack remainder = GTTransferUtils.insertItem(machineInput, heldStack, false);

        if (heldStack.isItemEqual(remainder)) return false;

        player.getHeldItem(hand).shrink(heldStack.getCount() - remainder.getCount());
        return true;

    }

    /**
     * @return {@code true} if a transfer happened
     */
    default boolean tryTransferOutputToPlayer(EntityPlayer playerIn, @NotNull IItemHandler machineOutput) {
        if (machineOutput.getSlots() > 0) {
            ItemStack foundStack = IntStream.range(0, machineOutput.getSlots())
                    .boxed()
                    .map(machineOutput::getStackInSlot)
                    .findFirst()
                    .orElse(null);

            if (foundStack != null) return playerIn.inventory.addItemStackToInventory(foundStack);
        }
        return false;
    }

    /**
     * @return {@code true} if a transfer happened
     */
    default boolean tryTransferInputToPlayer(EntityPlayer playerIn, @NotNull IItemHandler machineInput) {
        if (machineInput.getSlots() > 0) {
            ItemStack foundStack = IntStream.range(0, machineInput.getSlots())
                    .boxed()
                    .map(machineInput::getStackInSlot)
                    .findFirst()
                    .orElse(null);

            if (foundStack == null) return false;
            else playerIn.inventory.addItemStackToInventory(foundStack);
        }
        return false;
    }
}
