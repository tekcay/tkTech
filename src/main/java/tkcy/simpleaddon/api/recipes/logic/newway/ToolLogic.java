package tkcy.simpleaddon.api.recipes.logic.newway;

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
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

@Setter
@Getter
public class ToolLogic implements IRecipeLogic, IRecipePropertiesValueMap {

    private int toolUses;
    private ToolsModule.GtTool currentTool;
    private ToolsModule.GtTool recipeTool;
    private final AbstractRecipeLogic abstractRecipeLogic;

    public ToolLogic(AbstractRecipeLogic abstractRecipeLogic) {
        this.abstractRecipeLogic = abstractRecipeLogic;
    }

    /**
     * Sets {@code maxProgress} from the {@link ToolUsesProperty} in the {@link ToolRecipeBuilder}. Just used for the
     * label.
     */
    protected void setRecipeToolUses(Recipe recipe) {
        abstractRecipeLogic.setMaxProgress(ToolUsesProperty.getInstance().getValueFromRecipe(recipe));
    }

    /**
     * Sets {@code maxProgress} from the {@link ToolProperty} in the {@link ToolRecipeBuilder}. Just used for the label.
     */
    protected void setToolFromRecipe(Recipe recipe) {
        setRecipeTool(ToolProperty.getInstance().getValueFromRecipe(recipe));
    }

    /**
     * Little hackery to the standard input consumption logic in used (see
     * {@link Recipe#matches(boolean, IItemHandlerModifiable, IMultipleTankHandler)}).
     *
     * @param inputInventory
     * @param simulate
     * @return
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
        return false;
    }

    @Override
    public void resetLogic() {
        setRecipeTool(null);
        setCurrentTool(null);
        setToolUses(0);
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (!abstractRecipeLogic.isWorking()) return;
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
    public void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                                    IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {}

    @Override
    public @Nullable ToolLogic getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.TOOL ? this : null;
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.TOOL;
    }
}
