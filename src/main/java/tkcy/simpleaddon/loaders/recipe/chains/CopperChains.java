package tkcy.simpleaddon.loaders.recipe.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.CarbonDioxide;
import static gregtech.api.unification.ore.OrePrefix.dust;

import gregtech.api.recipes.RecipeMaps;

public class CopperChains {

    public static void init() {
        cupricOxide();
    }

    private static void cupricOxide() {
        // 2 CuO + C -> 2 Cu + CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, CupricOxide, 2)
                .input(dust, Carbon)
                .output(dust, Copper, 2)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // CuO + H2 -> Cu + H2O
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, CupricOxide)
                .fluidInputs(Hydrogen.getFluid(1000))
                .output(dust, Copper)
                .fluidOutputs(Water.getFluid(1000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // CuO + CO -> Cu + CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, CupricOxide)
                .fluidInputs(CarbonMonoxide.getFluid(1000))
                .output(dust, Copper)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();
    }
}
