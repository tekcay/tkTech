package tkcy.simpleaddon.loaders.recipe.parts;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class FoilHandler {

    public static void init() {
        remove();
        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, FoilHandler::foils);

    }

    private static void foils(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        TKCYSARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material)
                .output(OrePrefix.foil, material, 4)
                .EUt(GTValues.VA[GTValues.LV])
                .duration((int) (material.getMass()))
                .buildAndRegister();
    }

    private static void remove() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(OrePrefix.foil::doGenerateItem)
                .forEach(FoilHandler::remove);
    }

    private static void remove(Material material) {
        method(material, OrePrefix.ingot, 10);
        method(material, OrePrefix.plate, 1);
    }

    private static void method(Material material, OrePrefix orePrefix, int circuit) {
        GTRecipeHandler.removeRecipesByInputs(RecipeMaps.BENDER_RECIPES, OreDictUnifier.get(orePrefix, material),
                IntCircuitIngredient.getIntegratedCircuit(circuit));
    }
}
