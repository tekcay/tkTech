package tkcy.tktech.api.capabilities.multiblock;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.TraceabilityPredicate;

import tkcy.tktech.mixins.gregtech.MixinMultiblockWithDisplayBase;

/**
 * Implemented by {@link MultiblockWithDisplayBase} via mixins.
 * </br>
 * See {@link MixinMultiblockWithDisplayBase#addProxyHatchToAutoAbilities(boolean, boolean, CallbackInfoReturnable)}
 */
public interface IProxyAbleController {

    default int maxProxyHatches() {
        return 1;
    }

    default TraceabilityPredicate getProxyControllerPredicate() {
        return abilities(TkTechMultiblockAbilities.CONTROLLER_PROXY).setMaxGlobalLimited(maxProxyHatches(), 1);
    }

    default boolean canBeProxied() {
        return true;
    }
}
