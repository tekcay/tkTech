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
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys;

public class HeatOutputRecipeBuilder extends RecipeBuilder<HeatOutputRecipeBuilder> {

    @SuppressWarnings("unused")
    public HeatOutputRecipeBuilder() {}

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
    public HeatOutputRecipeBuilder outputHeat(int kev) {
        if (kev <= 0) {
            GTLog.logger.error(RecipePropertiesKeys.HEAT_OUTPUT + " cannot be less than or equal to 0",
                    new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(HeatOutputRecipeProperty.getInstance(), kev);
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.recipePropertyStorage == null) this.recipePropertyStorage = new RecipePropertyStorage();
        if (this.recipePropertyStorage.hasRecipeProperty(HeatOutputRecipeProperty.getInstance())) {
            if (this.recipePropertyStorage.getRecipePropertyValue(HeatOutputRecipeProperty.getInstance(), 0) <= 0) {
                this.recipePropertyStorage.store(HeatOutputRecipeProperty.getInstance(), 0);
            }
        } else {
            this.recipePropertyStorage.store(HeatOutputRecipeProperty.getInstance(), 0);
        }
        return super.build();
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
}
