package tkcy.tktech.api.recipes.builders;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import gregtech.api.recipes.RecipeBuilder;

import tkcy.tktech.api.recipes.properties.InputBlockStateRecipeProperty;
import tkcy.tktech.api.recipes.properties.OutputBlockStateRecipeProperty;
import tkcy.tktech.api.utils.BlockStateHelper;

public interface IBlockInWorldRecipeBuilder<T extends RecipeBuilder<T>> extends IAdvancedRecipeBuilder<T> {

    default T inputBlockInWorld(IBlockState blockState) {
        ItemStack itemStack = BlockStateHelper.blockStateToItemStack(blockState);
        return inputBlockInWorld(itemStack);
    }

    default T inputBlockInWorld(Block block) {
        ItemStack input = Item.getItemFromBlock(block).getDefaultInstance();
        return inputBlockInWorld(input);
    }

    default T inputBlockInWorld(ItemStack itemStack) {
        InputBlockStateRecipeProperty.getInstance().testAndApplyPropertyValue(
                itemStack,
                getRecipeStatus(),
                getRecipeBuilder());
        return getRecipeBuilder().inputs(itemStack);
    }

    default T outputBlockInWorld(IBlockState blockState) {
        ItemStack itemStack = BlockStateHelper.blockStateToItemStack(blockState);
        return outputBlockInWorld(itemStack);
    }

    default T outputBlockInWorld(Block block) {
        ItemStack output = Item.getItemFromBlock(block).getDefaultInstance();
        return outputBlockInWorld(output);
    }

    default T outputBlockInWorld(ItemStack itemStack) {
        OutputBlockStateRecipeProperty.getInstance().testAndApplyPropertyValue(
                itemStack,
                getRecipeStatus(),
                getRecipeBuilder());
        return getRecipeBuilder().outputs(itemStack);
    }
}
