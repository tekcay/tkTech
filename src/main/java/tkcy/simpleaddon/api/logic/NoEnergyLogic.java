package tkcy.simpleaddon.api.logic;

import static gregtech.api.recipes.logic.OverclockingLogic.standardOverclockingLogic;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;

/**
 * Recipe Logic for a Multiblock that does not require power.
 */
public class NoEnergyLogic extends MultiblockRecipeLogic {

    public NoEnergyLogic(RecipeMapMultiblockController tileEntity, RecipeMap<?> recipeMap) {
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
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        return true; // spoof energy being drawn
    }

    @Override
    public long getMaxVoltage() {
        return GTValues.LV;
    }

    @NotNull
    @Override
    protected int[] runOverclockingLogic(@NotNull IRecipePropertyStorage propertyStorage, int recipeEUt,
                                         long maxVoltage, int recipeDuration, int amountOC) {
        return standardOverclockingLogic(
                1,
                getMaxVoltage(),
                recipeDuration,
                amountOC,
                getOverclockingDurationDivisor(),
                getOverclockingVoltageMultiplier()

        );
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return GTValues.V[GTValues.LV];
    }

    /**
     * Used to reset cached values in the Recipe Logic on structure deform
     */
    public void invalidate() {
        previousRecipe = null;
        progressTime = 0;
        maxProgressTime = 0;
        recipeEUt = 0;
        fluidOutputs = null;
        itemOutputs = null;
        setActive(false); // this marks dirty for us
    }
}
