package tkcy.simpleaddon.api.utils;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MassiveSingleFilterItemStackHandler2 extends GTItemStackHandler {

    private int maxPerSlot;

    public MassiveSingleFilterItemStackHandler2(MetaTileEntity metaTileEntity) {
        super(metaTileEntity);
        this.maxPerSlot = 0;
    }

    public MassiveSingleFilterItemStackHandler2(MetaTileEntity metaTileEntity, int maxPerSlot) {
        super(metaTileEntity);
        this.maxPerSlot = maxPerSlot;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return this.maxPerSlot;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.maxPerSlot;
    }

    public int getAmount() {
        return this.getStackInSlot(0).getCount();
    }
}
