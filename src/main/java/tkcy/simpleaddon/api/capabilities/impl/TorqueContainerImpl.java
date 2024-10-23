package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.api.capabilities.TorqueContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

public class TorqueContainerImpl extends DefaultContainerImpl implements TorqueContainer {

    public TorqueContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity, minValue, maxValue);
    }

    @Override
    @NotNull
    public String getName() {
        return CapabilityModule.ContainerType.TORQUE.name();
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_TORQUE_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_TORQUE_CONTAINER.cast(this);
        }
        return null;
    }
}
