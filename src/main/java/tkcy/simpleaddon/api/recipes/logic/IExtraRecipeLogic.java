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
     * @return list of itemStacks to find a recipe with.
     */
    @NotNull
    List<ItemStack> getInputItemStacks();

    void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters);

    @NotNull
    default MetaTileEntity getMetaTileEntity() {
        return getLogic().getMetaTileEntity();
    }
}
