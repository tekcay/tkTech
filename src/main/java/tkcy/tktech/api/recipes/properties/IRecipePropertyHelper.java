package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.tktech.api.utils.TKCYSALog;

public interface IRecipePropertyHelper<T> {

    Predicate<T> testSuppliedValue();

    T getDefaultValue();

    String getErrorMessage();

    RecipeProperty<T> getProperty();

    default T getValueFromRecipe(@NotNull Recipe recipe) {
        return recipe.getProperty(this.getProperty(), this.getDefaultValue());
    }

    default boolean areValueEquals(T recipeValue, Object valueToTest) {
        return recipeValue.equals(valueToTest);
    }

    /**
     * Tests if the provided {@code recipe} has this {@link RecipeProperty}, then does
     * {@link #getValueFromRecipe(Recipe)}.
     * 
     * @param recipe
     * @param toTest whether test {@link Recipe#hasProperty(RecipeProperty)} before getting the value.
     * @return {@code null} if the {@code recipe} does not have this {@link RecipeProperty}.
     */
    @Nullable
    default T getValueFromRecipe(Recipe recipe, boolean toTest) {
        if (!toTest) return getValueFromRecipe(recipe);
        if (recipe.hasProperty(this.getProperty())) {
            return getValueFromRecipe(recipe);
        } else return null;
    }

    default boolean hasRecipePropertyValue(Recipe recipe, T value) {
        if (value == null) return false;
        T recipeValue = this.getValueFromRecipe(recipe, true);
        return recipeValue != null && areValueEquals(recipeValue, value);
    }

    default boolean hasRecipePropertyUncastedValue(Recipe recipe, Object value) {
        if (value == null) return false;
        T recipeValue = this.getValueFromRecipe(recipe, true);
        return recipeValue != null && areValueEquals(recipeValue, value);
    }

    default RecipeBuilder<?> testAndApplyPropertyValue(T valueToTest, EnumValidationResult recipeStatus,
                                                       RecipeBuilder<?> recipeBuilder) {
        if (!this.testSuppliedValue().test(valueToTest)) {
            TKCYSALog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(getProperty(), valueToTest);
        return recipeBuilder;
    }
}
