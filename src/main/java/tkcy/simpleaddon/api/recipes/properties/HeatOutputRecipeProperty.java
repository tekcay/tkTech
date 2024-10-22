package tkcy.simpleaddon.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;
import static tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys.HEAT_OUTPUT;

public class HeatOutputRecipeProperty extends RecipeProperty<Integer> {

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
}
