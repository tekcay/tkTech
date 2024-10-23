package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.RotationPowerContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

public class RotationPowerContainerImpl extends DefaultContainerImpl implements RotationPowerContainer {

    public RotationPowerContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity, minValue, maxValue);
    }

    @Override
    public @NotNull String getName() {
        return CapabilityModule.ContainerType.ROTATION_POWER.name();
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_ROTATION_POWER_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_ROTATION_POWER_CONTAINER.cast(this);
        }
        return null;
    }
}
