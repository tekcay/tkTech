package tkcy.tktech.api.recipes.builders;

import gregtech.api.recipes.RecipeBuilder;

public interface IAdvancedRecipeBuilder<T extends RecipeBuilder<T>> {

    T getRecipeBuilder();
}
