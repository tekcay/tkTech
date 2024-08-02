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
import gregtech.common.metatileentities.MetaTileEntities;

import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.*;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.TKCYSADataCodes;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack>
                                           implements MetaTileEntityStorageFormat<ItemStack> {

    private ModulableSingleItemStackHandler2 storedStackHandler;
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
    protected void setLayerCapacity(boolean isLarge) {
        this.layerCapacity = (int) Math.pow(10, 4) * (isLarge ? 21 : 1);
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        initStoredStackHandler();
        itemInventory = this.storedStackHandler;
        importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    protected void initStoredStackHandler() {
        if (this.storedItemStack == null || this.storedItemStack.isEmpty()) {
            this.storedStackHandler = new ModulableSingleItemStackHandler2(this, totalCapacity);
        } else {
            this.storedStackHandler = new ModulableSingleItemStackHandler2(this, totalCapacity, this.storedItemStack);
        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge);
    }

    protected boolean isImportEmpty() {
        return this.importItems.getStackInSlot(0).isEmpty();
    }

    protected void updateStoredItemStack(@NotNull ItemStack itemStack) {
        this.storedItemStack = itemStack;
        this.storedQuantity = itemStack.getCount();

        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_STACK, TKCYSADataCodes.getItemStackWriter(this.storedItemStack));
        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_COUNT, TKCYSADataCodes.getInt(this.storedQuantity));
    }

    protected void setStoredItemStack() {
        this.storedItemStack = this.itemStackFilter.copy();
        this.storedItemStack.setCount(this.storedQuantity);
    }

    protected void updateStoredItemStack() {
        this.storedItemStack = this.storedStackHandler.getContent();
        this.storedQuantity = this.storedStackHandler.getContent().getCount();
        this.itemStackFilter = ItemHandlerHelpers.copyWithAmount(this.storedItemStack, 1);

        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_STACK,
                TKCYSADataCodes.getItemStackWriter(this.itemStackFilter));
        writeCustomData(TKCYSADataCodes.UPDATE_ITEM_COUNT, TKCYSADataCodes.getInt(this.storedQuantity));
    }

    protected void updateFormedValidFormer() {
        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0) {

            if (this.storedStackHandler.getContent() != this.storedItemStack) {
                this.storedStackHandler.setStackInSlot(0, this.storedItemStack);
            }

            ItemStack previousStack = this.storedItemStack.copy();

            if (this.storedStackHandler.getContent() != previousStack) {

                if (importItems.getSlots() != 0) {

                    if (!isImportEmpty() && !this.storedStackHandler.isFull()) {
                        GTTransferUtils.moveInventoryItems(importItems, this.storedStackHandler);
                    }

                    if (this.storedStackHandler.getContent() != previousStack) {
                        updateStoredItemStack(this.storedStackHandler.getContent());
                    }
                }

                if (this.storedStackHandler.isEmpty()) return;

                if (exportItems.getSlots() != 0 &&
                        previousStack.getCount() != this.storedStackHandler.getContent().getCount()) {

                    this.storedStackHandler.transferToHandler(exportItems);

                    if (this.storedStackHandler.getContent() != previousStack) {

                        if (this.storedStackHandler.isEmpty()) {
                            updateStoredItemStack(ItemStack.EMPTY);
                        } else if (this.storedItemStack != this.storedStackHandler.getContent()) {
                            updateStoredItemStack(this.storedStackHandler.getContent());
                        }
                    }
                }
            }
        }
    }

    private int ticks = 5;

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && getOffsetTimer() % ticks == 0) {

            if (!this.storedStackHandler.getContent().isItemEqual(this.itemStackFilter)) {
                this.storedStackHandler.setStackInSlot(0, this.storedItemStack);
            }

            if (importItems.getSlots() != 0) {

                if (!isImportEmpty() && !this.storedStackHandler.isFull()) {
                    GTTransferUtils.moveInventoryItems(importItems, this.storedStackHandler);
                }
            }

            int maxToTransfer = this.storedStackHandler.getMaxTransferredAmount(exportItems);
            if (maxToTransfer != 0) {
                this.storedStackHandler.export(exportItems, maxToTransfer);
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
                .or(metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS[0])
                        .setMaxGlobalLimited(1));
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
    public String getPercentageTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.fill.percentage";
    }

    @Override
    public String getCapacityTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.capacity";
    }

    @Override
    public String getContentTextTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.content";
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
