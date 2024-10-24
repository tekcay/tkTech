package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.api.capabilities.TemperatureContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public class TemperatureContainerImpl extends DefaultContainerImpl implements TemperatureContainer {

    public TemperatureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
    }

    public TemperatureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue, int minValue) {
        super(metaTileEntity, maxValue, minValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.TEMPERATURE.name();
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_TEMPERATURE_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_TEMPERATURE_CONTAINER.cast(this);
        }
        return null;
    }
}
