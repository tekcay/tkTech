package tkcy.simpleaddon.api.recipes.builders;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys;

@NoArgsConstructor
public class HeatOutputRecipeBuilder extends RecipeBuilder<HeatOutputRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatOutputRecipeBuilder(Recipe recipe, RecipeMap<HeatOutputRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeatOutputRecipeBuilder(HeatOutputRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public HeatOutputRecipeBuilder copy() {
        return new HeatOutputRecipeBuilder(this);
    }

    @NotNull
    public HeatOutputRecipeBuilder outputHeat(int heatInJ) {
        if (heatInJ <= 0) {
            GTLog.logger.error(RecipePropertiesKeys.HEAT_OUTPUT + " cannot be less than or equal to 0",
                    new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(HeatOutputRecipeProperty.getInstance(), heatInJ);
        return this;
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(RecipePropertiesKeys.HEAT_OUTPUT.name())) {
            this.outputHeat(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Override
    public ValidationResult<Recipe> build() {
        RecipeBuilderHelper.build(this.recipePropertyStorage, HeatOutputRecipeProperty.getInstance());
        this.EUt(1);
        applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }
}
