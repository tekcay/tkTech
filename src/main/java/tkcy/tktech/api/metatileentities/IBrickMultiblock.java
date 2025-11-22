package tkcy.tktech.api.metatileentities;

import static gregtech.api.metatileentity.multiblock.MultiblockControllerBase.abilities;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.TraceabilityPredicate;

import tkcy.tktech.api.capabilities.multiblock.TkTechMultiblockAbilities;

public interface IBrickMultiblock {

    boolean isBrick();

    default TraceabilityPredicate inputBusPredicate() {
        return abilities(isBrick() ? TkTechMultiblockAbilities.BRICK_BUS_INPUT : MultiblockAbility.IMPORT_ITEMS);
    }

    default TraceabilityPredicate outputBusPredicate() {
        return abilities(isBrick() ? TkTechMultiblockAbilities.BRICK_BUS_OUTPUT : MultiblockAbility.EXPORT_ITEMS);
    }

    default TraceabilityPredicate exportFluidPredicate() {
        return abilities(isBrick() ? TkTechMultiblockAbilities.BRICK_HATCH_OUTPUT : MultiblockAbility.EXPORT_FLUIDS);
    }

    default TraceabilityPredicate importFluidPredicate() {
        return abilities(isBrick() ? TkTechMultiblockAbilities.BRICK_HATCH_INPUT : MultiblockAbility.IMPORT_FLUIDS);
    }

    default TraceabilityPredicate mufflerPredicate() {
        return abilities(isBrick() ? TkTechMultiblockAbilities.BRICK_MUFFLER : MultiblockAbility.MUFFLER_HATCH);
    }
}
