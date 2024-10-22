package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.consumers;

import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.DefaultContainerMetatileEntity;

public abstract class ConsumerContainerMetatileEntity extends DefaultContainerMetatileEntity {
    protected ConsumerContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    protected void consume(int amount) {
        getInternContainer().increaseValue(amount);
    }

    @Override
    public void update() {
        super.update();
        if (this.isBlockRedstonePowered()) return;
        if (getInternContainer().isEmpty()) return;

        if (!getWorld().isRemote) {
            consume(-10);
        }
    }
}
