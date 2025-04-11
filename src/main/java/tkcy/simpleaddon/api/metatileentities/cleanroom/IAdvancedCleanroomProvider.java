package tkcy.simpleaddon.api.metatileentities.cleanroom;

import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IAdvancedCleanroomProvider extends ICleanroomProvider {

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

    default AdvancedCleanroomType getCleanroomType() {
        return AdvancedCleanroomType.ADVANCED_CLEANROOM_TYPES.get(getCleanroomTypeIndex());
    }
}
