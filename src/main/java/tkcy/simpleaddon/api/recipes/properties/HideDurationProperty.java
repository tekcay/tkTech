package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.modules.RecipePropertiesKey;

public class HideDurationProperty extends RecipeProperty<Boolean> implements RecipePropertyHelper<Boolean> {

    public static final String KEY = RecipePropertiesKey.HIDE_DURATION_KEY;

    private static HideDurationProperty INSTANCE;

    private HideDurationProperty() {
        super(KEY, Boolean.class);
    }

    public static HideDurationProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HideDurationProperty();
        }
        return INSTANCE;
    }

    @Override
    public int getInfoHeight(Object value) {
        return 0;
    }

    @Override
    public boolean hideDuration() {
        return true;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Boolean valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        return recipeBuilder;
    }

    @Override
    public Predicate<Boolean> isValueValid() {
        return value -> value == getDefaultValue();
    }

    @Override
    public Boolean getDefaultValue() {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "must be true";
    }

    @Override
    public RecipeProperty<Boolean> getPropertyInstance() {
        return this;
    }

    @Override
    public boolean canDrawInfo(Object value) {
        return true;
    }
}
