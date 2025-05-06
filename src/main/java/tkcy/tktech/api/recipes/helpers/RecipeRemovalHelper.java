package tkcy.tktech.api.recipes.helpers;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
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

    public static void removeRecipeByOutput(RecipeMap<?> recipeMap, OrePrefix orePrefix, Material material) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (ItemStack stack : recipe.getOutputs()) {
                int count = stack.getCount();
                if (stack.isItemEqual(OreDictUnifier.get(orePrefix, material, count))) {
                    recipeMap.removeRecipe(recipe);
                }
            }
        }
    }

    public static void removeRecipeByOutput(RecipeMap<?> recipeMap, @Nonnull ItemStack itemStack) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            if (recipe.getOutputs().contains(itemStack)) {
                recipeMap.removeRecipe(recipe);
            }
        }
    }

    public static void removeRecipeByOutputs(RecipeMap<?> recipeMap, @Nonnull ItemStack itemStack) {
        for (Recipe recipe : recipeMap.getRecipeList()) {
            for (ItemStack stack : recipe.getOutputs()) {
                if (stack.isItemEqual(itemStack)) {
                    recipeMap.removeRecipe(recipe);
                }
            }
        }
    }
}
