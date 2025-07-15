package tkcy.tktech.mixins.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;
import gregtech.integration.jei.JustEnoughItemsModule;
import gregtech.integration.jei.recipe.RecipeMapCategory;

import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import tkcy.tktech.api.recipes.recipemaps.ICustomCategoryRecipeMap;

@Mixin(value = JustEnoughItemsModule.class, remap = false)
public class MixinJEIModule {

    /**
     * Used to prevent GT to register recipeCategories of a {@link RecipeMap} that has a custom
     * {@link RecipeMapCategory}.
     * </br>
     * </br>
     * This mixin checks if the current recipeMap implements {@link ICustomCategoryRecipeMap} while looping over all
     * registered recipeMaps in {@link JustEnoughItemsModule#registerCategories(IRecipeCategoryRegistration)}.
     */
    @Redirect(method = "registerCategories",
              at = @At(
                       value = "INVOKE",
                       target = "Lgregtech/api/recipes/ui/RecipeMapUI;isJEIVisible()Z"))
    private boolean redirectIsJEIVisible(RecipeMapUI recipeMapUI, @Coerce Object __instance) {
        RecipeMap<?> recipeMap = recipeMapUI.recipeMap();

        if (recipeMap instanceof ICustomCategoryRecipeMap) {
            return false;
        }
        return recipeMapUI.isJEIVisible();
    }
}
