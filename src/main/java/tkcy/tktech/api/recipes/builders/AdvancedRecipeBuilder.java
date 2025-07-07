package tkcy.tktech.api.recipes.builders;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.AbstractRecipeLogic;
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
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.modules.toolmodule.ToolsModule;

@NoArgsConstructor
public class AdvancedRecipeBuilder extends RecipeBuilder<AdvancedRecipeBuilder> {

    protected boolean hideDuration = false;
    protected boolean useAndDisplayEnergy = true;
    protected boolean hasInputsChemicalStructures = false;
    protected boolean hasOutputsChemicalStructures = false;
    protected final Set<Material> inputMaterialsChemStructure = new HashSet<>();
    protected final Set<Material> outputMaterialsChemStructure = new HashSet<>();

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
        ToolRecipeProperty toolProperty = ToolRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) toolProperty.testAndApplyPropertyValue(gtTool, this.recipeStatus, this);
    }

    private AdvancedRecipeBuilder toolUses(int uses) {
        ToolUsesRecipeProperty toolUsesProperty = ToolUsesRecipeProperty.getInstance();
        return (AdvancedRecipeBuilder) toolUsesProperty.testAndApplyPropertyValue(uses, this.recipeStatus, this);
    }

    private AdvancedRecipeBuilder toolFacing(EnumFacing toolFacing) {
        ToolFacingRecipeProperty toolFacingProperty = ToolFacingRecipeProperty.getInstance();
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

    /**
     * Set the recipe {@link #duration}.
     * 
     * @param duration
     * @param recipeDurationRate a value that can modify the recipe duration at
     *                           {@link AbstractRecipeLogic#setupRecipe(Recipe)}
     */
    public AdvancedRecipeBuilder duration(int duration, float recipeDurationRate) {
        duration(duration);
        DurationModifierRecipeProperty recipeProperty = DurationModifierRecipeProperty.getInstance();
        recipeProperty.testAndApplyPropertyValue(recipeDurationRate, this.recipeStatus, this);
        return this;
    }

    public AdvancedRecipeBuilder chemicalStructures(boolean isInput, Material... materials) {
        if (isInput) {
            inputMaterialsChemStructure.addAll(Arrays.asList(materials));
        } else {
            outputMaterialsChemStructure.addAll(Arrays.asList(materials));
        }
        return this;
    }

    @Override
    public ValidationResult<Recipe> build() {
        if (BooleanHelper.doesAnyNotMatch(Set::isEmpty, inputMaterialsChemStructure, outputMaterialsChemStructure)) {

            ChemicalStructuresRecipeProperty recipeProperty = ChemicalStructuresRecipeProperty.getInstance();
            recipeProperty.testAndApplyPropertyValue(
                    new ChemicalStructuresRecipeProperty.Container(inputMaterialsChemStructure,
                            outputMaterialsChemStructure),
                    recipeStatus,
                    this);
        }

        if (this.hideDuration) {
            this.duration(10);
            applyProperty(HideDurationRecipeProperty.getInstance(), true);
        }

        if (!useAndDisplayEnergy) {
            applyProperty(PrimitiveProperty.getInstance(), true);
            this.EUt(1); // secretly force to 1 to allow recipe matching to work properly
        }

        return super.build();
    }
}
