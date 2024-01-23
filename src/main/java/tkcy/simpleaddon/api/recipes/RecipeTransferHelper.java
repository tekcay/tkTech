package tkcy.simpleaddon.api.recipes;

import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.chance.output.ChancedOutputList;
import gregtech.api.recipes.chance.output.impl.ChancedFluidOutput;
import gregtech.api.recipes.chance.output.impl.ChancedItemOutput;
import gregtech.api.recipes.ingredients.GTRecipeInput;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PUBLIC)
public class RecipeTransferHelper {

    private final List<GTRecipeInput> inputs;
    private final NonNullList<ItemStack> outputs;
    private final List<GTRecipeInput> fluidInputs;
    private final List<FluidStack> fluidOutputs;
    private final int duration;
    private final int EUt;
    private final ChancedOutputList<FluidStack, ChancedFluidOutput> chancedFluidOutputs;
    private final ChancedOutputList<ItemStack, ChancedItemOutput> chancedOutputs;

    private RecipeBuilder<?> recipeBuilder;

    public static boolean isValid(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public void addRecipeToRecipeMap(RecipeMap<?> recipeMap) {
        this.recipeBuilder = recipeMap.recipeBuilder();
        if (isValid(this.inputs)) addInputs();
        if (isValid(this.outputs)) addOutputs();
        if (isValid(this.fluidInputs)) addFluidInputs();
        if (isValid(this.fluidOutputs)) addFluidOutputs();
        if (this.EUt != 0) addEUt();
        if (this.duration != 0) addDuration();

        this.recipeBuilder.buildAndRegister();

        // if (isValid(this.chancedOutputs.getChancedEntries())) addChancedOutputs();
        // if (isValid(this.chancedFluidOutputs.getChancedEntries())) addChancedFluidOutputs();
    }

    private void addInputs() {
        this.inputs.forEach(this.recipeBuilder::inputs);
    }

    private void addOutputs() {
        this.outputs.forEach(this.recipeBuilder::outputs);
    }

    private void addFluidInputs() {
        this.fluidInputs.forEach(this.recipeBuilder::fluidInputs);
    }

    private void addFluidOutputs() {
        this.fluidOutputs.forEach(this.recipeBuilder::fluidOutputs);
    }

    /*
     * private void addChancedOutputs() {
     * this.chancedOutputs.forEach(this.recipeBuilder::chancedOutputs);
     * }
     * 
     * private void addChancedFluidOutputs() {
     * recipe.getFluidOutputs().forEach(this.recipeBuilder::chancedFluidOutputs);
     * }
     * 
     */

    private void addEUt() {
        this.recipeBuilder.EUt(this.EUt);
    }

    private void addDuration() {
        this.recipeBuilder.duration(this.duration);
    }
}
