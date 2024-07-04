package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

public interface RecipePropertyHelper<T> {

    RecipeBuilder<?> testAndApplyPropertyValue(T valueToTest, EnumValidationResult recipeStatus,
                                               RecipeBuilder<?> recipeBuilder);

    Predicate<T> testSuppliedValue();

    T getDefaultValue();

    String getErrorMessage();

    RecipeProperty<T> getPropertyInstance();

    default T getValueFromRecipe(Recipe recipe) {
        return recipe.getProperty(this.getPropertyInstance(), this.getDefaultValue());
    }
}
