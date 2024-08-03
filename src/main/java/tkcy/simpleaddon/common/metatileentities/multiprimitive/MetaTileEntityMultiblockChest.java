package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.gui.Widget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;
import gregtech.common.metatileentities.storage.MetaTileEntityCrate;

import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbilities;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockChest extends MetaTileEntityMultiblockStorage<IItemHandler, ItemStack> {

    private GTItemStackHandler storage;
    private int page = 1;

    public MetaTileEntityMultiblockChest(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.initializeInventory();
    }

    @Override
    protected void setLayerCapacity(boolean isLarge) {
        this.layerCapacity = (int) Math.pow(10, 2) * (isLarge ? 21 : 1);
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();

        this.storage = new GTItemStackHandler(this, this.totalCapacity);
        importItems = exportItems = new ItemHandlerList(Collections.singletonList(this.storage));
        itemInventory = this.storage;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockChest(metaTileEntityId, getMaterial(), isLarge);
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

    /**
     * From {@link MetaTileEntityCrate}
     */

    /*
     * @Override
     * protected ModularUI createUI(EntityPlayer entityPlayer) {
     * 
     * GTLog.logger.info("this.storage.getSlots() : " + this.storage.getSlots());
     * 
     * int slotsPerPage = 60;
     * 
     * int factor = slotsPerPage / 9 > 8 ? 18 : 9;
     * 
     * ModularUI.Builder builder = ModularUI
     * .builder(GuiTextures.BACKGROUND, 400, 112 + slotsPerPage)
     * .widget(getFlexButton(370, 200, 20, 60))
     * .label(5, 5, getMetaFullName());
     * 
     * for (int i = 0; i < slotsPerPage; i++) {
     * builder.slot(this.storage, i, 7 * (factor == 18 ? 2 : 1) + i % factor * 18, 18 + i / factor * 18,
     * GuiTextures.SLOT);
     * }
     * builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7 + (factor == 18 ? 88 : 0),
     * 18 + slotsPerPage / factor * 18 + 11);
     * return builder.build(getHolder(), entityPlayer);
     * }
     */

    private void decrementPageIndex(Widget.ClickData clickData) {
        if (--page >= 0) this.page--;
    }

    private void incrementPageIndex(Widget.ClickData clickData) {
        this.page++;
    }
    /*
     * 
     * @Override
     * protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
     * WidgetGroup group = new WidgetGroup(x, y, width, height);
     * group.addWidget(new ClickButtonWidget(380, 20, 9, 18, "", this::decrementPageIndex)
     * .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
     * .setTooltipText("gregtech.multiblock.large_boiler.throttle_decrement"));
     * group.addWidget(new ClickButtonWidget(380, 40, 9, 18, "", this::incrementPageIndex)
     * .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
     * .setTooltipText("gregtech.multiblock.large_boiler.throttle_increment"));
     * return group;
     * }
     */

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag(NBTLabel.ITEM_INVENTORY.name(), this.storage.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.storage.deserializeNBT(data.getCompoundTag(NBTLabel.ITEM_INVENTORY.name()));
    }
}
