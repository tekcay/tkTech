package tkcy.tktech.common.metatileentities.multiprimitive;

import java.util.List;
import java.util.function.Function;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;

import tkcy.tktech.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.tktech.api.utils.MaterialHelper;
import tkcy.tktech.api.utils.StorageUtils;
import tkcy.tktech.api.utils.units.CommonUnits;
import tkcy.tktech.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityMultiblockTank extends MetaTileEntityMultiblockStorage<IFluidHandler, FluidStack>
                                          implements MetaTileEntityStorageFormat<FluidStack> {

    private FluidPipeProperties fluidPipeProperties;
    private FilteredFluidHandler tank;

    public MetaTileEntityMultiblockTank(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId, material, isLarge);
    }

    @Override
    protected int getLayerCapacity() {
        return (int) Math.pow(10, 6) * super.getLayerCapacity();
    }

    private void setFluidPipeProperties() {
        this.fluidPipeProperties = MaterialHelper.getMaterialProperty(this.getMaterial(), PropertyKey.FLUID_PIPE);
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.liter;
    }

    @Override
    protected void initializeInventory() {
        if (this.getMaterial() == null) return;
        super.initializeInventory();

        setFluidPipeProperties();

        this.tank = new FilteredFluidHandler(getTotalCapacity());
        this.tank.setFilter(MaterialHelper.getPropertyFluidFilter(getMaterial()));
        this.exportFluids = this.importFluids = new FluidTankList(true, this.tank);
        this.fluidInventory = this.tank;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityMultiblockTank(metaTileEntityId, getMaterial(), isLarge());
    }

    @Override
    protected void updateFormedValid() {
        this.tank.setCapacity(getTotalCapacity());
    }

    @Override
    protected Capability<IFluidHandler> getCapability() {
        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Override
    protected IFluidHandler getHandler() {
        return getCapability().cast(this.fluidInventory);
    }

    @Override
    protected TraceabilityPredicate getTransferPredicate() {
        return metaTileEntities(StorageModule.getTankValve(getMaterial()));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format(
                "tkcysa.multiblock.modulable_storage.layer_infos", getCapacityPerLayerFormatted(), getMaxSideLength()));

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature",
                    this.fluidPipeProperties.getMaxFluidTemperature()));
            if (this.fluidPipeProperties.isAcidProof()) {
                tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
            }
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.fluid_pipe_hold_shift"));
        }
    }

    @Override
    public Function<FluidStack, String> getContentLocalizedNameProvider() {
        return FluidStack::getLocalizedName;
    }

    @Override
    public Function<FluidStack, Integer> getContentAmountProvider() {
        return fluidStack -> fluidStack.amount;
    }

    @Override
    public StorageUtils<FluidStack> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    @Nullable
    public FluidStack getContent() {
        return this.fluidInventory.drain(Integer.MAX_VALUE, false);
    }
}
