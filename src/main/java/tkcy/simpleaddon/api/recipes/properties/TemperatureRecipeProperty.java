package tkcy.simpleaddon.api.recipes.properties;

import static tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys.TEMPERATURE;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.api.utils.TKCYSALog;

public class TemperatureRecipeProperty extends RecipeProperty<Integer> implements RecipePropertyHelper<Integer> {

    @Override
    public String getKey() {
        return TEMPERATURE.name();
    }

    public static TemperatureRecipeProperty INSTANCE;

    private TemperatureRecipeProperty() {
        super(TEMPERATURE.name(), Integer.class);
    }

    public static TemperatureRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TemperatureRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        if (canDrawInfo(value)) {
            minecraft.fontRenderer.drawString(I18n.format("tkcysa.recipe." + this.getKey(), castValue(value)), x, y,
                    color);
        }
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Integer valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (!this.isValueValid().test(valueToTest)) {
            TKCYSALog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, valueToTest);
        return recipeBuilder;
    }

    @Override
    public Predicate<Integer> isValueValid() {
        return integer -> integer > getDefaultValue();
    }

    @Override
    public boolean canDrawInfo(Object value) {
        if (value instanceof Integer heat) {
            return isValueValid().test(heat);
        } else return false;
    }

    @Override
    public Integer getDefaultValue() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return getKey() + " must be 1 or more!";
    }

    @Override
    public RecipeProperty<Integer> getPropertyInstance() {
        return this;
    }
}
