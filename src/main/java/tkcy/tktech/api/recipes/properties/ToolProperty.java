package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.tktech.api.utils.TKCYSALog;
import tkcy.tktech.modules.RecipePropertiesKey;
import tkcy.tktech.modules.toolmodule.ToolsModule;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@WorkingTool
public class ToolProperty extends RecipeProperty<ToolsModule.GtTool>
                          implements IRecipePropertyHelper<ToolsModule.GtTool> {

    public static final String KEY = RecipePropertiesKey.TOOL_KEY;
    private static ToolProperty INSTANCE;

    private ToolProperty() {
        super(KEY, ToolsModule.GtTool.class);
    }

    @NotNull
    public static ToolProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagString(castValue(value).getToolClassName());
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        ToolsModule.GtTool tool = ToolsModule.getGtTool(((NBTTagString) nbt).getString());
        return tool == null ? getDefaultValue() : tool;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(ToolsModule.GtTool valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (!this.testSuppliedValue().test(valueToTest)) {
            TKCYSALog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, valueToTest);
        recipeBuilder.inputs(valueToTest.getToolStack());
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

    @Override
    public String getErrorMessage() {
        return "Tool type must be declared in the ToolModule.GtTool enum!";
    }

    @Override
    public RecipeProperty<ToolsModule.GtTool> getProperty() {
        return this;
    }
}
