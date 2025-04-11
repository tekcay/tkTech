package tkcy.simpleaddon.api.logic;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.impl.CleanroomLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.ICleanroomProvider;
import gregtech.api.metatileentity.multiblock.IMaintenance;
import gregtech.common.ConfigHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class AdvancedCleanroomLogic extends CleanroomLogic {

    public static final int BASE_CLEAN_AMOUNT = 5;

    private int maxProgress = 0;
    private int progressTime = 0;

    private int minEnergyTier;

    private final MetaTileEntity metaTileEntity;
    private final boolean hasMaintenance;

    private boolean isActive;
    private boolean isWorkingEnabled = true;
    private boolean wasActiveAndNeedsUpdate;

    private boolean hasNotEnoughEnergy;

    public AdvancedCleanroomLogic(MetaTileEntity metaTileEntity, int minEnergyTier, MetaTileEntity metaTileEntity1, boolean hasMaintenance) {
        super(metaTileEntity, minEnergyTier);
        this.metaTileEntity = metaTileEntity1;
        this.hasMaintenance = hasMaintenance;
    }

    /**
     * Performs the actual cleaning
     * Call this method every tick in update
     */
    @Override
    public void updateLogic() {
        // cleanrooms which cannot work do nothing
        if (!this.isWorkingEnabled) return;

        // all maintenance problems not fixed means the machine does not run
        if (hasMaintenance && ((IMaintenance) metaTileEntity).getNumMaintenanceProblems() > 5) return;

        // if the energy tier is below min tier then do nothing
        if (!isVoltageHighEnough()) return;

        // drain the energy
        if (consumeEnergy(true)) {
            consumeEnergy(false);
        } else {
            if (progressTime >= 2) {
                if (ConfigHolder.machines.recipeProgressLowEnergy) this.progressTime = 1;
                else this.progressTime = Math.max(1, progressTime - 2);
            }
            hasNotEnoughEnergy = true;

            // the cleanroom does not have enough energy, so it looses cleanliness
            if (metaTileEntity.getOffsetTimer() % maxProgress == 0) {
                adjustCleanAmount(true);
            }
            return;
        }

        if (!this.isActive) setActive(true);

        // increase progress
        progressTime++;
        if (progressTime % getMaxProgress() != 0) return;
        progressTime = 0;

        adjustCleanAmount(false);
    }

    protected void adjustCleanAmount(boolean shouldRemove) {
        int amountToClean = BASE_CLEAN_AMOUNT * (getTierDifference() + 1);
        if (shouldRemove) amountToClean *= -1;

        // each maintenance problem lowers gain by 1
        if (hasMaintenance) amountToClean -= ((IMaintenance) metaTileEntity).getNumMaintenanceProblems();
        ((ICleanroomProvider) metaTileEntity).adjustCleanAmount(amountToClean);
    }

    protected boolean consumeGas(boolean simulate) {
        return ((ICleanroomProvider) metaTileEntity).drainEnergy(simulate);
    }

    public boolean isVoltageHighEnough() {
        return minEnergyTier <= ((ICleanroomProvider) metaTileEntity).getEnergyTier();
    }

    /**
     * @return whether working is enabled for the logic
     */
    public boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }

    /**
     * @return whether the cleanroom is currently working
     */
    public boolean isWorking() {
        return isActive && !hasNotEnoughEnergy && isWorkingEnabled;
    }

    /**
     * writes all needed values to NBT
     * This MUST be called and returned in the MetaTileEntity's {@link MetaTileEntity#writeToNBT(NBTTagCompound)} method
     */
    public NBTTagCompound writeToNBT(@NotNull NBTTagCompound data) {
        data.setBoolean("isActive", this.isActive);
        data.setBoolean("isWorkingEnabled", this.isWorkingEnabled);
        data.setBoolean("wasActiveAndNeedsUpdate", this.wasActiveAndNeedsUpdate);
        data.setInteger("progressTime", progressTime);
        data.setInteger("maxProgress", maxProgress);
        return data;
    }

    /**
     * reads all needed values from NBT
     * This MUST be called and returned in the MetaTileEntity's {@link MetaTileEntity#readFromNBT(NBTTagCompound)}
     * method
     */
    public void readFromNBT(@NotNull NBTTagCompound data) {
        this.isActive = data.getBoolean("isActive");
        this.isWorkingEnabled = data.getBoolean("isWorkingEnabled");
        this.wasActiveAndNeedsUpdate = data.getBoolean("wasActiveAndNeedsUpdate");
        this.progressTime = data.getInteger("progressTime");
        this.maxProgress = data.getInteger("maxProgress");
    }

    /**
     * writes all needed values to InitialSyncData
     * This MUST be called and returned in the MetaTileEntity's
     * {@link MetaTileEntity#writeInitialSyncData(PacketBuffer)} method
     */
    public void writeInitialSyncData(@NotNull PacketBuffer buf) {
        buf.writeBoolean(this.isActive);
        buf.writeBoolean(this.isWorkingEnabled);
        buf.writeBoolean(this.wasActiveAndNeedsUpdate);
        buf.writeInt(this.progressTime);
        buf.writeInt(this.maxProgress);
    }

    /**
     * reads all needed values from InitialSyncData
     * This MUST be called and returned in the MetaTileEntity's
     * {@link MetaTileEntity#receiveInitialSyncData(PacketBuffer)} method
     */
    public void receiveInitialSyncData(@NotNull PacketBuffer buf) {
        setActive(buf.readBoolean());
        setWorkingEnabled(buf.readBoolean());
        setWasActiveAndNeedsUpdate(buf.readBoolean());
        this.progressTime = buf.readInt();
        this.maxProgress = buf.readInt();
    }

    /**
     * reads all needed values from CustomData
     * This MUST be called and returned in the MetaTileEntity's
     * {@link MetaTileEntity#receiveCustomData(int, PacketBuffer)} method
     */
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.IS_WORKING) {
            setActive(buf.readBoolean());
            metaTileEntity.scheduleRenderUpdate();
        }
    }

    /**
     * @return whether the cleanroom was active and needs an update
     */
    public boolean wasActiveAndNeedsUpdate() {
        return this.wasActiveAndNeedsUpdate;
    }

    /**
     * set whether the cleanroom was active and needs an update
     *
     * @param wasActiveAndNeedsUpdate the state to set
     */
    public void setWasActiveAndNeedsUpdate(boolean wasActiveAndNeedsUpdate) {
        this.wasActiveAndNeedsUpdate = wasActiveAndNeedsUpdate;
    }
}
