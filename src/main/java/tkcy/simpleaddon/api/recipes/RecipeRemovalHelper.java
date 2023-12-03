package tkcy.simpleaddon.api.recipes;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

public class RecipeRemovalHelper {

    public static void removeRecipeByInput(RecipeMap<?> recipeMap, OrePrefix orePrefix, Material material) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (GTRecipeInput gtRecipeInput : recipe.getInputs()) {
                for (ItemStack stack : gtRecipeInput.getInputStacks()) {
                    int count = stack.getCount();
                    if (stack.isItemEqual(OreDictUnifier.get(orePrefix, material, count))) {
                        recipeMap.removeRecipe(recipe);
                    }
                }
            }
        }
    }
}
