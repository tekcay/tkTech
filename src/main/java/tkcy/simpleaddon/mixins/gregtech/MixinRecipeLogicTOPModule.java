package tkcy.simpleaddon.mixins.gregtech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import gregtech.integration.theoneprobe.provider.RecipeLogicInfoProvider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import tkcy.simpleaddon.api.recipes.logic.IHideEnergyRecipeLogic;
import tkcy.simpleaddon.api.recipes.logic.OnBlockRecipeLogic;

@Mixin(value = RecipeLogicInfoProvider.class, remap = false)
public abstract class MixinRecipeLogicTOPModule extends CapabilityInfoProvider<AbstractRecipeLogic> {

    @Inject(method = "addProbeInfo*", at = @At(value = "HEAD"), cancellable = true)
    public void removeEuInfo(@NotNull AbstractRecipeLogic capability, @NotNull IProbeInfo probeInfo,
                             @NotNull EntityPlayer player, @NotNull TileEntity tileEntity,
                             @NotNull IProbeHitData data, CallbackInfo callbackInfo) {
        if (capability instanceof OnBlockRecipeLogic && !capability.consumesEnergy() ||
                capability instanceof IHideEnergyRecipeLogic) {
            callbackInfo.cancel();
        }
    }
}
