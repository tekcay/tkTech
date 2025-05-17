package tkcy.tktech.api.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.client.renderer.ICubeRenderer;

import tkcy.tktech.api.recipes.logic.markers.IHideEnergyRecipeLogic;

public abstract class PrimitiveSingleBlock extends MetaTileEntity {

    protected AbstractRecipeLogic primitiveLogic;
    protected final ICubeRenderer renderer;

    public PrimitiveSingleBlock(ResourceLocation metaTileEntityId, RecipeMap<?> recipeMap, ICubeRenderer renderer) {
        super(metaTileEntityId);
        this.primitiveLogic = new Logic(this, recipeMap);
        this.renderer = renderer;
    }

    @SideOnly(Side.CLIENT)
    protected abstract ICubeRenderer getBaseRenderer();

    protected abstract SoundEvent getRecipeSound();

    @Override
    public boolean isActive() {
        return primitiveLogic.isActive() && primitiveLogic.isWorkingEnabled();
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, true);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player).build(getHolder(), player);
    }

    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.PRIMITIVE_BACKGROUND, 176, 166)
                .shouldColor(false)
                .widget(new LabelWidget(5, 5, getMetaFullName()))

                .widget(new TankWidget(importFluids.getTankAt(0), 30, 30, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .slot(this.importItems, 0, 60, 30, GuiTextures.PRIMITIVE_SLOT)

                .progressBar(primitiveLogic::getProgressPercent, 85, 30, 25, 18,
                        GuiTextures.PRIMITIVE_BLAST_FURNACE_PROGRESS_BAR,
                        ProgressWidget.MoveType.HORIZONTAL, primitiveLogic.getRecipeMap())

                .widget(new SlotWidget(this.exportItems, 0, 120, 30, true, false)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            IFluidHandler fluidHandler = fluidInventory;
            if (fluidHandler.getTankProperties().length > 0) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
            }
            return null;
        } else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            IItemHandler itemHandler = itemInventory;
            if (itemHandler.getSlots() > 0) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler);
            }
            return null;
        }
        return super.getCapability(capability, side);
    }

    protected static class Logic extends AbstractRecipeLogic implements IHideEnergyRecipeLogic {

        public Logic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        protected long getEnergyInputPerSecond() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected long getEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected long getEnergyCapacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected boolean drawEnergy(long l, boolean b) {
            return true;
        }

        @Override
        public long getMaxVoltage() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean consumesEnergy() {
            return false;
        }
    }
}
