package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import gregtech.api.util.GTUtility;
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

import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbilities;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockChest extends MetaTileEntityMultiblockStorage<IItemHandler, List<ItemStack>> {

    @Getter
    private GTItemStackHandler storage;

    public MetaTileEntityMultiblockChest(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected int getLayerCapacity() {
        return 72 * super.getLayerCapacity();
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();
        this.storage = new GTItemStackHandler(this, getLayerCapacity() * getMaxSideLength());
        initHandlers();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        if (this.storage.getSlots() != getTotalCapacity()) {
            resizeStorageHandler();
        }
    }

    private void initHandlers() {
        importItems = exportItems = new ItemHandlerList(Collections.singletonList(this.storage));
        itemInventory = storage;
    }

    private void resizeStorageHandler() {
        GTItemStackHandler tempoHandler = new GTItemStackHandler(this, getTotalCapacity());
        GTTransferUtils.moveInventoryItems(this.storage, tempoHandler);
        this.storage.setSize(getTotalCapacity());
        GTTransferUtils.moveInventoryItems(tempoHandler, this.storage);
        initHandlers();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockChest(metaTileEntityId, getMaterial(), isLarge());
    }

    @Override
    protected void updateFormedValid() {}

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
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_chest.tooltip.1"));
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_chest.tooltip.2"));
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_storage.layer_infos",
                getCapacityPerLayerFormatted(), getMaxSideLength()));
    }

    @Override
    public Function<List<ItemStack>, String> getContentLocalizedNameProvider() {
        return itemStacks -> "used";
    }

    @Override
    public String getLinkingWordForContentDisplay() {
        return " ";
    }

    @Override
    public Function<List<ItemStack>, Integer> getContentAmountProvider() {
        return itemStacks -> Math.toIntExact(getTotalCapacity() - itemStacks.stream()
                .filter(ItemStack::isEmpty)
                .count());
    }

    @Override
    public StorageUtils<List<ItemStack>> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    @Nullable
    public List<ItemStack> getContent() {
        return GTUtility.itemHandlerToList(this.storage);
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.stack;
    }


}
