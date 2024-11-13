package tkcy.simpleaddon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoEnergyRecipeBuilder extends RecipeBuilder<NoEnergyRecipeBuilder> {

    @SuppressWarnings("unused")
    public NoEnergyRecipeBuilder(Recipe recipe, RecipeMap<NoEnergyRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public NoEnergyRecipeBuilder(RecipeBuilder<NoEnergyRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public NoEnergyRecipeBuilder copy() {
        return new NoEnergyRecipeBuilder(this);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1);
        this.applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }
}
