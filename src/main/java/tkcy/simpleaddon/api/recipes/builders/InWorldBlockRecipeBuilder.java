package tkcy.simpleaddon.api.recipes.builders;

import net.minecraft.block.state.IBlockState;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;

import lombok.NoArgsConstructor;
import net.minecraft.item.ItemStack;
import tkcy.simpleaddon.api.recipes.properties.InputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.OutputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.utils.BlockStateHelper;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
@NoArgsConstructor
@SuppressWarnings("unused")
public class InWorldBlockRecipeBuilder extends RecipeBuilder<InWorldBlockRecipeBuilder> {

    @SuppressWarnings("unused")
    public InWorldBlockRecipeBuilder(Recipe recipe, RecipeMap<InWorldBlockRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public InWorldBlockRecipeBuilder(InWorldBlockRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public InWorldBlockRecipeBuilder copy() {
        return new InWorldBlockRecipeBuilder(this);
    }

    public InWorldBlockRecipeBuilder inputBlockInWorld(IBlockState blockState) {
        ItemStack inWorldStateStack = BlockStateHelper.blockStateToItemStack(blockState);
        InputBlockStateRecipeProperty recipeProperty = InputBlockStateRecipeProperty.getInstance();

        return (InWorldBlockRecipeBuilder) recipeProperty.testAndApplyPropertyValue(inWorldStateStack, this.recipeStatus,
                this);
    }

    public InWorldBlockRecipeBuilder outputBlockInWorld(IBlockState blockState) {
        OutputBlockStateRecipeProperty recipeProperty = OutputBlockStateRecipeProperty.getInstance();
        return (InWorldBlockRecipeBuilder) recipeProperty.testAndApplyPropertyValue(blockState, this.recipeStatus,
                this);
    }
}
