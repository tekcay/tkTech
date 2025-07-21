package tkcy.tktech.api.recipes.builders;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.tktech.api.recipes.properties.FailedOutputRecipeProperty;

public interface IFailedStackRecipeBuilder<T extends RecipeBuilder<T>> extends IAdvancedRecipeBuilder<T> {

    /**
     * If the recipe fails, this will be the output.
     */
    default T failedOutputStack(@NotNull ItemStack itemStack) {
        return testAndApplyPropertyValue(FailedOutputRecipeProperty.getInstance(), itemStack);
    }

    /**
     * If the recipe fails, this will be the output.
     */
    default T failedOutputStack(OrePrefix orePrefix, Material material, int amount) {
        ItemStack itemStack = OreDictUnifier.get(orePrefix, material, amount);
        return failedOutputStack(itemStack);
    }
}
