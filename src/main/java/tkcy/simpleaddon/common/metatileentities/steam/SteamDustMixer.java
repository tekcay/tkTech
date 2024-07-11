package tkcy.simpleaddon.common.metatileentities.steam;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SteamMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.particle.VanillaParticleEffects;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleSidedCubeRenderer;

import tkcy.simpleaddon.api.recipes.CastingInfo;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

public class SteamDustMixer extends SteamMetaTileEntity {

    public SteamDustMixer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING, Textures.ALLOY_SMELTER_OVERLAY, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new SteamDustMixer(metaTileEntityId);
    }

    @Override
    protected boolean isBrickedCasing() {
        return true;
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new NotifiableItemStackHandler(this, 3, this, false);
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new NotifiableItemStackHandler(this, 1, this, true);
    }

    @SideOnly(Side.CLIENT)
    protected SimpleSidedCubeRenderer getBaseRenderer() {
        return Textures.STEAM_BRICKED_CASING_STEEL;
    }

    @Override
    public ModularUI createUI(EntityPlayer player) {
        return createUITemplate(player)
                .slot(this.importItems, 0, 75, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.FURNACE_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 1, 55, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.FURNACE_OVERLAY_STEAM.get(isHighPressure))
                .slot(this.importItems, 2, 35, 25, GuiTextures.SLOT_STEAM.get(isHighPressure),
                        GuiTextures.FURNACE_OVERLAY_STEAM.get(isHighPressure))
                .progressBar(workableHandler::getProgressPercent, 100, 26, 20, 16,
                        GuiTextures.PROGRESS_BAR_ARROW_STEAM.get(isHighPressure), ProgressWidget.MoveType.HORIZONTAL,
                        workableHandler.getRecipeMap())
                .slot(this.exportItems, 0, 130, 25, true, false, GuiTextures.SLOT_STEAM.get(isHighPressure))
                .build(getHolder(), player);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick() {
        super.randomDisplayTick();
        if (isActive() && GTValues.RNG.nextBoolean()) {
            VanillaParticleEffects.defaultFrontEffect(this, 0.5F, EnumParticleTypes.SMOKE_NORMAL);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tkcysa.steam_melter.tooltip.1"));
        tooltip.add(I18n.format("tkcysa.steam_melter.tooltip.2"));
        CastingInfo.CASTING_INFOS.forEach(castingInfo -> CastingInfo.addToTooltip.accept(castingInfo, tooltip));
    }
}
