package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.consumers;

import net.minecraft.util.ResourceLocation;

import tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.DefaultContainerMetatileEntity;

public abstract class ConsumerContainerMetatileEntity extends DefaultContainerMetatileEntity {

    protected ConsumerContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    protected void consume(int amount) {
        getInternalContainer().increaseValue(amount);
    }

    @Override
    public void update() {
        super.update();
        if (this.isBlockRedstonePowered()) return;
        if (getInternalContainer().isEmpty()) return;

        if (!getWorld().isRemote) {
            consume(-10);
        }
    }
}
