package tkcy.simpleaddon.api.recipes.logic;

import org.jetbrains.annotations.Nullable;

import tkcy.simpleaddon.api.recipes.logic.newway.IRecipeLogic;
import tkcy.simpleaddon.api.recipes.logic.newway.RecipeLogicType;
import tkcy.simpleaddon.api.recipes.logic.newway.ToolLogic;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public interface IToolRecipeLogic {

    void runToolRecipeLogic(ToolsModule.GtTool tool);

    @Nullable
    default ToolLogic getToolLogic(IRecipeLogic recipeLogic) {
        IRecipeLogic logic = recipeLogic.getInstance(RecipeLogicType.TOOL);
        if (logic instanceof ToolLogic) {
            return (ToolLogic) logic;
        }
        return null;
    }
}
