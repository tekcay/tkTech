package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;

import java.util.*;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.RecipeHelper;

public class Hydrogenation {

    public static void init() {
        removeOilDesulfurizationRecipes();
        removeAmmoniaRecipe();

        Map<Material, Material> sulfuricOilLayers = new HashMap<>();
        sulfuricOilLayers.put(SulfuricHeavyFuel, HeavyFuel);
        sulfuricOilLayers.put(SulfuricNaphtha, Naphtha);
        sulfuricOilLayers.put(SulfuricLightFuel, LightFuel);
        sulfuricOilLayers.put(SulfuricGas, RefineryGas);

        sulfuricOilLayers.forEach(Hydrogenation::oilDesulfurizationRecipes);
        miscHydrogenations();
    }

    private static void miscHydrogenations() {
        // N2 + 3 H2 -> NH3
        TKCYSARecipeMaps.HYDROGENATION.recipeBuilder()
                .duration(SECOND)
                .EUt(VA[HV])
                .notConsumable(OrePrefix.dust, Iron)
                .fluidInputs(Nitrogen.getFluid(50))
                .fluidInputs(Hydrogen.getFluid(150))
                .fluidOutputs(Ammonia.getFluid(50))
                .buildAndRegister();
    }

    private static void oilDesulfurizationRecipes(Material sulfuricLayer, Material desulfurized) {
        TKCYSARecipeMaps.HYDROGENATION.recipeBuilder()
                .duration(SECOND)
                .EUt(VA[MV])
                .fluidInputs(sulfuricLayer.getFluid(50))
                .fluidInputs(Hydrogen.getFluid(400))
                .fluidOutputs(desulfurized.getFluid(50))
                .fluidOutputs(HydrogenSulfide.getFluid(200))
                .buildAndRegister();
    }

    private static void removeAmmoniaRecipe() {
        RecipeMap<?> chemRecipeMap = RecipeMaps.CHEMICAL_RECIPES;
        RecipeHelper.tryToRemoveRecipeWithCircuitConfig(
                chemRecipeMap, 384, 1, Nitrogen.getFluid(1000), Hydrogen.getFluid(3000));

        chemRecipeMap = RecipeMaps.LARGE_CHEMICAL_RECIPES;
        RecipeHelper.tryToRemoveRecipeWithCircuitConfig(
                chemRecipeMap, 384, 1, Nitrogen.getFluid(1000), Hydrogen.getFluid(3000));

        RecipeHelper.tryToRemoveRecipeWithCircuitConfig(
                chemRecipeMap, VA[HV], 24, Nitrogen.getFluid(4000), Methane.getFluid(3000), Oxygen.getFluid(3000));
    }

    private static void removeOilDesulfurizationRecipes() {
        List<FluidStack> sulfuricLayers = Arrays.asList(
                SulfuricGas.getFluid(16000),
                SulfuricNaphtha.getFluid(12000),
                SulfuricLightFuel.getFluid(12000),
                SulfuricHeavyFuel.getFluid(8000));

        removeOilDesulfurizationRecipes(sulfuricLayers, RecipeMaps.LARGE_CHEMICAL_RECIPES);
        removeOilDesulfurizationRecipes(sulfuricLayers, RecipeMaps.CHEMICAL_RECIPES);
    }

    private static void removeOilDesulfurizationRecipes(List<FluidStack> fluidStackList, RecipeMap<?> recipeMap) {
        fluidStackList.stream()
                .map(fluidStack -> getRecipe(recipeMap, fluidStack))
                .forEach(recipeMap::removeRecipe);
    }

    private static Recipe getRecipe(RecipeMap<?> recipeMap, FluidStack sulfuricFluidStack) {
        return recipeMap.findRecipe(VA[LV], new ArrayList<>(),
                Arrays.asList(sulfuricFluidStack, Hydrogen.getFluid(2000)));
    }
}
