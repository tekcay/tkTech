package tkcy.simpleaddon.common.metatileentities.storage;

import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import gregtech.api.capability.impl.ItemHandlerProxy;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbilities;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityModulableCrateValve extends MetaTileEntityModulableValve<IItemHandler>
                                               implements IMultiblockAbilityPart<IItemHandler> {

    public MetaTileEntityModulableCrateValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, material);
    }

    // TODO might happen around here
    @Override
    protected void initializeDummyInventory() {
        this.itemInventory = new ItemHandlerProxy(new ItemStackHandler(), new ItemStackHandler());
    }

    @Override
    protected void autoOutputInventory(IItemHandler handler) {
        GTTransferUtils.moveInventoryItems(this.itemInventory, handler);
    }

    @Override
    protected Capability<IItemHandler> getCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.itemInventory = controllerBase.getItemInventory(); // directly
                                                                // use
                                                                // controllers
                                                                // item
                                                                // inventory
                                                                // as
                                                                // there
        // is no reason to proxy it
    }

    @Override
    public MultiblockAbility<IItemHandler> getAbility() {
        return TKCYSAMultiblockAbilities.CRATE_VALVE;
    }

    @Override
    public void registerAbilities(List<IItemHandler> abilityList) {
        abilityList.add(this.getImportItems());
    }
}
