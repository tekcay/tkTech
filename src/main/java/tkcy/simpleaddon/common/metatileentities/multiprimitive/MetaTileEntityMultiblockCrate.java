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
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.utils.ItemHandlerHelpers;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.TKFilteredItemHandler;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockCrate extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack>
                                           implements MetaTileEntityStorageFormat<ItemStack> {

    private TKFilteredItemHandler itemHandler;

    public MetaTileEntityMultiblockCrate(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void initializeAbilities() {
        this.importItems = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.exportItems = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.itemHandler = new TKFilteredItemHandler(this, this.totalCapacity);
    }

    @Override
    protected void setLayerCapacity(boolean isLarge) {
        this.layerCapacity = (int) Math.pow(10, 3) * (isLarge ? 21 : 1) * 64;
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockCrate(metaTileEntityId, getMaterial(), isLarge);
    }

    @Override
    protected void updateFormedValid() {
        ItemStack predicate = this.itemHandler.getPredicateStack();
        if (!predicate.isEmpty()) GTLog.logger.info("filter is here : " + predicate.getDisplayName());
        this.itemHandler.updateContent();
        GTTransferUtils.moveInventoryItems(this.itemHandler, this.exportItems);
        if (!this.itemHandler.hasItemFillPredicate()) {
            if (!this.itemHandler.trySetFillPredicate(this.importItems)) return;
        }
        ItemHandlerHelpers.moveHandlerToFiltered(this.importItems, this.itemHandler);
    }

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected IItemHandler getHandler() {
        return getCapability().cast(this.itemHandler);
    }

    @Override
    protected TraceabilityPredicate getTransferMetatileEntity() {
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
        if (this.itemHandler == null) return null;
        this.itemHandler.updateContent();
        return this.itemHandler.getContentStack();
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
