package tkcy.simpleaddon.api.metatileentities.cleanroom;

import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public interface IAdvancedCleanroomProvider extends ICleanroomProvider {

    /**
     * Consumes gas from the cleanroom
     *
     * @param simulate whether to actually apply change values or not
     * @return whether the draining succeeded
     */
    @Nullable
    FluidStack drainGas(boolean simulate);

    long energyToDrainWhenClean();
    long energyToDrain();

    int gasAmountToDrainWhenClean();
    int gasAmountToDrain();
    int getRoomVolume();
    AdvancedCleanroomType[] getAvailableCleanroomTypes();
    int getCleanroomTypeIndex();
    void setCleanroomTypeIndex(int index);
    default AdvancedCleanroomType getCleanroomType() {
        return getAvailableCleanroomTypes()[getCleanroomTypeIndex()];
    }

}
