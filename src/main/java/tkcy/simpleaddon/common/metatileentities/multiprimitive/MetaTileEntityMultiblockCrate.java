package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.utils.*;
import tkcy.simpleaddon.api.utils.item.ItemHandlerHelpers;
import tkcy.simpleaddon.api.utils.item.ModulableSingleItemStackHandler;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.TKCYSADataCodes;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack> {

    private ModulableSingleItemStackHandler storedStackHandler;
    private ItemStack storedItemStack = ItemStack.EMPTY;
    private ItemStack itemStackFilter = ItemStack.EMPTY;
    private int storedQuantity = 0;

    public MetaTileEntityMultiblockCrate(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeInventory();
    }

    @Override
    protected int getLayerCapacity() {
        return (int) Math.pow(10, 4) * super.getLayerCapacity();
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        this.storedStackHandler = new ModulableSingleItemStackHandler(this, getTotalCapacity());
        itemInventory = this.storedStackHandler;
        importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge());
    }

    protected boolean isImportHandlerWorkable() {
        return ItemHandlerHelpers.handlerToStacksInSlots.apply(importItems)
                .filter(itemStack -> !itemStack.isEmpty())
                .anyMatch(itemStack -> itemStack.isItemEqual(this.itemStackFilter));
    }

    protected void updateStoredItemStack() {
        this.storedItemStack = this.storedStackHandler.getContent();
        this.storedQuantity = this.storedStackHandler.getContent().getCount();
        this.itemStackFilter = ItemHandlerHelpers.copyWithAmount(this.storedItemStack, 1);

        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_STACK,
                TKCYSADataCodes.getItemStackWriter(this.itemStackFilter));
        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_COUNT, TKCYSADataCodes.getInt(this.storedQuantity));
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0) {

            if (!this.storedStackHandler.getContent().isItemEqual(this.itemStackFilter)) {
                this.storedStackHandler.setStackInSlot(0, this.storedItemStack);
            }

            if (importItems.getSlots() != 0 && !this.storedStackHandler.isFull()) {

                if (this.itemStackFilter.isEmpty() || isImportHandlerWorkable()) {
                    GTTransferUtils.moveInventoryItems(importItems, this.storedStackHandler);
                }
            }

            int maxToTransfer = this.storedStackHandler.getMaxTransferable(exportItems);
            if (maxToTransfer != 0) {
                this.storedStackHandler.exportToHandler(exportItems, maxToTransfer);
            }

            updateStoredItemStack();
        }
    }

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected IItemHandler getHandler() {
        return getCapability().cast(this.storedStackHandler);
    }

    @Override
    protected TraceabilityPredicate getTransferPredicate() {
        return new TraceabilityPredicate()
                .or(abilities(MultiblockAbility.EXPORT_ITEMS))
                .or(abilities(MultiblockAbility.IMPORT_ITEMS))
                .setMaxGlobalLimited(4, 2);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_storage.layer_infos",
                getCapacityPerLayerFormatted(), getMaxSideLength()));
    }

    @Override
    public Function<ItemStack, String> getContentLocalizedNameProvider() {
        return ItemStack::getDisplayName;
    }

    @Override
    public Function<ItemStack, Integer> getContentAmountProvider() {
        return ItemStack::getCount;
    }

    @Override
    public StorageUtils<ItemStack> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    @Nullable
    public ItemStack getContent() {
        return this.storedItemStack;
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.empty;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);

        if (!this.storedItemStack.isEmpty()) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            this.itemStackFilter.writeToNBT(nbtTagCompound);

            data.setTag(NBTLabel.ITEM_INVENTORY.name(), nbtTagCompound);
            data.setInteger(NBTLabel.ITEM_QUANTITY.name(), this.storedQuantity);
        }
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        if (data.hasKey(NBTLabel.ITEM_INVENTORY.name(), Constants.NBT.TAG_COMPOUND) &&
                data.hasKey(NBTLabel.ITEM_QUANTITY.name(), Constants.NBT.TAG_INT)) {

            this.itemStackFilter = new ItemStack(data.getCompoundTag(NBTLabel.ITEM_INVENTORY.name()));
            this.storedQuantity = data.getInteger(NBTLabel.ITEM_QUANTITY.name());
            this.storedItemStack = ItemHandlerHelpers.copyWithAmount(this.itemStackFilter, this.storedQuantity);
        }
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == TKCYSADataCodes.UPDATE_ITEM_STACK) {
            try {
                this.itemStackFilter = buf.readItemStack();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (dataId == TKCYSADataCodes.UPDATE_ITEM_COUNT) {
            this.storedQuantity = buf.readInt();
        }
    }
}
