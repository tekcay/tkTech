package tkcy.simpleaddon.api.recipes.builders;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import tkcy.simpleaddon.api.recipes.properties.*;

@NoArgsConstructor
public class HeatInputRecipeBuilder extends RecipeBuilder<HeatInputRecipeBuilder> {

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
        RecipePropertyHelper<Integer> property = HeatInputRecipeProperty.getInstance();
        return (HeatInputRecipeBuilder) property.testAndApplyPropertyValue(heatInJ, this.recipeStatus, this);
    }

    @NotNull
    public HeatInputRecipeBuilder temperature(int temperatureInK) {
        RecipePropertyHelper<Integer> property = TemperatureRecipeProperty.getInstance();
        return (HeatInputRecipeBuilder) property.testAndApplyPropertyValue(temperatureInK, this.recipeStatus, this);
    }

    @Override
    public ValidationResult<Recipe> build() {
        RecipeBuilderHelper.build(this.recipePropertyStorage, HeatInputRecipeProperty.getInstance(),
                TemperatureRecipeProperty.getInstance());
        this.EUt(1);
        applyProperty(PrimitiveProperty.getInstance(), true);
        return super.build();
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(RecipePropertiesKeys.HEAT_INPUT.name())) {
            this.inputHeat(((Number) value).intValue());
            return true;
        }
        if (key.equals(RecipePropertiesKeys.TEMPERATURE.name())) {
            this.temperature(((Number) value).intValue());
            return true;
        }
        return super.applyProperty(key, value);
    }
}
