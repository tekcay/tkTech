package tkcy.simpleaddon.common.metatileentities.storage;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidHandlerProxy;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityModulableTankValve extends MetaTileEntityModulableValve<IFluidHandler>
                                              implements IMultiblockAbilityPart<IFluidHandler> {

    public MetaTileEntityModulableTankValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, material);
    }

    @Override
    public IFluidHandler getHandler(@Nullable TileEntity tileEntity) {
        return tileEntity == null ?
                null : tileEntity.getCapability(this.getCapability(), getFrontFacing().getOpposite());
    }

    @Override
    protected int transferInventoryToHandler(IFluidHandler handler) {
        return GTTransferUtils.transferFluids(fluidInventory, handler);
    }

    @Override
    protected Capability<IFluidHandler> getCapability() {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        super.addToMultiBlock(controllerBase);
        this.fluidInventory = controllerBase.getFluidInventory(); // directly use controllers fluid inventory as there
        // is no reason to proxy it
    }

    @Override
    protected void initializeDummyInventory() {
        this.fluidInventory = new FluidHandlerProxy(new FluidTankList(false), new FluidTankList(false));
    }

    @Override
    public MultiblockAbility<IFluidHandler> getAbility() {
        return MultiblockAbility.TANK_VALVE;
    }

    @Override
    public void registerAbilities(@NotNull List<IFluidHandler> abilityList) {
        abilityList.add(this.getImportFluids());
    }
}
