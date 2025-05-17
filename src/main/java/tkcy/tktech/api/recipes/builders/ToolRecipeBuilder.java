package tkcy.tktech.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import tkcy.tktech.api.recipes.properties.HideDurationRecipeProperty;
import tkcy.tktech.api.recipes.properties.ToolRecipeProperty;
import tkcy.tktech.api.recipes.properties.ToolUsesRecipeProperty;
import tkcy.tktech.modules.toolmodule.ToolsModule;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@WorkingTool
@NoArgsConstructor
@SuppressWarnings("unused")
public class ToolRecipeBuilder extends RecipeBuilder<ToolRecipeBuilder> {

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
        ToolRecipeProperty toolProperty = ToolRecipeProperty.getInstance();
        return (ToolRecipeBuilder) toolProperty.testAndApplyPropertyValue(gtTool, this.recipeStatus, this);
    }

    public ToolRecipeBuilder toolUses(int uses) {
        ToolUsesRecipeProperty toolUsesProperty = ToolUsesRecipeProperty.getInstance();
        return (ToolRecipeBuilder) toolUsesProperty.testAndApplyPropertyValue(uses, this.recipeStatus, this);
    }

    @Override
    public ValidationResult<Recipe> build() {
        this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        this.duration(10);
        applyProperty(PrimitiveProperty.getInstance(), true);
        applyProperty(HideDurationRecipeProperty.getInstance(), true);
        return super.build();
    }
}
