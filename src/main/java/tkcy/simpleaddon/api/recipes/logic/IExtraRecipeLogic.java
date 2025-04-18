package tkcy.simpleaddon.api.recipes.logic;

import java.util.List;
import java.util.Map;

import gregtech.api.recipes.Recipe;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;

public interface IExtraRecipeLogic {

    @NotNull
    AbstractRecipeLogic getLogic();

    /**
     * @return list of itemStacks to find a recipe with. Might not match with {@link #getItemStacksToConsume(Recipe recipe))}.
     */
    @NotNull
    List<ItemStack> getInputItemStacks();

    /**
     * @return the itemStacks that will effectively be consumed. Might not match with {@link #getInputItemStacks()}.
     */
    @NotNull
    List<ItemStack> getItemStacksToConsume(@NotNull Recipe recipe);

    void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters);

    @NotNull
    default MetaTileEntity getMetaTileEntity() {
        return getLogic().getMetaTileEntity();
    }
}
