package tkcy.tktech.api.recipes.logic;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.ingredients.GTRecipeFluidInput;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.recipes.logic.IParallelableRecipeLogic;

/**
 * Recipe Logic for a Multiblock that does not require power.
 */
public class NoEnergyParallelLogic extends NoEnergyLogic implements IParallelableRecipeLogic {

    protected int maxVoltageTier;

    public NoEnergyParallelLogic(RecipeMapMultiblockController tileEntity) {
        super(tileEntity);
    }

    public static void setMaxVoltageTier(MultiblockRecipeLogic logic, int maxVoltageTier) {
        ((NoEnergyParallelLogic) logic).setMaxVoltageTier(maxVoltageTier);
    }

    public void setMaxVoltageTier(int maxVoltageTier) {
        this.maxVoltageTier = maxVoltageTier;
    }

    public int getMaxVoltageTier() {
        return maxVoltageTier;
    }

    @Override
    public long getMaxVoltage() {
        return Math.min(GTValues.V[getMaxVoltageTier()], super.getMaxVoltage());
    }

    public List<GTRecipeInput> getParallelFluid(List<GTRecipeInput> fluidInputs, int parallelValue) {
        return fluidInputs.stream()
                .map(GTRecipeInput::getInputFluidStack)
                .map(fluidStack -> new FluidStack(fluidStack, fluidStack.amount * parallelValue))
                .map(GTRecipeFluidInput::new)
                .collect(Collectors.toList());
    }

    @Override
    public void applyParallelBonus(@NotNull RecipeBuilder<?> builder) {
        List<GTRecipeInput> fluids = getParallelFluid(builder.getFluidInputs(), builder.getParallel());
        builder.fluidInputs(fluids);
    }
}
