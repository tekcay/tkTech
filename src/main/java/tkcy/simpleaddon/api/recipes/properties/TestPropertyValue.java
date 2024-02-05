package tkcy.simpleaddon.api.recipes.properties;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.EnumValidationResult;

import java.util.function.Predicate;

public interface TestPropertyValue<T> {

    RecipeBuilder<?> testAndApplyPropertyValue(T valueToTest, EnumValidationResult recipeStatus, RecipeBuilder<?> recipeBuilder);
    Predicate<T> testSuppliedValue();
    T getDefaultValue();

}
