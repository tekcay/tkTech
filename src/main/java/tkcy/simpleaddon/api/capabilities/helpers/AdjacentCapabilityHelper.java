package tkcy.simpleaddon.api.capabilities.helpers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;

public interface AdjacentCapabilityHelper {

    EnumFacing getEmittingFace();

    MetaTileEntity getThisMetatileEntity();

    @Nullable
    default <T> T getAdjacentCapabilityContainer(Capability<T> capabilityToLookFor) {
        TileEntity te = getThisMetatileEntity()
                .getWorld()
                .getTileEntity(getThisMetatileEntity()
                        .getPos()
                        .offset(getEmittingFace()));
        if (te != null) {
            return te.getCapability(capabilityToLookFor, getEmittingFace().getOpposite());
        }
        return null;
    }
}
