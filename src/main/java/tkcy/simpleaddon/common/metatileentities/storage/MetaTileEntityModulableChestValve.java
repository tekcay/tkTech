package tkcy.simpleaddon.common.metatileentities.storage;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbilities;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityModulableChestValve extends MetaTileEntityModulableValve<IItemHandler>
                                               implements IMultiblockAbilityPart<IItemHandler>, IDataInfoProvider {

    public MetaTileEntityModulableChestValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, material);
    }

    // TODO might happen around here
    @Override
    protected void initializeDummyInventory() {
        this.itemInventory = new ItemHandlerProxy(new ItemStackHandler(), new ItemStackHandler());
    }

    @Override
    protected void autoOutputInventory(IItemHandler handler) {}

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableChestValve(metaTileEntityId, getMaterial());
    }

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected boolean doesAutoOutput() {
        return false;
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.itemInventory = controllerBase.getItemInventory();
    }

    @Override
    public MultiblockAbility<IItemHandler> getAbility() {
        return TKCYSAMultiblockAbilities.CHEST_VALVE;
    }

    @Override
    public void registerAbilities(List<IItemHandler> abilityList) {
        abilityList.add(this.getImportItems());
    }

    @Override
    public @NotNull List<ITextComponent> getDataInfo() {
        List<ITextComponent> list = new ObjectArrayList<>();
        list.add(new TextComponentTranslation("behavior.tricorder.size", "lkh", itemInventory.getSlots()));
        return list;
    }
}
