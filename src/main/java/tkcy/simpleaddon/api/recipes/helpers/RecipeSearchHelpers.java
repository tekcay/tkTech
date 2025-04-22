package tkcy.simpleaddon.api.recipes.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@UtilityClass
public class RecipeSearchHelpers {

    @Nullable
    public static Recipe findRecipeWithTool(@NotNull RecipeMap<?> recipeMap, @NotNull ToolsModule.GtTool gtTool,
                                            @NotNull IItemHandlerModifiable inputItemInventory,
                                            @NotNull IMultipleTankHandler inputFluidInventory) {
        return findRecipeWithTool(recipeMap, gtTool, GTUtility.itemHandlerToList(inputItemInventory),
                GTUtility.fluidHandlerToList(inputFluidInventory));
    }

    @WorkingTool
    @Nullable
    public static Recipe findRecipeWithTool(@NotNull RecipeMap<?> recipeMap, @NotNull ToolsModule.GtTool gtTool,
                                            @NotNull ItemStack itemStack) {
        return findRecipeWithTool(recipeMap, gtTool, Collections.singletonList(itemStack), null);
    }

    @WorkingTool
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
    @WorkingTool
    public static List<ItemStack> getAppendedInputsTool(@NotNull IItemHandlerModifiable handlerInventory,
                                                        @NotNull ToolsModule.GtTool gtTool) {
        List<ItemStack> list = GTUtility.itemHandlerToList(handlerInventory);
        appendStacksTool(list, gtTool);
        return list;
    }

    @WorkingTool
    public static void appendStacksTool(@NotNull List<ItemStack> itemStacks,
                                        @NotNull ToolsModule.GtTool gtTool) {
        itemStacks.add(gtTool.getToolStack());
    }

    @Nullable
    public static Recipe findFirstRecipeWithProperties(@NotNull RecipeMap<?> recipeMap,
                                                       @NotNull Map<IRecipePropertyHelper<?>, ?> recipePropertiesToValue) {
        return recipeMap.getRecipeList()
                .stream()
                .filter(recipe -> doesRecipeMatchProperties(recipe, recipePropertiesToValue))
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public static Recipe findFirstRecipeWithProperties(@NotNull RecipeMap<?> recipeMap,
                                                       @NotNull Map<IRecipePropertyHelper<?>, ?> recipePropertiesToValue,
                                                       List<ItemStack> inputs, List<FluidStack> fluidsInput) {
        return recipeMap.getRecipeList()
                .stream()
                .filter(recipe -> doesRecipeMatchProperties(recipe, recipePropertiesToValue))
                .filter(recipe -> recipe.matches(false, inputs, fluidsInput))
                .findFirst()
                .orElse(null);
    }

    public static boolean doesRecipeMatchProperties(@NotNull Recipe recipe,
                                                    @NotNull Map<IRecipePropertyHelper<?>, ?> recipePropertiesToValue) {
        for (Map.Entry<IRecipePropertyHelper<?>, ?> entry : recipePropertiesToValue.entrySet()) {
            IRecipePropertyHelper<?> recipeProperty = entry.getKey();
            if (!recipeProperty.hasRecipePropertyUncastedValue(recipe, entry.getValue())) return false;
        }
        return true;
    }

    @Nullable
    public static Recipe findFirstRecipeWithProperties(@NotNull RecipeMap<?> recipeMap,
                                                       @NotNull Map<IRecipePropertyHelper<?>, ?> recipePropertiesToValue,
                                                       List<ItemStack> itemStacksInput,
                                                       List<FluidStack> fluidStacksInput, long voltage) {
        Recipe recipe = recipeMap.findRecipe(voltage, itemStacksInput, fluidStacksInput);
        boolean doesMatch = false;

        for (Map.Entry<IRecipePropertyHelper<?>, ?> entry : recipePropertiesToValue.entrySet()) {

            IRecipePropertyHelper<?> recipeProperty = entry.getKey();
            doesMatch = recipeProperty.hasRecipePropertyUncastedValue(recipe, entry.getValue());
            if (!doesMatch) break;
        }
        if (doesMatch) return recipe;

        return null;
    }

    /**
     * Used for debugging
     */
    @WorkingTool
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

    @WorkingTool
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
