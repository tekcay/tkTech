package tkcy.tktech.loaders.recipe.chains.metals;

import static gregtech.api.recipes.RecipeMaps.BLAST_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.tktech.api.recipes.recipemaps.TKCYSARecipeMaps.ADVANCED_ELECTROLYSIS;
import static tkcy.tktech.api.recipes.recipemaps.TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.ZincSulfate;
import static tkcy.tktech.api.unification.ore.TKCYSAOrePrefix.anode;
import static tkcy.tktech.api.unification.ore.TKCYSAOrePrefix.cathode;

import gregtech.api.GTValues;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.helpers.RecipeRemovalHelper;

@UtilityClass
public class ZincChain {

    public static void init() {
        // Primitive
        FLUID_PRIMITIVE_BLAST.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Coke)
                .fluidOutputs(Zinc.getFluid(GTValues.L))
                .duration(20 * 100)
                .buildAndRegister();

        FLUID_PRIMITIVE_BLAST.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Coal)
                .fluidOutputs(Zinc.getFluid(GTValues.L))
                .duration(20 * 120)
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
                .duration(20 * 20)
                .buildAndRegister();

        BLAST_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .input(dust, Carbon)
                .output(ingot, Zinc)
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .EUt(60)
                .duration(20 * 10)
                .buildAndRegister();

        // ZnO + CO -> Zn + CO2
        BLAST_RECIPES.recipeBuilder()
                .input(dust, Zincite)
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .output(ingot, Zinc)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .EUt(60)
                .duration(20 * 20)
                .buildAndRegister();

        // ZnSO4 + H2O -> Zn + 1/2 O2 + H2SO4
        ADVANCED_ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Water.getFluid(1000))
                .notConsumable(anode, Carbon)
                .notConsumable(cathode, Carbon)
                .input(dust, ZincSulfate)
                .output(dust, Zinc)
                .fluidOutputs(Oxygen.getFluid(500))
                .fluidOutputs(SulfuricAcid.getFluid(1000))
                .EUt(100)
                .buildAndRegister();
    }
}
