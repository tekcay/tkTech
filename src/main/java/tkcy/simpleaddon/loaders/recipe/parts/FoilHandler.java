package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.loaders.recipe.CraftingComponent.*;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

public class FoilHandler {

    public static void init() {
        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, FoilHandler::processFoils);
        addClusterMillRecipes();
    }

    private static void addClusterMillRecipes() {
        MetaTileEntityLoader.registerMachineRecipe(TKCYSAMetaTileEntities.CLUSTER_MILLS,
                "MMM", "CHC", "MMM",
                'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
    }

    public static void processFoils(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        TKCYSARecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material)
                .output(OrePrefix.foil, material, 4)
                .EUt(GTValues.VA[GTValues.LV])
                .duration((int) (material.getMass()))
                .buildAndRegister();
    }
}
