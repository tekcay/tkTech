package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbilities;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockChest extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack> {

    @Getter
    private GTItemStackHandler storage;

    public MetaTileEntityMultiblockChest(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void setLayerCapacity(boolean isLarge) {
        this.layerCapacity = (int) Math.pow(10, 2) * (isLarge ? 21 : 1);
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        this.storage = new GTItemStackHandler(this, 30);
        importItems = exportItems = new ItemHandlerList(Collections.singletonList(this.storage));
        itemInventory = this.storage;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockChest(metaTileEntityId, getMaterial(), isLarge);
    }

    @Override
    protected void updateFormedValid() {
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
        return abilities(TKCYSAMultiblockAbilities.CHEST_VALVE);
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
        return itemStack -> "bla";
    }

    @Override
    public Function<ItemStack, Integer> getContentAmountProvider() {
        return itemStack -> 3;
    }

    @Override
    public StorageUtils<ItemStack> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    @Nullable
    public ItemStack getContent() {
        return ItemStack.EMPTY;
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.stack;
    }

    @Override
    public String getPercentageTranslationKey() {
        return null;
    }

    @Override
    public String getCapacityTranslationKey() {
        return null;
    }

    @Override
    public String getContentTextTranslationKey() {
        return null;
    }

    @Override
    public void displayInfos(List<ITextComponent> textList) {}
}
