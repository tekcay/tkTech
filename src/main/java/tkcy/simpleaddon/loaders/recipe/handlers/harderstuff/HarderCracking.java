package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;
import static tkcy.simpleaddon.api.utils.CollectionHelper.buildMap;
import static tkcy.simpleaddon.modules.PetroChemModule.*;

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
        chemReactorCrackingRecipesRemoval();
        addRecipes();
    }

    private static void addRecipes() {
        buildMap(desulfurizedFuels, lightlyHydroCracked).forEach(HarderCracking::addLightlyHydroCrackedRecipes);
        buildMap(desulfurizedFuels, lightlyHydroCracked).forEach(HarderCracking::addLightlyHydroCrackedRecipes);
        buildMap(desulfurizedFuels, lightlySteamCracked).forEach(HarderCracking::addLightlySteamCrackedRecipes);
        buildMap(desulfurizedFuels, severelyHydroCracked).forEach(HarderCracking::addSeverelyHydroCrackedRecipes);
        buildMap(desulfurizedFuels, severelySteamCracked).forEach(HarderCracking::addSeverelySteamCrackedRecipes);
        buildMap(hydrocarbonMaterials, hydroCrackedHydrocarbonMaterials)
                .forEach(HarderCracking::addModeratelyHydroCrackedRecipes);
        buildMap(hydrocarbonMaterials, steamCrackedHydrocarbonMaterials)
                .forEach(HarderCracking::addModeratelySteamCrackedRecipes);
    }

    private static void addLightlyHydroCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(1000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addLightlySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(2000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelyHydroCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(2000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(4000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelyHydroCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(4000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(8000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void chemReactorCrackingRecipesRemoval() {
        desulfurizedFuels.forEach(HarderCracking::removeLightlyAndModeraltelyCrackingRecipes);
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
