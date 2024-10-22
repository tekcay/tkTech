package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.consumers;

import net.minecraft.util.ResourceLocation;

import tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.DefaultContainerMetatileEntity;

public abstract class ConsumerContainerMetatileEntity extends DefaultContainerMetatileEntity {

    protected ConsumerContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
}
