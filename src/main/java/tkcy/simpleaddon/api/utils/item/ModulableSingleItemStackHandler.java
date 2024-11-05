package tkcy.simpleaddon.api.utils.item;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GTTransferUtils;

import lombok.Getter;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
@Getter
public class ModulableSingleItemStackHandler extends GTItemStackHandler {

    private final int maxPerSlot;
    private final MetaTileEntity metaTileEntity;

    public ModulableSingleItemStackHandler(@NotNull MetaTileEntity metaTileEntity, int maxPerSlot) {
        super(metaTileEntity, 1);
        this.metaTileEntity = metaTileEntity;
        this.maxPerSlot = maxPerSlot;
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

    public int getMaxTransferable(@NotNull IItemHandler targetInventory) {
        if (isEmpty()) return 0;

        int storedAmount = getContent().getCount();
        int maxTransferable = 0;
        int maxTransferableInSlot;

        for (int slotIndex = 0; slotIndex < targetInventory.getSlots(); slotIndex++) {
            ItemStack itemStackInSlot = targetInventory.getStackInSlot(slotIndex);

            if (itemStackInSlot.isEmpty()) {
                maxTransferableInSlot = Math.min(64, storedAmount);
                maxTransferable += maxTransferableInSlot;
                storedAmount -= maxTransferableInSlot;

            } else if (itemStackInSlot.isItemEqual(getContent())) {
                maxTransferableInSlot = Math.min(64 - itemStackInSlot.getCount(), storedAmount);
                maxTransferable += maxTransferableInSlot;
                storedAmount -= maxTransferableInSlot;
            }

            if (storedAmount == 0) return maxTransferable;
        }
        return maxTransferable;
    }

    public void exportToHandler(IItemHandler targetHandler, int maxToBeTransferred) {
        ItemStack tobeTransferred = ItemHandlerHelpers.copyWithAmount(getContent(), maxToBeTransferred);
        GTTransferUtils.insertItem(targetHandler, tobeTransferred, false);
        this.increaseAmount(-maxToBeTransferred);
    }
}
