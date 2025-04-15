package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.api.utils.TKCYSALog;

public interface RecipePropertyHelper<T> {

    Predicate<T> testSuppliedValue();

    T getDefaultValue();

    String getErrorMessage();

    RecipeProperty<T> getProperty();

    default T getValueFromRecipe(Recipe recipe) {
        return recipe.getProperty(this.getProperty(), this.getDefaultValue());
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
