package tkcy.tktech.api.recipes.builders;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.EnumValidationResult;

public interface IAdvancedRecipeBuilder<T extends RecipeBuilder<T>> {

    T getRecipeBuilder();

    EnumValidationResult getRecipeStatus();
}
