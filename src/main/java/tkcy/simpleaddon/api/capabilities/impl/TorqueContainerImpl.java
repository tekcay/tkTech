package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.api.capabilities.TorqueContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public class TorqueContainerImpl extends DefaultContainerImpl implements TorqueContainer {

    public TorqueContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.TORQUE.name();
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_TORQUE_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_TORQUE_CONTAINER.cast(this);
        }
        return null;
    }
}
