package tkcy.simpleaddon.api.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ingredients.GTRecipeInput;
import gregtech.api.util.GTUtility;

import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.ToolsModule;

public class RecipeSearchHelpers {

    @Nullable
    public static Recipe findRecipeWithTool(@NotNull RecipeMap<?> recipeMap, @NotNull ToolsModule.GtTool gtTool,
                                            @NotNull IItemHandlerModifiable inputItemInventory,
                                            @NotNull IMultipleTankHandler inputFluidInventory) {
        return findRecipeWithTool(recipeMap, gtTool, GTUtility.itemHandlerToList(inputItemInventory),
                GTUtility.fluidHandlerToList(inputFluidInventory));
    }

    @Nullable
    public static Recipe findRecipeWithTool(@NotNull RecipeMap<?> recipeMap, @NotNull ToolsModule.GtTool gtTool,
                                            @NotNull List<ItemStack> inputItemStacks,
                                            List<FluidStack> inputFluidStacks) {
        return recipeMap.getRecipeList().stream()
                .filter(recipe -> recipe.hasProperty(ToolProperty.getInstance()))
                .filter(recipe -> ToolProperty.getInstance().getValueFromRecipe(recipe).equals(gtTool))
                .filter(recipe -> recipe.hasProperty(ToolUsesProperty.getInstance()))
                .filter(recipe -> recipe.matches(false, inputItemStacks, inputFluidStacks))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns a list of itemStacks made of the list of {@code ItemStack} contained in {@code inputInventory} appended
     * by the used tool.
     */
    public static List<ItemStack> getAppendedInputsTool(@NotNull IItemHandlerModifiable inputInventory,
                                                        @NotNull ToolsModule.GtTool gtTool) {
        List<ItemStack> list = new ArrayList<>();
        list.add(gtTool.getToolStack());
        list.addAll(GTUtility.itemHandlerToList(inputInventory));
        return list;
    }

    @Nullable
    public static Recipe findRecipeWithToolp(@NotNull RecipeMap<?> recipeMap, @NotNull ToolsModule.GtTool gtTool,
                                             @NotNull List<ItemStack> inputItemStacks,
                                             @NotNull List<FluidStack> inputFluidStacks) {
        TKCYSALog.logger.info("starts method");
        if (inputItemStacks.isEmpty()) {
            return null;
        }

        for (Recipe recipe : recipeMap.getRecipeList()) {
            if (!recipe.hasProperty(ToolProperty.getInstance())) {
                TKCYSALog.logger.info("does not have toolProperty");
                continue;
            }

            ToolsModule.GtTool tool = ToolProperty.getInstance().getValueFromRecipe(recipe);

            TKCYSALog.logger.info("this recipe has tool " + tool.name());
            TKCYSALog.logger.info("expected : " + gtTool);

            // if (!tool.equals(gtTool)) continue;

            if (!recipe.hasProperty(ToolUsesProperty.getInstance())) {
                TKCYSALog.logger.info("does not have toolUseProperty");
                continue;
            }

            int toolUses = ToolUsesProperty.getInstance().getValueFromRecipe(recipe);

            TKCYSALog.logger.info("recipeUses : " + toolUses);
            recipe.getOutputs()
                    .forEach(itemStack -> TKCYSALog.logger
                            .info("recipeOutputs : " + TKCYSALog.itemStackToString(itemStack)));
            inputItemStacks
                    .forEach(itemStack -> TKCYSALog.logger
                            .info("provided stacks : " + TKCYSALog.itemStackToString(itemStack)));

            List<ItemStack> recipeInputStacks = accept(recipe);

            TKCYSALog.logger.info("recipeItemStacks.size() : " + inputItemStacks.size());
            if (inputItemStacks.size() > 0) {
                for (ItemStack stack : recipeInputStacks) {
                    TKCYSALog.logger.info("recipeItemStacks : " + TKCYSALog.itemStackToString(stack));
                }
            }
            ItemStack toolStack = gtTool.getToolStack();
            // TKCYSALog.logger.info(TKCYSALog.itemStackToString(toolStack));
            // inputItemStacks.add(toolStack);
            List<ItemStack> toSend = new ArrayList<>();
            toSend.add(toolStack);
            toSend.addAll(inputItemStacks);

            if (!recipe.matches(false, toSend, inputFluidStacks)) {
                TKCYSALog.logger.info("recipe does not match");
                continue;
            }

            return recipe;

        }

        return null;
    }

    public static List<ItemStack> accept(Recipe recipe) {
        List<ItemStack> recipeInputStacks = new ArrayList<>();
        for (GTRecipeInput gtRecipeInput : recipe.getInputs()) {
            for (int i = 0; i < gtRecipeInput.getInputStacks().length; i++) {
                recipeInputStacks.add(gtRecipeInput.getInputStacks()[i]);
                TKCYSALog.logger.info(
                        "recipe itemStack : " +
                                TKCYSALog.itemStackToString(gtRecipeInput.getInputStacks()[i]));
            }
        }
        return recipeInputStacks;
    }
}
