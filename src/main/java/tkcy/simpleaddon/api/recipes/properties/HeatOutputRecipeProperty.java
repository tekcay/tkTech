package tkcy.simpleaddon.api.recipes.properties;

import static tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys.HEAT_OUTPUT;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.api.utils.TKCYSALog;

public class HeatOutputRecipeProperty extends RecipeProperty<Integer> implements RecipePropertyHelper<Integer> {

    @Override
    public String getKey() {
        return HEAT_OUTPUT.name();
    }

    public static HeatOutputRecipeProperty INSTANCE;

    private HeatOutputRecipeProperty() {
        super(HEAT_OUTPUT.name(), Integer.class);
    }

    public static HeatOutputRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatOutputRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcysa.recipe." + this.getKey(), castValue(value)), x, y, color);
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Integer valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (!this.testSuppliedValue().test(valueToTest)) {
            TKCYSALog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, valueToTest);
        return recipeBuilder;
    }

    @Override
    public Predicate<Integer> testSuppliedValue() {
        return integer -> integer < 0;
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
