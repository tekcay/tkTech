package tkcy.simpleaddon.api.utils;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

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

    public void increaseAmount(int amount) {
        getContent().grow(amount);
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
        // TODO

        ItemStack tobeTransferred = ItemHandlerHelpers.copyWithAmount(getContent(), maxToBeTransferred);
        GTTransferUtils.insertItem(handler, tobeTransferred, false);
        this.increaseAmount(-maxToBeTransferred);
    }
}
