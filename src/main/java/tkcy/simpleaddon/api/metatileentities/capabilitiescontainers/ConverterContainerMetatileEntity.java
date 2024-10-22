package tkcy.simpleaddon.api.metatileentities.capabilitiescontainers;

import lombok.Getter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;

import java.util.List;

@Getter
public abstract class ConverterContainerMetatileEntity extends DefaultContainerMetatileEntity  {

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

    @Override
    public void update() {
        super.update();
        // Redstone stops fluid transfer
        if (this.isBlockRedstonePowered()) return;
        if (receiverContainer.getValue() == receiverContainer.getMinValue()) return;

        if (!getWorld().isRemote) {

            convert();

            energyContainer = getAdjacentCapabilityContainer(this);
            if (energyContainer != null) {
                transferRate = rotationContainer.getSpeed();
            } else {
                transferRate = 0;
                return;
            }
            energyContainer.addEnergy(transferRate);
        }
    }
}
