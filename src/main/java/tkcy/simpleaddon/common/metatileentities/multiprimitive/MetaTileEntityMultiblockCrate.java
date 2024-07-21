package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.ItemHandlerHelpers;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack>
                                           implements MetaTileEntityStorageFormat<ItemStack> {

    private ItemStack itemStackFilter;

    public MetaTileEntityMultiblockCrate(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeInventory();
        this.itemStackFilter = ItemStack.EMPTY;
    }

    @Override
    protected void setLayerCapacity(boolean isLarge) {
        this.layerCapacity = (int) Math.pow(10, 2) * (isLarge ? 21 : 1);
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        this.itemInventory = new GTItemStackHandler(this, this.totalCapacity);
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge);
    }

    private boolean isStackValidForFilter(@Nullable ItemStack itemStackFilter) {
        if (itemStackFilter == null) return false;
        if (itemStackFilter.isItemEqual(ItemStack.EMPTY)) return false;
        return itemStackFilter.getItem() != Items.AIR;
    }

    private boolean hasStackFilter() {
        return isStackValidForFilter(this.itemStackFilter);
    }

    @Override
    protected void updateFormedValid() {
        if (!hasStackFilter()) {
            log("NO STACK FILTER");
            setStackFilter();
        }
        if (hasStackFilter()) {
            log(String.format("HAS STACK FILTER : %s", this.itemStackFilter.getDisplayName()));
            ItemHandlerHelpers.moveInventoryItemsFiltered(this.importItems, this.itemInventory, this.itemStackFilter);
            // tryToFill();
        }
        tryToEmpty();
    }

    private void log(String message) {
        if (getOffsetTimer() % 20 == 0) {
            GTLog.logger.info(message);
        }
    }

    protected int moveInventoryItems(IItemHandler sourceInventory, IItemHandler targetInventory,
                                     int maxTransferAmount) {
        int itemsLeftToTransfer = maxTransferAmount;
        for (int srcIndex = 0; srcIndex < sourceInventory.getSlots(); srcIndex++) {
            ItemStack sourceStack = sourceInventory.extractItem(srcIndex, itemsLeftToTransfer, true);
            if (sourceStack.isEmpty()) {
                continue;
            }
            if (!this.itemStackFilter.isItemEqual(sourceStack)) {
                continue;
            }
            ItemStack remainder = GTTransferUtils.insertItem(targetInventory, sourceStack, true);
            int amountToInsert = sourceStack.getCount() - remainder.getCount();

            if (amountToInsert > 0) {
                sourceStack = sourceInventory.extractItem(srcIndex, amountToInsert, false);
                if (!sourceStack.isEmpty()) {
                    GTTransferUtils.insertItem(targetInventory, sourceStack, false);
                    itemsLeftToTransfer -= sourceStack.getCount();

                    if (itemsLeftToTransfer == 0) {
                        break;
                    }
                }
            }
        }
        return maxTransferAmount - itemsLeftToTransfer;
    }

    private void tryToFill() {
        int maxToExtract = getInitStream(this.importItems)
                .map(slotIndex -> this.importItems.extractItem(slotIndex, Integer.MAX_VALUE, true))
                .filter(this::isStackValidForFilter)
                .filter(this.itemStackFilter::isItemEqual)
                .mapToInt(ItemStack::getCount)
                .sum();

        if (maxToExtract == 0) return;

        // moveInventoryItems(this.importItems, this.itemInventory, maxToExtract);

        ItemStack extractedStack;
        ItemStack remainder;

        for (int slotIndex = 0; slotIndex < this.importItems.getSlots(); slotIndex++) {
            int toRemove = Math.min(64, maxToExtract);
            GTLog.logger.info("toRemove : " + toRemove);

            extractedStack = this.importItems.extractItem(slotIndex, toRemove, true);
            if (extractedStack.isEmpty()) {
                GTLog.logger.info("extractedStack.isEmpty()");
                continue;
            }

            remainder = GTTransferUtils.insertItem(this.itemInventory, extractedStack, true);
            if (remainder.isEmpty()) {
                GTLog.logger.info("remainder.isEmpty()");
                continue;
            }

            extractedStack = this.importItems.extractItem(slotIndex, toRemove, false);
            GTLog.logger.info(
                    String.format("extracted : %d %s", extractedStack.getCount(), extractedStack.getDisplayName()));
            remainder = GTTransferUtils.insertItem(this.itemInventory, extractedStack, false);
            GTLog.logger.info(String.format("remainder : %d %s", remainder.getCount(), remainder.getDisplayName()));

            maxToExtract -= remainder.getCount();
            if (maxToExtract == 0) return;
        }
    }

    private void tryToEmpty() {
        GTTransferUtils.moveInventoryItems(this.itemInventory, this.exportItems);
    }

    private Stream<Integer> getInitStream(IItemHandler itemHandler) {
        return IntStream
                .range(0, itemHandler.getSlots())
                .boxed();
    }

    private void setStackFilter() {
        this.itemStackFilter = getInitStream(this.importItems)
                .map(slotIndex -> this.importItems.extractItem(slotIndex, Integer.MAX_VALUE, true))
                .filter(this::isStackValidForFilter)
                .findAny()
                .orElse(ItemStack.EMPTY);
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
        if (this.itemInventory == null) return null;
        ItemStack content = new ItemStack(this.itemStackFilter.getItem());
        getInitStream(this.itemInventory)
                .map(slotIndex -> this.itemInventory.getStackInSlot(slotIndex))
                .map(ItemStack::getCount)
                .forEach(content::grow);
        return content;
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.liter;
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
}
