package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.*;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack>
                                           implements MetaTileEntityStorageFormat<ItemStack>, IDataInfoProvider {

    private GTItemStackHandler singleSlotItemHandler;
    private ItemStack storedItemStack = ItemStack.EMPTY;

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
        // this.singleSlotItemHandler = new ModulableSingleItemStackHandler2(this, this.totalCapacity);
        this.itemInventory = new GTItemStackHandler(this, 1);
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0) {
            if (!this.itemInventory.getStackInSlot(0).isItemEqual(this.storedItemStack)) {
                this.itemInventory.insertItem(0, this.storedItemStack, false);
            }

            this.itemInventory.insertItem(0, this.storedItemStack, false);
            GTTransferUtils.moveInventoryItems(this.importItems, this.itemInventory);
            this.storedItemStack = this.itemInventory.getStackInSlot(0);
            GTTransferUtils.moveInventoryItems(this.itemInventory, this.exportItems);
            this.storedItemStack = this.itemInventory.getStackInSlot(0);
        }
    }

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected IItemHandler getHandler() {
        return getCapability().cast(this.itemInventory);
    }

    @Override
    protected TraceabilityPredicate getTransferPredicate() {
        return new TraceabilityPredicate()
                .or(abilities(MultiblockAbility.IMPORT_ITEMS))
                .or(abilities(MultiblockAbility.EXPORT_ITEMS));
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
        if (this.itemInventory == null) return ItemStack.EMPTY;
        return itemInventory.getStackInSlot(0);
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
    public @NotNull List<ITextComponent> getDataInfo() {
        List<ITextComponent> textComponents = new ArrayList<>();/*
                                                                 * ItemStack first =
                                                                 * this.singleSlotItemHandler.getStackInSlot(0);
                                                                 * textComponents.add(new TextComponentTranslation(
                                                                 * "behavior.tricorder.itemStack",
                                                                 * "this.singleSlotItemHandler.getStackInSlot(0)",
                                                                 * first.getCount(), first.getDisplayName()));
                                                                 * 
                                                                 * textComponents
                                                                 * .add(new
                                                                 * TextComponentTranslation("behavior.tricorder.size",
                                                                 * "this.singleSlotItemHandler.getSlots",
                                                                 * this.singleSlotItemHandler.getSlots()));
                                                                 */

        ItemStack second = this.itemInventory.getStackInSlot(0);
        textComponents.add(new TextComponentTranslation("behavior.tricorder.itemStack",
                "this.itemInventory.getStackInSlot(0)", second.getCount(),
                second.getDisplayName()));

        ItemStack third = this.itemInventory.getStackInSlot(1);
        textComponents.add(new TextComponentTranslation("behavior.tricorder.itemStack",
                "this.itemInventory.getStackInSlot(1)", third.getCount(),
                third.getDisplayName()));

        return textComponents;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        NBTTagCompound stackTag = new NBTTagCompound();
        this.storedItemStack.writeToNBT(stackTag);
        data.setTag("storedItem", stackTag);
        // storedItemStack.writeToNBT(stackTag);
        // data.setTag(STORED_ITEM, stackTag);
        // data.setInteger(ITEM_COUNT, this.itemStackSize);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        // this.itemStackSize = data.getInteger(ITEM_COUNT);
        // this.storedItemStack = new ItemStack(data.getCompoundTag(STORED_ITEM));
        this.storedItemStack = new ItemStack(data.getCompoundTag("storedItem"));
        // this.itemInventory.insertItem(0, new ItemStack(data.getCompoundTag("storedItem")), false);
    }
    /*
     * @Override
     * public void writeInitialSyncData(PacketBuffer buf) {
     * super.writeInitialSyncData(buf);
     * buf.writeInt(itemStackSize);
     * }
     * 
     * @Override
     * public void receiveInitialSyncData(PacketBuffer buf) {
     * super.receiveInitialSyncData(buf);
     * itemStackSize = buf.readInt();
     * }
     */
}
