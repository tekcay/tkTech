package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.HeatContainer;

public class HeatContainerImpl extends DefaultContainerImpl implements HeatContainer {

    public HeatContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
    }

    public HeatContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue, int minValue) {
        super(metaTileEntity, maxValue, minValue);
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == getContainerTypeWrapper().getCapability()) {
            return getContainerTypeWrapper().getCapability().cast(this);
        }
        return null;
    }
}
