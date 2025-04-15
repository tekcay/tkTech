package tkcy.simpleaddon.api.recipes.builders;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tkcy.simpleaddon.api.recipes.properties.*;
import tkcy.simpleaddon.api.utils.BlockStateHelper;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

@NoArgsConstructor
public class AdvancedRecipeBuilder extends RecipeBuilder<AdvancedRecipeBuilder> {

    protected boolean hideDuration = false;
    protected boolean useAndDisplayEnergy = true;

    @SuppressWarnings("unused")
    public AdvancedRecipeBuilder(Recipe recipe, RecipeMap<AdvancedRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public AdvancedRecipeBuilder(AdvancedRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public AdvancedRecipeBuilder copy() {
        return new AdvancedRecipeBuilder(this);
    }




    public AdvancedRecipeBuilder inputBlockInWorld(IBlockState blockState) {
        Block block = blockState.getBlock();
        ItemStack input = Item.getItemFromBlock(block).getDefaultInstance();
        return inputBlockInWorld(input, blockState);
    }

    public AdvancedRecipeBuilder inputBlockInWorld(Block block) {
        ItemStack input = Item.getItemFromBlock(block).getDefaultInstance();
        IBlockState blockState = block.getDefaultState();
        return inputBlockInWorld(input, blockState);
    }

    private AdvancedRecipeBuilder inputBlockInWorld(ItemStack itemStack, IBlockState blockState) {
        this.inputs(itemStack);
        InputBlockStateRecipeProperty recipeProperty = InputBlockStateRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) recipeProperty.testAndApplyPropertyValue(blockState, this.recipeStatus, this);
    }


    public AdvancedRecipeBuilder outputBlockInWorld(IBlockState blockState) {
        Block block = blockState.getBlock();
        ItemStack output = Item.getItemFromBlock(block).getDefaultInstance();
        return outputBlockInWorld(output, blockState);
    }

    public AdvancedRecipeBuilder outputBlockInWorld(Block block) {
        ItemStack output = Item.getItemFromBlock(block).getDefaultInstance();
        IBlockState blockState = block.getDefaultState();
        return outputBlockInWorld(output, blockState);
    }

    private AdvancedRecipeBuilder outputBlockInWorld(ItemStack itemStack, IBlockState blockState) {
        this.outputs(itemStack);
        OutputBlockStateRecipeProperty recipeProperty = OutputBlockStateRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) recipeProperty.testAndApplyPropertyValue(blockState, this.recipeStatus, this);
    }

    public AdvancedRecipeBuilder tool(ToolsModule.GtTool gtTool) {
        ToolProperty toolProperty = ToolProperty.getInstance();
        return (AdvancedRecipeBuilder) toolProperty.testAndApplyPropertyValue(gtTool, this.recipeStatus, this);
    }

    public AdvancedRecipeBuilder toolUses(int uses) {
        ToolUsesProperty toolUsesProperty = ToolUsesProperty.getInstance();
        return (AdvancedRecipeBuilder) toolUsesProperty.testAndApplyPropertyValue(uses, this.recipeStatus, this);
    }

    public AdvancedRecipeBuilder hideDuration() {
        this.hideDuration = true;
        return this;
    }

    public AdvancedRecipeBuilder useAndDisplayEnergy(boolean useAndDisplayEnergy) {
        this.useAndDisplayEnergy = useAndDisplayEnergy;
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (this.hideDuration) {
            this.duration(10);
            applyProperty(HideDurationProperty.getInstance(), true);
        }

        if (!useAndDisplayEnergy) {
            applyProperty(PrimitiveProperty.getInstance(), true);
            this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        }

        return super.build();
    }
}
