package tkcy.simpleaddon.api.utils;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;

public class RecipeHelper {

    public static final List<FluidStack> emptyFluidStack = new ArrayList<>();

    public static ItemStack getCircuitStack(int circuitConfig) {
        return IntCircuitIngredient.getIntegratedCircuit(circuitConfig);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <
            T extends RecipeBuilder<T>> boolean tryToRemoveRecipeWithCircuitConfig(@NotNull RecipeMap<T> recipeMap,
                                                                                   long voltage,
                                                                                   int circuitConfig,
                                                                                   @NotNull FluidStack... fluidStacks) {
        return recipeMap.removeRecipe(findRecipeWithCircuitConfig(recipeMap, voltage, circuitConfig, fluidStacks));
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <
            T extends RecipeBuilder<T>> boolean tryToRemoveRecipeWithCircuitConfig(@NotNull RecipeMap<T> recipeMap,
                                                                                   long voltage,
                                                                                   int circuitConfig,
                                                                                   @NotNull ItemStack... itemStacks) {
        return recipeMap.removeRecipe(findRecipeWithCircuitConfig(recipeMap, voltage, circuitConfig, itemStacks));
    }

    public static Recipe findRecipeWithCircuitConfig(@NotNull RecipeMap<?> recipeMap, long voltage, int circuitConfig,
                                                     @NotNull FluidStack... fluidStacks) {
        List<FluidStack> fluidStackList = Arrays.asList(fluidStacks);
        List<ItemStack> circuit = Collections.singletonList(getCircuitStack(circuitConfig));
        return Optional.ofNullable(recipeMap.findRecipe(voltage, circuit, fluidStackList))
                .orElseThrow(NoSuchElementException::new);
    }

    public static Recipe findRecipeWithCircuitConfig(@NotNull RecipeMap<?> recipeMap, long voltage, int circuitConfig,
                                                     @NotNull ItemStack... itemStacks) {
        List<ItemStack> itemStackList = new ArrayList<>();
        itemStackList.add(getCircuitStack(circuitConfig));
        itemStackList.addAll(Arrays.asList(itemStacks));

        return Optional.ofNullable(recipeMap.findRecipe(voltage, itemStackList, emptyFluidStack))
                .orElseThrow(NoSuchElementException::new);
    }
}
