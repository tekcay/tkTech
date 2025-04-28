package tkcy.simpleaddon.api.recipes.logic;

import org.jetbrains.annotations.Nullable;

import tkcy.simpleaddon.api.recipes.logic.impl.ToolLogic;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public interface IToolRecipeLogic {

    void runToolRecipeLogic(ToolsModule.GtTool tool);

    @Nullable
    default ToolLogic getToolLogic() {
        if (this instanceof IExtraRecipeLogic iExtraRecipeLogic) {
            return (ToolLogic) iExtraRecipeLogic.getRecipeLogicContainer().getInstance(RecipeLogicType.TOOL);
        }
        return null;
    }
}
