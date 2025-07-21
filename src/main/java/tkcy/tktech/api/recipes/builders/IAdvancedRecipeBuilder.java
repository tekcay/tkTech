package tkcy.tktech.api.recipes.builders;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.EnumValidationResult;

import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;

public interface IAdvancedRecipeBuilder<T extends RecipeBuilder<T>> {

    T getRecipeBuilder();

    EnumValidationResult getRecipeStatus();

    /**
     * Just calls {@link IRecipePropertyHelper#testAndApplyPropertyValue testAndApplyPropertyValue}
     * without the need to call {@link #getRecipeBuilder()} and {@link #getRecipeStatus()}.
     * 
     * @return the builder
     */
    default <U> T testAndApplyPropertyValue(IRecipePropertyHelper<U> recipeProperty, U value) {
        recipeProperty.testAndApplyPropertyValue(value, getRecipeStatus(), getRecipeBuilder());
        return getRecipeBuilder();
    }
}
