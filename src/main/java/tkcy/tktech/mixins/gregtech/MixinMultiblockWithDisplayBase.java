package tkcy.tktech.mixins.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.TraceabilityPredicate;

import tkcy.tktech.api.capabilities.multiblock.IControllerProxy;
import tkcy.tktech.api.capabilities.multiblock.IProxyAbleController;
import tkcy.tktech.common.metatileentities.multiblockpart.MTeControllerProxyHatch;

@Mixin(value = MultiblockWithDisplayBase.class, remap = false)
public abstract class MixinMultiblockWithDisplayBase implements IProxyAbleController {

    /**
     * Adds the {@link IControllerProxy} ability to all {@link MultiblockWithDisplayBase} instances so
     * Controller Proxy Hatches ({@link MTeControllerProxyHatch}) can be added to the structure.
     */
    @Inject(method = "autoAbilities(ZZ)Lgregtech/api/pattern/TraceabilityPredicate;",
            at = @At(value = "RETURN", ordinal = 0),
            cancellable = true)
    public void addProxyHatchToAutoAbilities(boolean checkMaintenance, boolean checkMuffler,
                                             CallbackInfoReturnable<TraceabilityPredicate> callbackInfoReturnable) {
        if (canBeProxied()) {
            TraceabilityPredicate predicate = callbackInfoReturnable.getReturnValue()
                    .or(getProxyControllerPredicate());
            callbackInfoReturnable.setReturnValue(predicate);
        }
    }
}
