package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public class HeatContainerImpl extends DefaultContainerImpl implements HeatContainer {

    public HeatContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity,  maxValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.HEAT.name();
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER.cast(this);
        }
        return null;
    }
}
