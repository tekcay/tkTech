package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;
import tkcy.simpleaddon.api.capabilities.PressureContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;

public class PressureContainerImpl extends DefaultContainerImpl implements PressureContainer {

    public PressureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity, minValue, maxValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.PRESSURE.name();
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_PRESSURE_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_PRESSURE_CONTAINER.cast(this);
        }
        return null;
    }
}
