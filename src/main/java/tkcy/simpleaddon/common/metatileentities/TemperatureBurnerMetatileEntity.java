package tkcy.simpleaddon.common.metatileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.gui.widgets.TankWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;
import gregtech.client.utils.RenderUtil;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.api.capabilities.TemperatureContainer;
import tkcy.simpleaddon.api.capabilities.helpers.AdjacentCapabilityHelper;
import tkcy.simpleaddon.api.capabilities.helpers.MultipleContainerWrapper;
import tkcy.simpleaddon.api.capabilities.impl.HeatContainerImpl;
import tkcy.simpleaddon.api.capabilities.impl.TemperatureContainerImpl;
import tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.SupplierContainerMetatileEntity;
import tkcy.simpleaddon.api.recipes.logic.HeatLogic;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;
import tkcy.simpleaddon.modules.capabilitiesmodule.Machines;

public class TemperatureBurnerMetatileEntity extends SupplierContainerMetatileEntity
                                             implements AdjacentCapabilityHelper, Machines.HeatMachine,
                                             Machines.TemperatureMachine {

    @Getter
    private final MultipleContainerWrapper containerWrapper;
    private final HeatLogic workableHandler;

    public TemperatureBurnerMetatileEntity(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.workableHandler = new HeatLogic(this, TKCYSARecipeMaps.HEATING_RECIPES, false);
        this.containerWrapper = new MultipleContainerWrapper.MultipleContainerWrapperBuilder()
                .addContainer(new HeatContainerImpl(this, 0, 40000))
                .addContainer(new TemperatureContainerImpl(this, 298, 1000))
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new TemperatureBurnerMetatileEntity(metaTileEntityId);
    }

    @Override
    protected void doSomething() {
        tryToEmit(getEmittingFace());
    }

    @Override
    public void tryToEmit(EnumFacing emittingSide) {
        if (getHeatContainer() == null || getHeatContainer().isEmpty()) return;
        int currentHeat = getHeatContainer().getValue();

        if (getTemperatureContainer() != null) {
            getTemperatureContainer().setValue(currentHeat);
        }
        HeatContainer adjacentHeatContainer = getAdjacentCapabilityContainer(
                TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER);
        if (adjacentHeatContainer != null) {
            adjacentHeatContainer.increaseValue(currentHeat);
            getHeatContainer().increaseValue(-currentHeat);
        }
    }

    @Override
    public EnumFacing getEmittingFace() {
        return EnumFacing.UP;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        getBaseRenderer().render(renderState, translation, pipeline);
        Textures.FURNACE_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), false,
                false);
        Textures.STEAM_VENT_OVERLAY.renderSided(EnumFacing.UP, renderState,
                RenderUtil.adjustTrans(translation, EnumFacing.UP, 2), pipeline);

        // renderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(),
        // workableHandler.isActive(),
        // workableHandler.isWorkingEnabled());
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
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

                .widget(new TankWidget(exportFluids.getTankAt(0), 150, 30, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true)
                        .setContainerClicking(true, true))

                .slot(this.importItems, 0, 60, 30, GuiTextures.PRIMITIVE_SLOT)

                .progressBar(workableHandler::getProgressPercent, 85, 30, 25, 18,
                        GuiTextures.PRIMITIVE_BLAST_FURNACE_PROGRESS_BAR,
                        ProgressWidget.MoveType.HORIZONTAL, workableHandler.getRecipeMap())

                .widget(new SlotWidget(this.exportItems, 0, 120, 30, true, false)
                        .setBackgroundTexture(GuiTextures.PRIMITIVE_SLOT))

                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 0);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.STEAM_BRICKED_CASING_STEEL;
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
    protected FluidTankList createExportFluidHandler() {
        return new FluidTankList(false, new FluidTank(2000));
    }

    @Override
    @Nullable
    public HeatContainer getHeatContainer() {
        return (HeatContainer) this.containerWrapper.getContainer(CapabilityModule.ContainerType.HEAT);
    }

    @Override
    public @Nullable TemperatureContainer getTemperatureContainer() {
        return (TemperatureContainer) this.containerWrapper.getContainer(CapabilityModule.ContainerType.TEMPERATURE);
    }
}
