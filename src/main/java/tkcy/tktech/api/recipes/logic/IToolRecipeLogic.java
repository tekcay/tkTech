package tkcy.tktech.api.recipes.logic;

import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.Nullable;

import tkcy.tktech.api.recipes.logic.impl.ToolLogic;
import tkcy.tktech.modules.toolmodule.ToolsModule;

public interface IToolRecipeLogic {

    void runToolRecipeLogic(ToolsModule.GtTool tool, EnumFacing toolClickFacing);

    @Nullable
    default ToolLogic getToolLogic() {
        if (this instanceof IExtraRecipeLogic iExtraRecipeLogic) {
            return (ToolLogic) iExtraRecipeLogic.getRecipeLogicContainer().getInstance(RecipeLogicType.TOOL);
        }
        return null;
    }
}
