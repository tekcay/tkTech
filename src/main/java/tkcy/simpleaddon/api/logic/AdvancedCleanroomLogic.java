package tkcy.simpleaddon.api.logic;

import gregtech.api.capability.impl.CleanroomLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import gregtech.api.metatileentity.multiblock.IMaintenance;

import tkcy.simpleaddon.api.metatileentities.cleanroom.IAdvancedCleanroomProvider;

public class AdvancedCleanroomLogic extends CleanroomLogic {

    private final MetaTileEntity metaTileEntity;

    public AdvancedCleanroomLogic(MetaTileEntity metaTileEntity) {
        super(metaTileEntity, 1);
        this.metaTileEntity = metaTileEntity;
    }

    protected void adjustCleanAmount(boolean shouldRemove) {
        int amountToClean = BASE_CLEAN_AMOUNT * (getTierDifference() + 1);
        if (shouldRemove) amountToClean *= -1;

        // each maintenance problem lowers gain by 1
        amountToClean -= ((IMaintenance) metaTileEntity).getNumMaintenanceProblems();
        ((ICleanroomProvider) metaTileEntity).adjustCleanAmount(amountToClean);
    }

    @Override
    public boolean consumeEnergy(boolean simulate) {
        return super.consumeEnergy(simulate) && consumeGas(simulate);
    }

    protected boolean consumeGas(boolean simulate) {
        return ((IAdvancedCleanroomProvider) metaTileEntity).drainGas(simulate);
    }
}
