package tkcy.tktech.api.recipes.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;

import lombok.Getter;
import lombok.Setter;
import tkcy.tktech.api.recipes.logic.*;
import tkcy.tktech.api.recipes.properties.FailedOutputRecipeProperty;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;
import tkcy.tktech.modules.NBTHelpers;
import tkcy.tktech.modules.NBTLabel;

@Setter
@Getter
public class FailRecipeLogic implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private List<ItemStack> failedOutputStacks;
    private final AbstractRecipeLogic abstractRecipeLogic;

    public FailRecipeLogic(AbstractRecipeLogic abstractRecipeLogic) {
        this.abstractRecipeLogic = abstractRecipeLogic;
        this.failedOutputStacks = new ArrayList<>();
    }

    protected boolean isNotWorking() {
        return !abstractRecipeLogic.isWorking();
    }

    private void setFailedOutputStacksFromRecipe(Recipe recipe) {
        ItemStack failedOutputStack = FailedOutputRecipeProperty.getInstance().getValueFromRecipe(recipe);
        failedOutputStacks.add(failedOutputStack);
    }

    @Override
    public boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        setFailedOutputStacksFromRecipe(recipe);
        return true;
    }

    @Override
    public void resetLogic() {
        failedOutputStacks.clear();
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (isNotWorking()) return;
        NBTHelpers.itemStacksSerializer.accept(compound, failedOutputStacks, NBTLabel.FAILED_OUTPUT_STACK);
    }

    @Override
    public void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {
        failedOutputStacks = new ArrayList<>(
                NBTHelpers.getDeserializedItemStacks(compound, NBTLabel.FAILED_OUTPUT_STACK));
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {}

    @Override
    public @Nullable FailRecipeLogic getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.FAILED_OUTPUT ? this : null;
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.FAILED_OUTPUT;
    }

    @Override
    public void invalidate(IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        if (abstractRecipeLogic instanceof OnBlockRecipeLogic onBlockRecipeLogic) {
            onBlockRecipeLogic.getRecipeLogicContainer().outputRecipeOutputs(failedOutputStacks, null, outputInventory,
                    outputFluidInventory);
        }
        resetLogic();
    }
}
