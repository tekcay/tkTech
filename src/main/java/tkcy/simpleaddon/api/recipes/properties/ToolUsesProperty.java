package tkcy.simpleaddon.api.recipes.properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import gregtech.api.recipes.recipeproperties.RecipeProperty;

import tkcy.simpleaddon.modules.RecipePropertiesKey;

public class ToolUsesProperty extends RecipeProperty<Integer> {

    public static final String KEY = RecipePropertiesKey.TOOL_USAGE_KEY;

    private static ToolUsesProperty INSTANCE;

    private ToolUsesProperty() {
        super(KEY, Integer.class);
    }

    public static ToolUsesProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolUsesProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        Integer uses = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format("tkcya.recipe.tool.uses", uses), x, y, color);
    }
}
