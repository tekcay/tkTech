package tkcy.simpleaddon.api.utils;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;

public class RecipeHelper {

    public static Recipe findRecipeWithCircuitConfig(@NotNull RecipeMap<?> recipeMap, long voltage, int circuitConfig,
                                                     @NotNull FluidStack... fluidStacks) {
        List<FluidStack> fluidStackList = Arrays.asList(fluidStacks);
        List<ItemStack> circuit = Collections.singletonList(IntCircuitIngredient.getIntegratedCircuit(circuitConfig));
        return Optional.ofNullable(recipeMap.findRecipe(voltage, circuit, fluidStackList))
                .orElseThrow(NoSuchElementException::new);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean tryToRemoveRecipeWithCircuitConfig(@NotNull RecipeMap<?> recipeMap, long voltage, int circuitConfig,
                                                             @NotNull FluidStack... fluidStacks) {
        return recipeMap.removeRecipe(findRecipeWithCircuitConfig(recipeMap, voltage, circuitConfig, fluidStacks));
    }
}
