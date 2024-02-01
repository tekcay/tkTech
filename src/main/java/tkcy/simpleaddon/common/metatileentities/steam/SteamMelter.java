package tkcy.simpleaddon.common.metatileentities.steam;

import static gregtech.api.GTValues.L;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class SteamMelter extends SteamMetaTileEntity {

    protected FluidTank outputTank;

    public SteamMelter(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.PRIMITIVE_MELTING, Textures.COKE_OVEN_OVERLAY, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamMelter(metaTileEntityId);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, false);
    }

    @Override
    public FluidTankList createExportFluidHandler() {
        this.outputTank = new NotifiableFluidTank(L * 2, this, true);
        return new FluidTankList(false, this.outputTank);
    }

    @Override
    public void update() {
        super.update();
        if (this.outputTank.getFluid() == null) return;
        this.pushFluidsIntoNearbyHandlers(EnumFacing.DOWN);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return createUITemplate(entityPlayer)
                .slot(this.importItems, 0, 55, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.FURNACE_OVERLAY_STEAM.get(isHighPressure))
                .progressBar(workableHandler::getProgressPercent, 80, 26, 20, 16,
                        GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(isHighPressure), ProgressWidget.MoveType.HORIZONTAL,
                        workableHandler.getRecipeMap())
                .widget(new TankWidget(this.outputTank, 110, 26, 18, 18)
                        .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                        .setAlwaysShowFull(true))
                .build(getHolder(), entityPlayer);
    }

    @Override
    protected boolean isBrickedCasing() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.STEAM_BRICKED_CASING_STEEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick() {
        super.randomDisplayTick();
        if (isActive() && GTValues.RNG.nextBoolean()) {
            VanillaParticleEffects.defaultFrontEffect(this, 0.5F, EnumParticleTypes.SMOKE_NORMAL);
        }
    }
}
