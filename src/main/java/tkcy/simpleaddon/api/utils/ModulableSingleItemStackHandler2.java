package tkcy.simpleaddon.api.utils;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

public class ModulableSingleItemStackHandler2 extends GTItemStackHandler {

    private final int maxPerSlot;

    public ModulableSingleItemStackHandler2(MetaTileEntity metaTileEntity, int maxPerSlot) {
        super(metaTileEntity, 1);
        this.maxPerSlot = maxPerSlot;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.maxPerSlot;
    }
}
