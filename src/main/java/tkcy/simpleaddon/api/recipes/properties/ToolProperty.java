package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.RecipePropertiesKey;
import tkcy.simpleaddon.modules.ToolsModule;

public class ToolProperty extends RecipeProperty<ToolsModule.GtTool> implements TestPropertyValue<ToolsModule.GtTool> {

    public static final String KEY = RecipePropertiesKey.TOOL_KEY;

    private static ToolProperty INSTANCE;

    private ToolProperty() {
        super(KEY, ToolsModule.GtTool.class);
    }

    public static ToolProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolProperty();
        }
        return INSTANCE;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        ;
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(ToolsModule.GtTool valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (this.testSuppliedValue().test(valueToTest)) {
            TKCYSALog.logger.error("Tool type must be declared in the ToolModule.GtTool enum!",
                    new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, getDefaultValue());
        return recipeBuilder;
    }

    @Override
    public Predicate<ToolsModule.GtTool> testSuppliedValue() {
        return ToolsModule.GT_TOOLS::contains;
    }

    @Override
    public ToolsModule.GtTool getDefaultValue() {
        return ToolsModule.GtTool.AXE;
    }
}
