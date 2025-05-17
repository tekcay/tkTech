package tkcy.tktech.api.utils.handlers;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraftforge.fluids.IFluidTank;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.tktech.api.utils.StreamHelper;

public class FluidTankListHelper {

    public static FluidTankList createNotifiableFluidHandler(int tanks, int capacity, MetaTileEntity mteToNotify,
                                                             boolean isExport) {
        List<IFluidTank> fluidTanks = StreamHelper.initIntStream(tanks)
                .map(i -> new NotifiableFluidTank(capacity, mteToNotify, isExport))
                .collect(Collectors.toList());

        return new FluidTankList(false, fluidTanks);
    }
}
