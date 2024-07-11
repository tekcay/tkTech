package tkcy.simpleaddon.api.recipes.builders;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.EmptyRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.PrimitiveProperty;
import gregtech.api.util.ValidationResult;

import tkcy.simpleaddon.api.recipes.properties.HideDurationProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.modules.RecipePropertiesKey;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
@SuppressWarnings("unused")
public class ToolRecipeBuilder extends RecipeBuilder<ToolRecipeBuilder> {

    @SuppressWarnings("unused")
    public ToolRecipeBuilder() {
        recipePropertyStorage = EmptyRecipePropertyStorage.INSTANCE.copy();
    }

    @SuppressWarnings("unused")
    public ToolRecipeBuilder(Recipe recipe, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public ToolRecipeBuilder(ToolRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public ToolRecipeBuilder copy() {
        return new ToolRecipeBuilder(this);
    }

    public ToolRecipeBuilder tool(ToolsModule.GtTool gtTool) {
        ToolProperty toolProperty = ToolProperty.getInstance();
        return (ToolRecipeBuilder) toolProperty.testAndApplyPropertyValue(gtTool, this.recipeStatus, this);
    }

    public ToolRecipeBuilder toolUses(int uses) {
        ToolUsesProperty toolUsesProperty = ToolUsesProperty.getInstance();
        return (ToolRecipeBuilder) toolUsesProperty.testAndApplyPropertyValue(uses, this.recipeStatus, this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(RecipePropertiesKey.TOOL_KEY)) {
            this.tool((ToolsModule.GtTool) value);
            return true;
        }
        if (key.equals(RecipePropertiesKey.TOOL_USAGE_KEY)) {
            this.toolUses((Integer) value);
            return true;
        }
        return super.applyProperty(key, value);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        this.duration(10);
        applyProperty(PrimitiveProperty.getInstance(), true);
        applyProperty(HideDurationProperty.getInstance(), true);
        return super.build();
    }
}
