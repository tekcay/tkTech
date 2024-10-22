package tkcy.simpleaddon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.ValidationResult;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import tkcy.simpleaddon.api.recipes.properties.HeatInputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys;

public class HeatInputRecipeBuilder extends RecipeBuilder<HeatInputRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatInputRecipeBuilder() {}

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
        if (this.recipePropertyStorage == null) this.recipePropertyStorage = new RecipePropertyStorage();
        if (this.recipePropertyStorage.hasRecipeProperty(HeatInputRecipeProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(HeatInputRecipeProperty.getInstance(), 0) <= 0) {
                this.recipePropertyStorage.store(HeatInputRecipeProperty.getInstance(), 0);
            }
        } else {
            this.recipePropertyStorage.store(HeatInputRecipeProperty.getInstance(), 0);
        }
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
}
