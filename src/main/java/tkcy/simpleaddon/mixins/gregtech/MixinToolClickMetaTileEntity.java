package tkcy.simpleaddon.mixins.gregtech;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.cover.Cover;
import gregtech.api.cover.CoverRayTracer;
import gregtech.api.metatileentity.MetaTileEntity;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolClasses;
import tkcy.simpleaddon.api.machines.IOnSolderingIronClick;

@Mixin(value = MetaTileEntity.class, remap = false)
public abstract class MixinToolClickMetaTileEntity implements IOnSolderingIronClick {

    @Shadow
    public abstract boolean onToolClick(EntityPlayer playerIn, @NotNull Set<String> toolClasses, EnumHand hand,
                                        CuboidRayTraceResult hitResult);

    @Shadow
    public abstract Cover getCoverAtSide(EnumFacing gridSideHit);

    @Inject(method = "onToolClick", at = @At(value = "TAIL", ordinal = 0), cancellable = true)
    public void onToolClick(EntityPlayer playerIn, @NotNull Set<String> toolClasses, EnumHand hand,
                            CuboidRayTraceResult hitResult, CallbackInfoReturnable<Boolean> callback) {
        // the side hit from the machine grid

        EnumFacing gridSideHit = CoverRayTracer.determineGridSideHit(hitResult);
        Cover cover = gridSideHit == null ? null : getCoverAtSide(gridSideHit);
        if (toolClasses.contains(TKCYSAToolClasses.SOLDERING_IRON)) {
            callback.setReturnValue(
                    onSolderingIronClick(playerIn, hand, gridSideHit, hitResult));
        }
        callback.setReturnValue(false);
    }
}
