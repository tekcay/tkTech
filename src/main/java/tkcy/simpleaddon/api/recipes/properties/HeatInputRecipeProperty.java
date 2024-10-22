package tkcy.simpleaddon.api.recipes.properties;

import gregtech.api.recipes.recipeproperties.RecipeProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import static tkcy.simpleaddon.api.recipes.properties.RecipePropertiesKeys.HEAT_INPUT;

public class HeatInputRecipeProperty extends RecipeProperty<Integer> {

    @Override
    public String getKey() {
        return HEAT_INPUT.name();
    }

    public static HeatInputRecipeProperty INSTANCE;

    private HeatInputRecipeProperty() {
        super(HEAT_INPUT.name(), Integer.class);
    }

    public static HeatInputRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeatInputRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tkcysa.recipe." + this.getKey(), castValue(value)), x, y, color);
    }
}
