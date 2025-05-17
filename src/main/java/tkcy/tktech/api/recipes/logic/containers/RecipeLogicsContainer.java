package tkcy.tktech.api.recipes.logic.containers;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.tktech.api.recipes.logic.IRecipePropertiesValueMap;
import tkcy.tktech.api.recipes.logic.RecipeLogicType;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;

@Getter
@AllArgsConstructor
public class RecipeLogicsContainer implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private final AbstractRecipeLogic abstractRecipeLogic;
    private final Set<IRecipeLogicContainer> recipeLogics;

    public RecipeLogicsContainer(AbstractRecipeLogic abstractRecipeLogic, IRecipeLogicContainer... recipeLogics) {
        this.abstractRecipeLogic = abstractRecipeLogic;
        this.recipeLogics = new HashSet<>(Arrays.asList(recipeLogics));
    }

    @Override
    public boolean canRecipeLogicProgress() {
        return recipeLogics.stream()
                .allMatch(IRecipeLogicContainer::canRecipeLogicProgress);
    }

    @Override
    public boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        return recipeLogics.stream()
                .allMatch(iRecipeLogic -> iRecipeLogic.prepareRecipe(recipe, inputInventory));
    }

    @Override
    public void resetLogic() {
        recipeLogics.forEach(IRecipeLogicContainer::resetLogic);
    }

    @Override
    public void invalidate(IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        recipeLogics.forEach(
                recipeLogicContainer -> recipeLogicContainer.invalidate(outputInventory, outputFluidInventory));
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        recipeLogics.forEach(iRecipeLogic -> iRecipeLogic.serializeRecipeLogic(compound));
    }

    @Override
    public void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {
        recipeLogics.forEach(iRecipeLogic -> iRecipeLogic.deserializeRecipeLogic(compound));
    }

    @Override
    public void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks) {
        recipeLogics
                .forEach(iRecipeLogic -> iRecipeLogic.appendToInputsForRecipeSearch(handlerStacks, handlerFluidStacks));
    }

    @Override
    public void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                                    IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        recipeLogics.forEach(iRecipeLogic -> iRecipeLogic.outputRecipeOutputs(outputStacks, outputFluidStacks,
                outputInventory, outputFluidInventory));
    }

    @Override
    public @Nullable IRecipeLogicContainer getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogics.stream()
                .filter(iRecipeLogic -> iRecipeLogic.hasRecipeLogicType(recipeLogicType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        recipeLogics.forEach(iRecipeLogic -> iRecipeLogic.updateRecipeParameters(recipeParameters));
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogics.stream()
                .anyMatch(iRecipeLogic -> iRecipeLogic.hasRecipeLogicType(recipeLogicType));
    }

    @Override
    public void postSetupRecipe(@NotNull Recipe recipe) {
        recipeLogics.forEach(recipeLogicContainer -> recipeLogicContainer.postSetupRecipe(recipe));
    }
}
