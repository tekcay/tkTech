package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.MetaTileEntity;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.api.capabilities.helpers.AdjacentCapabilityHelper;

@Getter
public abstract class SupplierContainerMetatileEntity extends DefaultContainerMetatileEntity
                                                      implements AdjacentCapabilityHelper {

    private DefaultContainer receiverContainer;

    protected SupplierContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    protected abstract void tryToEmit(EnumFacing emittingSide);

    @Override
    public MetaTileEntity getThisMetatileEntity() {
        return this;
    }
}
