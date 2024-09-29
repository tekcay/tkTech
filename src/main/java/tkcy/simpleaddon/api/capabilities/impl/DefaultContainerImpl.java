package tkcy.simpleaddon.api.capabilities.impl;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MTETrait;
import gregtech.api.metatileentity.MetaTileEntity;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;

@Getter
public abstract class DefaultContainerImpl extends MTETrait implements DefaultContainer {

    private final int maxValue;
    private final int minValue;
    protected int value;

    public DefaultContainerImpl(@NotNull MetaTileEntity metaTileEntity, int minValue, int maxValue) {
        super(metaTileEntity);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = getDefaultValue();
    }

    @Override
    public boolean increaseValue(int amount) {
        if (value + amount > maxValue || value + amount < minValue) return false;
        value += amount;
        this.metaTileEntity.markDirty();
        return true;
    }
}
