package tkcy.simpleaddon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

import tkcy.simpleaddon.api.recipes.RecipeRemovalHelper;

public class ZincChain {

    public static void init() {
        // Primitive
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Coke)
                .output(ingot, Zinc)
                .duration(20 * 50)
                .buildAndRegister();

        // Primitive
        PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Coal)
                .output(ingot, Zinc)
                .duration(20 * 70)
                .buildAndRegister();

        // REMOVAL
        RecipeRemovalHelper.removeRecipeByInput(BLAST_RECIPES, dust, Zincite);

        // ZnO + C -> Zn + CO
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Coal)
                .output(ingot, Zinc)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(60)
                .duration(20 * 50)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Carbon)
                .output(ingot, Zinc)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(60)
                .duration(20 * 30)
                .buildAndRegister();

        // ZnO + CO -> Zn + CO2
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .output(ingot, Zinc)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(60)
                .duration(20 * 30)
                .buildAndRegister();

        // ZnSO4 + H2O -> Zn + 1/2 O2 + H2SO4
        ELECTROLYZER_RECIPES.recipeBuilder().duration(200)
                .fluidInputs(Water.getFluid(1000))
                .notConsumable(stickLong, Aluminium)
                .input(dust, ZincSulfate)
                .output(dust, Zinc)
                .fluidOutputs(Oxygen.getFluid(500), SulfuricAcid.getFluid(1000))
                .EUt(100)
                .buildAndRegister();
    }
}
