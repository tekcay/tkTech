package tkcy.simpleaddon.loaders.recipe.alloys;

import static gregtech.api.unification.material.Materials.Carbon;
import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.isAlloy;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.MaterialHelper;

public class AlloyingRecipes {

    public static void init() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(isAlloy)
                .forEach(AlloyingRecipes::generateRecipe);
    }

    private static void generateRecipe(Material output) {
        int componentAmount = MaterialHelper.getAmountComponentsSum(output);
        RecipeBuilder<?> recipeBuilder = TKCYSARecipeMaps.ALLOYING.recipeBuilder();

        int carbonAmount = MaterialHelper.getCarbonAmountInMaterial(output);

        output.getMaterialComponents()
                .stream()
                .filter(materialStack -> materialStack.material != Carbon)
                .map(AlloyingRecipes::generateFluidStackFromMaterialStack)
                .forEach(recipeBuilder::fluidInputs);

        if (carbonAmount > 0) recipeBuilder = addCarbonDustToRecipe(recipeBuilder, carbonAmount);
        recipeBuilder = generateNormalRecipe(recipeBuilder, output, componentAmount);
        recipeBuilder.buildAndRegister();
    }

    private static RecipeBuilder<?> addCarbonDustToRecipe(RecipeBuilder<?> recipeBuilder, int carbonAmount) {
        return recipeBuilder.input(OrePrefix.dust, Carbon, carbonAmount);
    }

    private static RecipeBuilder<?> generateNormalRecipe(RecipeBuilder<?> recipeBuilder, Material output,
                                                         int componentAmount) {
        return recipeBuilder.fluidOutputs(output.getFluid(componentAmount * GTValues.L))
                .notConsumable(OrePrefix.dust, output)
                .duration(SECOND * componentAmount);
    }

    public static FluidStack generateFluidStackFromMaterialStack(@NotNull MaterialStack materialStack) {
        Fluid fluid = materialStack.material.getFluid();
        int amount = (int) materialStack.amount;
        return new FluidStack(fluid, fluid.isGaseous() ? amount * 5000 : amount * GTValues.L);
    }
}
