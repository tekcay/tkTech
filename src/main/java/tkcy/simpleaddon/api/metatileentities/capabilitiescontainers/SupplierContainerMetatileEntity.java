package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers;

import gregtech.api.metatileentity.MetaTileEntity;
import lombok.Getter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.api.capabilities.helpers.AdjacentCapabilityHelper;

import java.util.List;

@Getter
public abstract class SupplierContainerMetatileEntity extends DefaultContainerMetatileEntity implements AdjacentCapabilityHelper {

    private DefaultContainer receiverContainer;
    private List<EnumFacing> emittingSide;

    protected SupplierContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    protected abstract void tryToEmit(EnumFacing emittingSide);

    @Override
    public MetaTileEntity getThisMetatileEntity() {
        return this;
    }


}
