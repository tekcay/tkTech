package tkcy.tktech.api.recipes.builders;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.ValidationResult;

import lombok.NoArgsConstructor;
import tkcy.tktech.api.recipes.properties.*;
import tkcy.tktech.api.utils.BlockStateHelper;
import tkcy.tktech.modules.toolmodule.ToolsModule;

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
        ItemStack itemStack = BlockStateHelper.blockStateToItemStack(blockState);
        return inputBlockInWorld(itemStack);
    }

    public AdvancedRecipeBuilder inputBlockInWorld(Block block) {
        ItemStack input = Item.getItemFromBlock(block).getDefaultInstance();
        return inputBlockInWorld(input);
    }

    public AdvancedRecipeBuilder inputBlockInWorld(ItemStack itemStack) {
        this.inputs(itemStack);
        InputBlockStateRecipeProperty recipeProperty = InputBlockStateRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) recipeProperty.testAndApplyPropertyValue(itemStack, this.recipeStatus, this);
    }

    public AdvancedRecipeBuilder outputBlockInWorld(IBlockState blockState) {
        ItemStack itemStack = BlockStateHelper.blockStateToItemStack(blockState);
        return outputBlockInWorld(itemStack);
    }

    public AdvancedRecipeBuilder outputBlockInWorld(Block block) {
        ItemStack output = Item.getItemFromBlock(block).getDefaultInstance();
        return outputBlockInWorld(output);
    }

    public AdvancedRecipeBuilder outputBlockInWorld(ItemStack itemStack) {
        this.outputs(itemStack);
        OutputBlockStateRecipeProperty recipeProperty = OutputBlockStateRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) recipeProperty.testAndApplyPropertyValue(itemStack, this.recipeStatus, this);
    }

    private AdvancedRecipeBuilder tool(ToolsModule.GtTool gtTool) {
        ToolProperty toolProperty = ToolProperty.getInstance();
        return (AdvancedRecipeBuilder) toolProperty.testAndApplyPropertyValue(gtTool, this.recipeStatus, this);
    }

    private AdvancedRecipeBuilder toolUses(int uses) {
        ToolUsesProperty toolUsesProperty = ToolUsesProperty.getInstance();
        return (AdvancedRecipeBuilder) toolUsesProperty.testAndApplyPropertyValue(uses, this.recipeStatus, this);
    }

    private AdvancedRecipeBuilder toolFacing(EnumFacing toolFacing) {
        ToolFacingProperty toolFacingProperty = ToolFacingProperty.getInstance();
        return (AdvancedRecipeBuilder) toolFacingProperty.testAndApplyPropertyValue(toolFacing, this.recipeStatus,
                this);
    }

    public AdvancedRecipeBuilder tool(ToolsModule.GtTool gtTool, int uses) {
        this.tool(gtTool);
        this.toolUses(uses);
        return this;
    }

    public AdvancedRecipeBuilder tool(ToolsModule.GtTool gtTool, int uses, EnumFacing toolFacing) {
        this.tool(gtTool, uses);
        this.toolFacing(toolFacing);
        return this;
    }

    /**
     * If the recipe fails, this will be the output.
     */
    public AdvancedRecipeBuilder failedOutputStack(@NotNull ItemStack itemStack) {
        FailedOutputRecipeProperty property = FailedOutputRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) property.testAndApplyPropertyValue(itemStack, this.recipeStatus, this);
    }

    /**
     * If the recipe fails, this will be the output.
     */
    public AdvancedRecipeBuilder failedOutputStack(OrePrefix orePrefix, Material material, int amount) {
        ItemStack itemStack = OreDictUnifier.get(orePrefix, material, amount);
        return failedOutputStack(itemStack);
    }

    public AdvancedRecipeBuilder hideDuration() {
        this.hideDuration = true;
        return this;
    }

    public AdvancedRecipeBuilder hideEnergy() {
        this.useAndDisplayEnergy = false;
        return this;
    }

    public AdvancedRecipeBuilder hideEnergyAndDuration() {
        this.hideDuration();
        this.hideEnergy();
        return this;
    }

    public AdvancedRecipeBuilder duration(int duration, float recipeDurationRate) {
        duration(duration);
        DurationModifierRecipeProperty recipeProperty = DurationModifierRecipeProperty.getInstance();
        recipeProperty.testAndApplyPropertyValue(recipeDurationRate, this.recipeStatus, this);
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
