package tkcy.simpleaddon.api.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys;

@NoArgsConstructor
public class HeatOutputRecipeBuilder extends RecipeBuilder<HeatOutputRecipeBuilder> implements RecipeBuilderHelper {

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

    public int getHeat() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(HeatOutputRecipeProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("", getHeat())
                .toString();
    }

    @Override
    public ValidationResult<Recipe> build() {
        build(this.recipePropertyStorage);
        return super.build();
    }

    @Override
    public RecipeProperty<Integer> getRecipeProperty() {
        return HeatOutputRecipeProperty.getInstance();
    }
}
