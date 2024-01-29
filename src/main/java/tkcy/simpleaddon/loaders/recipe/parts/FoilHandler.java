package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.loaders.recipe.CraftingComponent.*;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

public class FoilHandler {

    public static void init() {
        removeAndAddFoilRecipes();
        addClusterMillRecipes();
    }

    private static void addClusterMillRecipes() {
        MetaTileEntityLoader.registerMachineRecipe(TKCYSAMetaTileEntities.CLUSTER_MILLS,
                "MMM", "CHC", "MMM",
                'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
    }

    private static void removeAndAddFoilRecipes() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(OrePrefix.foil::doGenerateItem)
                .peek(FoilHandler::removeFoilRecipes)
                .forEach(FoilHandler::foils);
    }

    private static void removeFoilRecipes(Material material) {
        method(material, OrePrefix.ingot, 10);
        method(material, OrePrefix.plate, 1);
    }

    private static void foils(Material material) {
        TKCYSARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material)
                .output(OrePrefix.foil, material, 4)
                .EUt(GTValues.VA[GTValues.LV])
                .duration((int) (material.getMass()))
                .buildAndRegister();
    }

    private static void method(Material material, OrePrefix orePrefix, int circuit) {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BENDER_RECIPES, OreDictUnifier.get(orePrefix, material),
                IntCircuitIngredient.getIntegratedCircuit(circuit));
    }
}
