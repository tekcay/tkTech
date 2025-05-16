package tkcy.tktech.mixins.gregtech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.capability.IWorkable;
import gregtech.integration.theoneprobe.provider.CapabilityInfoProvider;
import gregtech.integration.theoneprobe.provider.WorkableInfoProvider;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import tkcy.tktech.api.recipes.logic.IHideRecipeProgress;

@Mixin(value = WorkableInfoProvider.class, remap = false)
public abstract class MixinWorkableInfoTOPModule extends CapabilityInfoProvider<IWorkable> {

    @Inject(method = "addProbeInfo*", at = @At(value = "HEAD"), cancellable = true)
    public void removeProgressInfo(@NotNull IWorkable capability, @NotNull IProbeInfo probeInfo,
                                   @NotNull EntityPlayer player, @NotNull TileEntity tileEntity,
                                   @NotNull IProbeHitData data, CallbackInfo callbackInfo) {
        if (!capability.isActive() || capability instanceof IHideRecipeProgress) callbackInfo.cancel();
    }
}
