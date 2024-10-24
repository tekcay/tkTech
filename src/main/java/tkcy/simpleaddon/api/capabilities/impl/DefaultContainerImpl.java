package tkcy.simpleaddon.api.capabilities.impl;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

@Getter
public abstract class DefaultContainerImpl extends MTETrait implements DefaultContainer {

    private final int maxValue;
    private int minValue;
    protected int value;

    protected DefaultContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue) {
        super(metaTileEntity);
        this.maxValue = maxValue;
        this.value = getDefaultValue();
    }

    protected DefaultContainerImpl(@NotNull MetaTileEntity metaTileEntity, int maxValue,
                                   int minValue) {
        super(metaTileEntity);
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.value = getDefaultValue();
    }

    @Override
    public boolean increaseValue(int amount) {
        if (value + amount > maxValue || value + amount < minValue) return false;
        value += amount;
        this.metaTileEntity.markDirty();
        return true;
    }

    @Override
    public void setValue(int amount) {
        value = amount;
        this.metaTileEntity.markDirty();
    }

    @Override
    @NotNull
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(getName(), this.value);
        return tagCompound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.value = compound.getInteger(getName());
    }

    @Override
    public void writeInitialSyncData(@NotNull PacketBuffer buf) {
        buf.writeInt(this.value);
    }

    @Override
    public void receiveInitialSyncData(@NotNull PacketBuffer buf) {
        this.value = buf.readInt();
    }
}
