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
import tkcy.simpleaddon.api.recipes.properties.HeatInputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys;

@NoArgsConstructor
public class HeatInputRecipeBuilder extends RecipeBuilder<HeatInputRecipeBuilder> implements RecipeBuilderHelper {

    @SuppressWarnings("unused")
    public HeatInputRecipeBuilder(Recipe recipe, RecipeMap<HeatInputRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public HeatInputRecipeBuilder(HeatInputRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public HeatInputRecipeBuilder copy() {
        return new HeatInputRecipeBuilder(this);
    }

    @NotNull
    public HeatInputRecipeBuilder inputHeat(int heatInJ) {
        if (heatInJ <= 0) {
            GTLog.logger.error(RecipePropertiesKeys.HEAT_INPUT + " cannot be less than or equal to 0",
                    new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(HeatInputRecipeProperty.getInstance(), heatInJ);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        build(this.recipePropertyStorage);
        return super.build();
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(RecipePropertiesKeys.HEAT_INPUT.name())) {
            this.inputHeat(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }

    public int getHeat() {
        return this.recipePropertyStorage == null ? 0 :
                this.recipePropertyStorage.getRecipePropertyValue(HeatInputRecipeProperty.getInstance(), 0);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("", getHeat())
                .toString();
    }

    @Override
    public RecipeProperty<Integer> getRecipeProperty() {
        return HeatInputRecipeProperty.getInstance();
    }
}
