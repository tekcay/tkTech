package tkcy.tktech.api.recipes.logic;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.properties.RecipePropertyStorage;

/**
 * Recipe Logic for a Multiblock that does not require power.
 */
public class NoEnergyLogic extends MultiblockRecipeLogic {

    public NoEnergyLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    @Override
    protected long getEnergyInputPerSecond() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(long recipeEUt, boolean simulate) {
        return true;
    }

    @Override
    public long getMaxVoltage() {
        return GTValues.LV;
    }

    @Override
    protected boolean hasEnoughPower(long eut, int duration) {
        return true;
    }

    @Override
    protected void runOverclockingLogic(@NotNull OCParams ocParams, @NotNull OCResult ocResult,
                                        @NotNull RecipePropertyStorage propertyStorage, long maxVoltage) {
        ocParams.setEut(1L);
        super.runOverclockingLogic(ocParams, ocResult, propertyStorage, maxVoltage);
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return GTValues.V[GTValues.LV];
    }

    // /**
    // * Used to reset cached values in the Recipe Logic on structure deform
    // */
    // @Override
    // public void invalidate() {
    // previousRecipe = null;
    // progressTime = 0;
    // maxProgressTime = 0;
    // recipeEUt = 0;
    // fluidOutputs = null;
    // itemOutputs = null;
    // setActive(false); // this marks dirty for us
    // }
}
