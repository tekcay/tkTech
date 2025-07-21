package tkcy.tktech.api.recipes.builders;

import net.minecraft.util.EnumFacing;

import gregtech.api.recipes.RecipeBuilder;

import tkcy.tktech.api.recipes.properties.ToolFacingRecipeProperty;
import tkcy.tktech.api.recipes.properties.ToolRecipeProperty;
import tkcy.tktech.api.recipes.properties.ToolUsesRecipeProperty;
import tkcy.tktech.modules.toolmodule.ToolsModule;

interface IToolRecipeBuilder<T extends RecipeBuilder<T>> extends IAdvancedRecipeBuilder<T> {

    default T tool(ToolsModule.GtTool gtTool, int uses) {
        testAndApplyPropertyValue(ToolRecipeProperty.getInstance(), gtTool);
        return testAndApplyPropertyValue(ToolUsesRecipeProperty.getInstance(), uses);
    }

    default T tool(ToolsModule.GtTool gtTool, int uses, EnumFacing toolFacing) {
        this.tool(gtTool, uses);
        return testAndApplyPropertyValue(ToolFacingRecipeProperty.getInstance(), toolFacing);
    }
}
