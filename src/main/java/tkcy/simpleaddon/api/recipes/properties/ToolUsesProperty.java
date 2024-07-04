package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.RecipePropertiesKey;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
public class ToolUsesProperty extends RecipeProperty<Integer> implements RecipePropertyHelper<Integer> {

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
        return value -> value >= this.getDefaultValue();
    }

    @Override
    public Integer getDefaultValue() {
        return 1;
    }

    @Override
    public String getErrorMessage() {
        return "Uses must be 1 or more!";
    }

    @Override
    public RecipeProperty<Integer> getPropertyInstance() {
        return this;
    }
}
