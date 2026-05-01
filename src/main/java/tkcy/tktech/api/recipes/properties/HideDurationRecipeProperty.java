package tkcy.tktech.api.recipes.properties;

import net.minecraft.client.Minecraft;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;

import tkcy.tktech.modules.RecipePropertiesKey;

public class HideDurationRecipeProperty extends SimpleRecipeProperty {

    public static final String KEY = RecipePropertiesKey.HIDE_DURATION_KEY;
    private static HideDurationRecipeProperty INSTANCE;

    private HideDurationRecipeProperty() {
        super(KEY, Boolean.class);
    }

    @NotNull
    public static HideDurationRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HideDurationRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public int getInfoHeight(@NotNull Object value) {
        return 0;
    }

    @Override
    public boolean hideDuration() {
        return true;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Boolean valueToTest,
                                                      RecipeBuilder<?> recipeBuilder, Runnable recipeInvalidator) {
        return recipeBuilder;
    }
}
