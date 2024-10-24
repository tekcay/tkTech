package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.capabilities.RotationContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public class RotationContainerImpl extends DefaultContainerImpl implements RotationContainer {

    public RotationContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity, maxValue);
    }

    @Override
    public @NotNull String getName() {
        return ContainerType.ROTATION.name();
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability) {
        if (capability == TKCYSATileCapabilities.CAPABILITY_ROTATION_CONTAINER) {
            return TKCYSATileCapabilities.CAPABILITY_ROTATION_CONTAINER.cast(this);
        }
        return null;
    }
}
