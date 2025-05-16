package tkcy.tktech.api.recipes.logic.impl;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;

import lombok.Getter;
import lombok.Setter;
import tkcy.tktech.api.recipes.logic.IRecipeLogicContainer;
import tkcy.tktech.api.recipes.logic.IRecipePropertiesValueMap;
import tkcy.tktech.api.recipes.logic.RecipeLogicType;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;
import tkcy.tktech.api.recipes.properties.ToolProperty;
import tkcy.tktech.api.recipes.properties.ToolUsesProperty;
import tkcy.tktech.modules.toolmodule.ToolsModule;

@Setter
@Getter
public class ToolLogic implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private int toolUses;
    private ToolsModule.GtTool currentTool;
    private ToolsModule.GtTool recipeTool;
    private final AbstractRecipeLogic abstractRecipeLogic;

    public ToolLogic(AbstractRecipeLogic abstractRecipeLogic) {
        this.abstractRecipeLogic = abstractRecipeLogic;
    }

    protected boolean isNotWorking() {
        return !abstractRecipeLogic.isWorking();
    }

    private void setRecipeToolUses(Recipe recipe) {
        abstractRecipeLogic.setMaxProgress(ToolUsesProperty.getInstance().getValueFromRecipe(recipe));
    }

    private void setToolFromRecipe(Recipe recipe) {
        setRecipeTool(ToolProperty.getInstance().getValueFromRecipe(recipe));
    }

    /**
     * Little hackery to the standard input consumption logic in used (see
     * {@link Recipe#matches(boolean, IItemHandlerModifiable, IMultipleTankHandler)}).
     * 
     * @return {@code true} if it worked.
     */
    protected boolean addToolStackToInventory(IItemHandler inputInventory, boolean simulate) {
        ToolsModule.GtTool tool = getRecipeTool();
        if (tool == null) return false;
        ItemStack transferredStack = GTTransferUtils.insertItem(inputInventory, tool.getToolStack(), simulate);
        return transferredStack.isEmpty();
    }

    @Override
    public boolean canRecipeLogicProgress() {
        return getCurrentTool() == getRecipeTool();
    }

    @Override
    public boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        setToolFromRecipe(recipe);
        setRecipeToolUses(recipe);
        if (addToolStackToInventory(inputInventory, true)) {
            addToolStackToInventory(inputInventory, false);
        } else return false;
        return true;
    }

    @Override
    public void resetLogic() {
        setRecipeTool(null);
        setCurrentTool(null);
        setToolUses(0);
    }

    @Override
    public void invalidate(IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        resetLogic();
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (isNotWorking()) return;
        getRecipeTool().serialize(compound);
    }

    @Override
    public void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {
        ToolsModule.GtTool tool = ToolsModule.deserializeTool(compound);
        setRecipeTool(tool);
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        recipeParameters.put(ToolProperty.getInstance(), getCurrentTool());
    }

    @Override
    public void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks) {
        handlerStacks.add(getCurrentTool().getToolStack());
    }

    @Override
    public @Nullable ToolLogic getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.TOOL ? this : null;
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.TOOL;
    }
}
