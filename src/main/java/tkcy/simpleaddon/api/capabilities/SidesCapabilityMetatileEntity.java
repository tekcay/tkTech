package tkcy.simpleaddon.api.capabilities;

import java.util.List;

import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

public interface SidesCapabilityMetatileEntity {

    @NotNull
    List<EnumFacing> getCapabilitySides();
}
