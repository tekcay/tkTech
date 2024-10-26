package tkcy.simpleaddon.common.metatileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.TKCYSATileCapabilities;
import tkcy.simpleaddon.api.capabilities.helpers.AdjacentCapabilityHelper;
import tkcy.simpleaddon.api.capabilities.helpers.MultipleContainerWrapper;
import tkcy.simpleaddon.api.capabilities.impl.HeatContainerImpl;
import tkcy.simpleaddon.api.metatileentities.capabilitiescontainers.SupplierContainerMetatileEntity;
import tkcy.simpleaddon.api.recipes.logic.HeatLogic;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.api.utils.rendering.RenderingUtils;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;
import tkcy.simpleaddon.modules.capabilitiesmodule.Machines;

@Getter
public class BurnerMetatileEntity extends SupplierContainerMetatileEntity
                                  implements AdjacentCapabilityHelper, Machines.HeatMachine {

    private final MultipleContainerWrapper containerWrapper;
    private final HeatLogic workableHandler;
    private final Material brickMaterial;
    private final Material heatPlateMaterial;

    public BurnerMetatileEntity(ResourceLocation metaTileEntityId, Material brickMaterial, Material heatPlateMaterial) {
        super(metaTileEntityId);
        this.brickMaterial = brickMaterial;
        this.heatPlateMaterial = heatPlateMaterial;
        this.workableHandler = new HeatLogic(this, TKCYSARecipeMaps.HEAT_PRODUCING_RECIPES, false);
        this.containerWrapper = new MultipleContainerWrapper.MultipleContainerWrapperBuilder()
                .addContainer(new HeatContainerImpl(this, 40000))
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new BurnerMetatileEntity(metaTileEntityId, brickMaterial, heatPlateMaterial);
    }

    @Override
    protected void doSomething() {
        tryToEmit(getEmittingFace());
    }

    @Override
    public void tryToEmit(EnumFacing emittingSide) {
        if (getHeatContainer() == null || getHeatContainer().isEmpty()) return;

        HeatContainer adjacentHeatContainer = getAdjacentCapabilityContainer(
                TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER);

        if (adjacentHeatContainer != null) {
            int currentHeat = getHeatContainer().getValue();
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

        RenderingUtils.renderAllSidesColour(Textures.COKE_BRICKS, brickMaterial, renderState, translation, pipeline);

        Textures.FURNACE_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), isActive(),
                false);

        RenderingUtils.renderSideColour(TKCYSATextures.HEATING_PLATE, heatPlateMaterial, EnumFacing.UP, renderState,
                translation, pipeline);

        TKCYSATextures.HEATING_PLATE_FRAME.renderSided(EnumFacing.UP, renderState, translation, pipeline);
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
        return (HeatContainer) this.containerWrapper.getContainer(ContainerType.HEAT);
    }
}
