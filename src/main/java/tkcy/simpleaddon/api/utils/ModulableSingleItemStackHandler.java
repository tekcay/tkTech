package tkcy.simpleaddon.api.utils;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;

import lombok.Getter;
import tkcy.simpleaddon.modules.NBTLabel;

@Getter
public class ModulableSingleItemStackHandler extends GTItemStackHandler {

    private int maxPerSlot;
    private final MetaTileEntity metaTileEntity;
    private final String nbtLabel;
    private final int slotIndex = 0;
    private static final int stacksSize = 1;

    public ModulableSingleItemStackHandler(MetaTileEntity metaTileEntity, int maxPerSlot, NBTLabel nbtLabel) {
        super(metaTileEntity, stacksSize);
        this.metaTileEntity = metaTileEntity;
        this.maxPerSlot = maxPerSlot;
        this.nbtLabel = nbtLabel.name();
        super.stacks = NonNullList.withSize(stacksSize, ItemStack.EMPTY);
    }

    public void setMaxPerSlot(int maxPerSlot) {
        this.maxPerSlot = maxPerSlot;
        this.metaTileEntity.markDirty();
    }

    @Override
    public int getSlots() {
        return stacksSize;
    }

    @Override
    public void setSize(int size) {}

    @Override
    protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
        return this.maxPerSlot;
    }

    @Override
    public int getSlotLimit(int slot) {
        return this.maxPerSlot;
    }

    public ItemStack getContentStack() {
        return this.stacks.get(this.slotIndex);
    }

    public int getContentAmount() {
        return getContentStack().getCount();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setInteger(NBTLabel.SLOT_INDEX.name(), this.slotIndex);
        getContentStack().writeToNBT(itemTag);

        NBTTagList nbtTagList = new NBTTagList();
        nbtTagList.appendTag(itemTag);

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag(this.nbtLabel, nbtTagList);
        return itemTag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagList tagList1 = nbt.getTagList(this.nbtLabel, Constants.NBT.TAG_COMPOUND);

        NBTTagCompound compound = tagList1.getCompoundTagAt(0);
        int slot = compound.getInteger(NBTLabel.SLOT_INDEX.name());
        this.stacks.set(slot, new ItemStack(compound));

        onLoad();
    }
}
