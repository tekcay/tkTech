package tkcy.tktech.loaders.recipe.parts;

import static gregtech.loaders.recipe.CraftingComponent.*;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

@UtilityClass
public class FoilHandler {

    public static void init() {
        OrePrefix.foil.addProcessingHandler(PropertyKey.INGOT, FoilHandler::processFoils);
        addClusterMillRecipes();
    }

    private static void addClusterMillRecipes() {
        MetaTileEntityLoader.registerMachineRecipe(TkTechMetaTileEntities.CLUSTER_MILLS,
                "MMM", "CHC", "MMM",
                'M', MOTOR, 'C', CIRCUIT, 'H', HULL);
    }

    public static void processFoils(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        TkTechRecipeMaps.CLUSTER_MILL_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, material)
                .output(OrePrefix.foil, material, 4)
                .EUt(GTValues.VA[GTValues.LV])
                .duration((int) (material.getMass()))
                .buildAndRegister();
    }
}
