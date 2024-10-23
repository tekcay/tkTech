package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;

public class HeatContainerImpl extends DefaultContainerImpl implements HeatContainer {

    public HeatContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity, minValue, maxValue);
    }

    @Override
    @NotNull
    public String getName() {
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
