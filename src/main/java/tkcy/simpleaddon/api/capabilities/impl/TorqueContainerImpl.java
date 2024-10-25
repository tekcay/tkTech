package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.TorqueContainer;

public class TorqueContainerImpl extends DefaultContainerImpl implements TorqueContainer {

    public TorqueContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
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
