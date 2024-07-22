package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
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

import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.*;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack>
                                           implements MetaTileEntityStorageFormat<ItemStack> {

    private ItemStack itemStackFilter;
    private MassiveSingleFilterItemStackHandler2 storeHandler;

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
        this.layerCapacity = (int) Math.pow(10, 4) * (isLarge ? 21 : 1);
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        this.storeHandler = new MassiveSingleFilterItemStackHandler2(this, this.totalCapacity);
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge);
    }
    /*
     * private boolean isStackValidForFilter(@Nullable ItemStack itemStackFilter) {
     * if (itemStackFilter == null) return false;
     * return itemStackFilter.getItem() != Items.AIR;
     * }
     * 
     * private void tryToTransfer() {
     * 
     * int maxAmountToTransfer = getInitStream(this.importItems)
     * .map(slotIndex -> this.importItems.getStackInSlot(slotIndex))
     * .mapToInt(ItemStack::getCount)
     * .sum();
     * 
     * if (maxAmountToTransfer == 0) return;
     * 
     * 
     * Map<Integer, ItemStack> slotIndexToItemStack = new HashMap<>(this.importItems.getSlots());
     * 
     * getInitStream(this.importItems)
     * .sorted()
     * .map(slotIndex -> this.importItems.getStackInSlot(slotIndex))
     * .forEach(slotIndexToItemStack.values()::add);
     * }
     */

    @Override
    protected void updateFormedValid() {
        if (getOffsetTimer() % 10 == 0) {
            this.storeHandler.setMaxPerSlot(this.totalCapacity);
            GTTransferUtils.moveInventoryItems(this.importItems, this.storeHandler);
            GTTransferUtils.moveInventoryItems(this.storeHandler, this.exportItems);
        }
    }
    /*
     * @Override
     * protected void updateFormedValid2() {
     * 
     * if (!this.storeHandler.hasItemStackFilter()) setStackFilter();
     * 
     * if (!this.storeHandler.isFilled()) {
     * 
     * int maxToRemove = getInitStream(this.importItems)
     * .map(slotIndex -> this.importItems.extractItem(slotIndex, Integer.MAX_VALUE, true))
     * .filter(this.storeHandler.getItemStackFilter()::isItemEqual)
     * .mapToInt(ItemStack::getCount)
     * .sum();
     * 
     * int toRemove = this.storeHandler.insertItem(maxToRemove, true);
     * 
     * if (toRemove > 0) {
     * this.storeHandler.insertItem(toRemove, false);
     * }
     * 
     * 
     * }
     * this.storeHandler.tryToExtractItem(this.exportItems, false);
     * }
     * 
     * private void log(String message) {
     * if (getOffsetTimer() % 20 == 0) {
     * GTLog.logger.info(message);
     * }
     * }
     * 
     * private Stream<Integer> getInitStream(IItemHandler itemHandler) {
     * return IntStream
     * .range(0, itemHandler.getSlots())
     * .boxed();
     * }
     * 
     * private void setStackFilter() {
     * this.itemStackFilter = getInitStream(this.importItems)
     * .map(slotIndex -> this.importItems.extractItem(slotIndex, Integer.MAX_VALUE, true))
     * .filter(this::isStackValidForFilter)
     * .findAny()
     * .orElse(ItemStack.EMPTY);
     * this.storeHandler.setItemStackFilter(this.itemStackFilter);
     * }
     */

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected IItemHandler getHandler() {
        return getCapability().cast(this.storeHandler);
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
        if (this.storeHandler == null) return null;
        return this.storeHandler.getStackInSlot(0);
    }
    /*
     * 
     * @Override
     * 
     * @Nullable
     * public ItemStack getContent() {
     * if (this.storeHandler == null) return null;
     * ItemStack content = new ItemStack(this.itemStackFilter.getItem());
     * getInitStream(this.storeHandler)
     * .map(slotIndex -> this.storeHandler.getStackInSlot(slotIndex))
     * .map(ItemStack::getCount)
     * .forEach(content::grow);
     * return content;
     * }
     */

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
