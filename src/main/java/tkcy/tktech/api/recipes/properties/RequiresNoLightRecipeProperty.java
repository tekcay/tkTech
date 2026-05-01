package tkcy.tktech.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.modules.RecipePropertiesKey;

public class RequiresNoLightRecipeProperty extends SimpleRecipeProperty {

    public static final String KEY = RecipePropertiesKey.NO_LIGHT;
    private static RequiresNoLightRecipeProperty INSTANCE;

    private RequiresNoLightRecipeProperty() {
        super(KEY, Boolean.class);
    }

    @NotNull
    public static RequiresNoLightRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiresNoLightRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tktech.recipe.requires_ignition"), x, y, color);
    }
}
