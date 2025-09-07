package tkcy.tktech.api.recipes.builders;

import gregtech.api.recipes.RecipeBuilder;

import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;

public interface IAdvancedRecipeBuilder<T extends RecipeBuilder<T>> {

    T getRecipeBuilder();

    void invalidateRecipe();

    /**
     * Just calls {@link IRecipePropertyHelper#testAndApplyPropertyValue testAndApplyPropertyValue}
     * without the need to call {@link #getRecipeBuilder()} and {@link #invalidateRecipe()}.
     * 
     * @return the builder
     */
    default <U> T testAndApplyPropertyValue(IRecipePropertyHelper<U> recipeProperty, U value) {
        recipeProperty.testAndApplyPropertyValue(value, getRecipeBuilder(), this::invalidateRecipe);
        return getRecipeBuilder();
    }
}
