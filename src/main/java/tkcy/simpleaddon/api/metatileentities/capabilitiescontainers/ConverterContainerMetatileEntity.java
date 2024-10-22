package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;

@Getter
public abstract class ConverterContainerMetatileEntity extends DefaultContainerMetatileEntity {

    private DefaultContainer emitterContainer;
    private DefaultContainer receiverContainer;
    private List<EnumFacing> receptionSides;
    private List<EnumFacing> emissionSides;

    protected ConverterContainerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    /**
     * Changes receiverContainer value to change emitterContainer value
     */
    abstract void convert();

    abstract boolean tryToEmit();
}
