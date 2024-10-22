package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.consumers;

import net.minecraft.util.ResourceLocation;
import tkcy.simpleaddon.api.capabilities.HeatContainer;

public abstract class HeatConsumerMetatileEntity extends ConsumerContainerMetatileEntity {

    protected HeatConsumerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }


    @Override
    public HeatContainer getSupplierContainer() {
        return
    }

}
