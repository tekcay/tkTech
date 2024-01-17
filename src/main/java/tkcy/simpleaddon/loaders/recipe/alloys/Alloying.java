package tkcy.simpleaddon.loaders.recipe.alloys;

import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.isAlloy;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.MaterialStack;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.TKCYSAUtil;

public class Alloying {

    public static void init() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(isAlloy)
                .forEach(Alloying::generateRecipe);
    }

    private static void generateRecipe(Material output) {
        int componentAmount = TKCYSAUtil.getAmountComponentsSum(output);
        RecipeBuilder<?> recipeBuilder = TKCYSARecipeMaps.ALLOYING.recipeBuilder();

        output.getMaterialComponents()
                .stream()
                .map(Alloying::generateFluidStackFromMaterialStack)
                .forEach(recipeBuilder::fluidInputs);

        recipeBuilder.fluidOutputs(output.getFluid(componentAmount * GTValues.L))
                .duration(SECOND * componentAmount)
                .buildAndRegister();
    }

    public static FluidStack generateFluidStackFromMaterialStack(@NotNull MaterialStack materialStack) {
        Fluid fluid = materialStack.material.getFluid();
        int amount = (int) materialStack.amount;
        return new FluidStack(fluid, fluid.isGaseous() ? amount * 5000 : amount * GTValues.L);
    }
}
