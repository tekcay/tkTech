package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.ContainerType;
import tkcy.simpleaddon.api.capabilities.PressureContainer;

public class PressureContainerImpl extends DefaultContainerImpl implements PressureContainer {

    public PressureContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity, minValue, maxValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.PRESSURE.name();
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        return null;
    }
}
