package tkcy.simpleaddon.api.recipes.logic;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public interface IToolRecipeLogic extends IExtraRecipeLogic {

    @NotNull
    static IToolRecipeLogic getToolRecipeLogic(IExtraRecipeLogic abstractRecipeLogic) {
        return (IToolRecipeLogic) abstractRecipeLogic;
    }

    static void resetToolLogic(IExtraRecipeLogic recipeLogic) {
        IToolRecipeLogic toolRecipeLogic = getToolRecipeLogic(recipeLogic);
        toolRecipeLogic.resetToolLogic();
    }

    void setRecipeTool(ToolsModule.GtTool recipeTool);

    void setCurrentTool(ToolsModule.GtTool recipeTool);

    void setToolUses(int toolUses);

    ToolsModule.GtTool getRecipeTool();

    ToolsModule.GtTool getCurrentTool();

    int getToolUses();

    default void resetToolLogic() {
        setRecipeTool((ToolsModule.GtTool) null);
        setCurrentTool(null);
        setToolUses(0);
    }

    default boolean canToolRecipeLogicProgress(ToolsModule.GtTool tool) {
        return tool == getRecipeTool();
    }

    /**
     * Sets {@code maxProgress} from the {@link ToolUsesProperty} in the {@link ToolRecipeBuilder}. Just used for the
     * label.
     */
    default void setRecipeToolUses(Recipe recipe) {
        getLogic().setMaxProgress(ToolUsesProperty.getInstance().getValueFromRecipe(recipe));
    }

    /**
     * Sets {@code maxProgress} from the {@link ToolProperty} in the {@link ToolRecipeBuilder}. Just used for the label.
     */
    default void setRecipeTool(Recipe recipe) {
        setRecipeTool(ToolProperty.getInstance().getValueFromRecipe(recipe));
    }

    default void serializeToolRecipeLogic(NBTTagCompound compound) {
        if (!getLogic().isWorking()) return;
        getRecipeTool().serialize(compound);
    }

    default void deserializeToolRecipeLogic(@NotNull NBTTagCompound compound) {
        ToolsModule.GtTool tool = ToolsModule.deserializeTool(compound);
        setRecipeTool(tool);
    }

    default void updateRecipeToolParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        recipeParameters.put(ToolProperty.getInstance(), getCurrentTool());
    }

    /**
     * Little hackery to the standard input consumption logic in used (see
     * {@link Recipe#matches(boolean, IItemHandlerModifiable, IMultipleTankHandler)}).
     * 
     * @param inputInventory
     * @param simulate
     * @return
     */
    default boolean addToolStackToInventory(IItemHandler inputInventory, boolean simulate) {
        ToolsModule.GtTool tool = getRecipeTool();
        if (tool == null) return false;
        ItemStack transferredStack = GTTransferUtils.insertItem(inputInventory, tool.getToolStack(), false);
        return transferredStack.isEmpty();
    }
}
