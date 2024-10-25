package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.TemperatureContainer;

public class TemperatureContainerImpl extends DefaultContainerImpl implements TemperatureContainer {

    public TemperatureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
    }

    public TemperatureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue, int minValue) {
        super(metaTileEntity, maxValue, minValue);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == getContainerTypeWrapper().getCapability()) {
            return getContainerTypeWrapper().getCapability().cast(this);
        }
        return null;
    }
}
