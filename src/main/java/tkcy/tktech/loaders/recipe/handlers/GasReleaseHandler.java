package tkcy.tktech.loaders.recipe.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

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
        TkTechRecipeMaps.GAS_RELEASE.recipeBuilder()
                .fluidInputs(material.getFluid(100))
                .duration(SECOND)
                .buildAndRegister();
    }
}
