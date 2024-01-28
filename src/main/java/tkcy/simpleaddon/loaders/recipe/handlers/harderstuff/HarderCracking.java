package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;

import java.util.Arrays;
import java.util.List;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockWireCoil;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.RecipeHelper;

public class HarderCracking {

    private static final List<RecipeMap<?>> chemRecipeMaps = Arrays.asList(CHEMICAL_RECIPES, LARGE_CHEMICAL_RECIPES);

    public static void init() {
        chemReactorRecipeRemoval();
        addTestRecipe();
    }

    private static void addTestRecipe() {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .EUt(20)
                .duration(20)
                .fluidInputs(HydrochloricAcid.getFluid(10))
                .fluidOutputs(Propane.getFluid(20))
                .buildAndRegister();
    }

    private static void chemReactorRecipeRemoval() {
        List<Material> oilLayersMaterials = Arrays.asList(HeavyFuel, LightFuel, Naphtha, RefineryGas);
        List<Material> hydrocarbonMaterials = Arrays.asList(Ethane, Ethylene, Propene, Propane, Butane, Butene,
                Butadiene);

        oilLayersMaterials.forEach(HarderCracking::removeLightlyAndModeraltelyCrackingRecipes);
        hydrocarbonMaterials.forEach(HarderCracking::removeModeratelyCrackingRecipes);
    }

    private static void removeLightlyAndModeraltelyCrackingRecipes(Material material) {
        for (RecipeMap<?> recipeMap : chemRecipeMaps) {
            // Lightly
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 1, Hydrogen.getFluid(1000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 1, Steam.getFluid(1000),
                    material.getFluid(1000));

            // Severely
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Hydrogen.getFluid(3000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 3, Steam.getFluid(1000),
                    material.getFluid(1000));
        }
    }

    private static void removeModeratelyCrackingRecipes(Material material) {
        for (RecipeMap<?> recipeMap : chemRecipeMaps) {

            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Hydrogen.getFluid(2000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Steam.getFluid(1000),
                    material.getFluid(1000));
        }
    }
}
