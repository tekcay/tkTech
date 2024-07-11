package tkcy.simpleaddon.loaders.recipe.handlers;

import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

@UtilityClass
public class GasReleaseHandler {

    public static void generateRecipes() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(Material::hasFluid)
                .filter(material -> material.getFluid().isGaseous())
                .forEach(GasReleaseHandler::generateRecipe);
    }

    private static void generateRecipe(Material material) {
        TKCYSARecipeMaps.GAS_RELEASE.recipeBuilder()
                .fluidInputs(material.getFluid(100))
                .duration(SECOND)
                .buildAndRegister();
    }
}
