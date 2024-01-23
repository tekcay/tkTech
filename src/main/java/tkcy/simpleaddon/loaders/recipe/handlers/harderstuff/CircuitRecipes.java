package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;

public class CircuitRecipes {

    public static void init() {
        RecipeMap<?> recipeMap = RecipeMaps.CIRCUIT_ASSEMBLER_RECIPES;

        recipeMap.getRecipeList()
                .stream()
                .filter(recipe -> recipe.hasInputFluid(Materials.Tin.getFluid(72)))
                .forEach(recipeMap::removeRecipe);
    }
}
