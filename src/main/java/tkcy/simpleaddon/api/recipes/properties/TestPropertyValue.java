package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.EnumValidationResult;

public interface TestPropertyValue<T> {

    RecipeBuilder<?> testAndApplyPropertyValue(T valueToTest, EnumValidationResult recipeStatus,
                                               RecipeBuilder<?> recipeBuilder);

    Predicate<T> testSuppliedValue();

    T getDefaultValue();
}
