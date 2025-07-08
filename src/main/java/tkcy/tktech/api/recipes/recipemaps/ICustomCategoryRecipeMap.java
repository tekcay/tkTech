package tkcy.tktech.api.recipes.recipemaps;

import gregtech.api.recipes.RecipeMap;
import gregtech.integration.jei.recipe.RecipeMapCategory;

import tkcy.tktech.mixins.gregtech.MixinJEIModule;

/**
 * Implements a {@link RecipeMap} with this to use a custom {@link RecipeMapCategory}.
 * </br>
 * See {@link MixinJEIModule} for more details.
 */
public interface ICustomCategoryRecipeMap<T extends RecipeMap<?>> {}
