package tkcy.tktech.api.metatileentities.cleanroom;

import java.util.List;

import net.minecraft.util.text.ITextComponent;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.ICleanroomProvider;

public interface IAdvancedCleanroomProvider extends ICleanroomProvider {

    static IAdvancedCleanroomProvider getInstance(MetaTileEntity metaTileEntity) {
        return (IAdvancedCleanroomProvider) metaTileEntity;
    }

    /**
     * Consumes gas from the cleanroom
     *
     * @param simulate whether to actually apply change values or not
     * @return whether the draining succeeded
     */
    boolean drainGas(boolean simulate);

    long energyToDrainWhenClean();

    long energyToDrain();

    int gasAmountToDrainWhenClean();

    int gasAmountToDrain();

    int getRoomVolume();

    int getCleanroomTypeIndex();

    void setCleanroomTypeIndex(int index);

    void addGasConsumptionInfos(List<ITextComponent> textComponents);

    boolean hasEnoughGas();

    boolean hasEnoughEnergy();

    default AdvancedCleanroomType getCleanroomType() {
        return AdvancedCleanroomType.ADVANCED_CLEANROOM_TYPES.get(getCleanroomTypeIndex());
    }
}
