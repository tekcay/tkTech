package tkcy.simpleaddon.api.utils;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTTransferUtils;

import lombok.Getter;

@Getter
public class ModulableSingleItemStackHandler2 extends GTItemStackHandler {

    private final int maxPerSlot;
    private final MetaTileEntity metaTileEntity;

    public ModulableSingleItemStackHandler2(@NotNull MetaTileEntity metaTileEntity, int maxPerSlot) {
        super(metaTileEntity, 1);
        this.metaTileEntity = metaTileEntity;
        this.maxPerSlot = maxPerSlot;
    }

    public ModulableSingleItemStackHandler2(@NotNull MetaTileEntity metaTileEntity, int maxPerSlot,
                                            @NotNull ItemStack itemStack) {
        super(metaTileEntity, 1);
        this.metaTileEntity = metaTileEntity;
        this.maxPerSlot = maxPerSlot;
        setStackInSlot(0, itemStack);
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return this.maxPerSlot;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.maxPerSlot;
    }

    public ItemStack getContent() {
        return this.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return getContent() == null || getContent().isEmpty();
    }

    public boolean isFull() {
        return getContent().getCount() == this.maxPerSlot;
    }

    public boolean hasChanged(@NotNull ItemStack previousStack) {
        return this.getContent() != previousStack;
    }

    public void increaseAmount(int amount) {
        getContent().grow(amount);
    }

    public ItemStack insertItem(@Nonnull ItemStack stack, boolean simulate) {
        return this.insertItem(0, stack, simulate);
    }

    /**
     * @return the {@code ItemStack} not transferred
     */
    @Nullable
    public ItemStack transferToHandler(@NotNull IItemHandler targetInventory) {
        if (this.isEmpty()) return ItemStack.EMPTY;

        ItemStack sourceStack = this.extractItem(0, getContent().getCount(), true);
        if (sourceStack.isEmpty()) return ItemStack.EMPTY;

        ItemStack remainder = GTTransferUtils.insertItem(targetInventory, sourceStack, true);
        int amountToInsert = sourceStack.getCount() - remainder.getCount();

        if (amountToInsert > 0) {
            sourceStack = extractItem(0, amountToInsert, false);

            return GTTransferUtils.insertItem(targetInventory, sourceStack, false);
        }
        return ItemStack.EMPTY;
    }

    public int getMaxTransferredAmount(@NotNull IItemHandler targetInventory, @NotNull ItemStack itemStack) {
        int itemStackToTransferAmount = itemStack.getCount();

        for (int slotIndex = 0; slotIndex < targetInventory.getSlots(); slotIndex++) {
            ItemStack itemStackInSlot = targetInventory.getStackInSlot(slotIndex);
            if (itemStackInSlot.isEmpty()) {
                itemStackToTransferAmount -= Math.min(64, itemStackToTransferAmount);
            } else if (itemStackInSlot.isItemEqual(itemStack)) {
                itemStackToTransferAmount -= Math.min(itemStackInSlot.getCount(), itemStackToTransferAmount);
            }
        }
        return itemStackToTransferAmount;
    }

    public int getMaxTransferredAmount(@NotNull IItemHandler targetInventory) {
        int itemStackToTransferAmount = getContent().getCount();
        if (itemStackToTransferAmount == 0) return 0;

        int maxTransferredAmount = 0;

        for (int slotIndex = 0; slotIndex < targetInventory.getSlots(); slotIndex++) {
            ItemStack itemStackInSlot = targetInventory.getStackInSlot(slotIndex);
            if (itemStackInSlot.isEmpty()) {
                maxTransferredAmount += Math.min(64, itemStackToTransferAmount);
                itemStackToTransferAmount -= maxTransferredAmount;

            } else if (itemStackInSlot.isItemEqual(getContent())) {
                maxTransferredAmount += Math.min(64 - itemStackInSlot.getCount(), itemStackToTransferAmount);
                itemStackToTransferAmount -= maxTransferredAmount;
            }
        }
        return maxTransferredAmount;
    }

    public void export(IItemHandler handler, int maxToBeTransferred) {
        ItemStack tobeTransferred = ItemHandlerHelpers.newItemStack(getContent(), maxToBeTransferred);
        GTTransferUtils.insertItem(handler, tobeTransferred, false);
        this.increaseAmount(-maxToBeTransferred);
    }
}
