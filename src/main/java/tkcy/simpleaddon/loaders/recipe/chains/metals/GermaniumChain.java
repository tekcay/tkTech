package tkcy.simpleaddon.loaders.recipe.chains.metals;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

public class GermaniumChain {

    public static void init() {
        CHEMICAL_RECIPES.recipeBuilder().duration(40).EUt(480)
                .input(dust, Zincite)
                .fluidInputs(SulfuricAcid.getFluid(2000))
                .fluidOutputs(ZincLeachingSolution.getFluid(1000))
                .buildAndRegister();

        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        CENTRIFUGE_RECIPES.recipeBuilder().duration(200)
                .EUt(200)
                .fluidInputs(ZincLeachingSolution.getFluid(1000))
                .output(dustSmall, Zinc)
                .fluidOutputs(ZincLeachingResidue.getFluid(1000))
                .buildAndRegister();

        /*
         * // ZincLeachingResidue [Contains: (H2O)(H2SO4)] -> FeSO4 + 0.5H4GeO4
         * CHEMICAL_RECIPES.recipeBuilder().duration(100).EUt(480)
         * .fluidInputs(ZincLeachingResidue.getFluid(1000))
         * .output(dust, IronSulfate, 6)
         * .fluidOutputs(GermanicAcid.getFluid(500))
         * .buildAndRegister();
         * 
         */

        // ZnO + 2H2SO4 = ZnSO4 + ZincLeachingResidue [Contains: (H2O)(H2SO4)]
        CENTRIFUGE_RECIPES.recipeBuilder().duration(200)
                .EUt(200)
                .fluidInputs(ZincLeachingSolution.getFluid(1000))
                .output(dustSmall, Zinc)
                .fluidOutputs(ZincLeachingResidue.getFluid(1000))
                .buildAndRegister();

        // ZincLeachingResidue [Contains: (H2O)(H2SO4)] = Zn + GeS2
        CENTRIFUGE_RECIPES.recipeBuilder().duration(200)
                .EUt(1200)
                .fluidInputs(ZincLeachingResidue.getFluid(1000))
                .output(dustSmall, Zinc)
                .output(dust, GermaniumSulfide)
                .buildAndRegister();

        // GeS2 + 3 O2 = GeO2 + 3 SO2
        BLAST_RECIPES.recipeBuilder().duration(200)
                .EUt(1200)
                .input(dust, GermaniumSulfide)
                .fluidInputs(Oxygen.getFluid(3000))
                .fluidOutputs(SulfurDioxide.getFluid(3000))
                .output(dust, GermaniumOxide)
                .buildAndRegister();

        // GeO2 + 4 HCl = GeCl4 + 2 H2O
        CHEMICAL_RECIPES.recipeBuilder().duration(200)
                .EUt(1200)
                .input(dust, GermaniumOxide)
                .fluidInputs(HydrochloricAcid.getFluid(4000))
                .fluidOutputs(GermaniumTetrachloride.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .buildAndRegister();

        // GeO2 + 2 H2 = Ge + 2 H2O
        BLAST_RECIPES.recipeBuilder().duration(200)
                .EUt(1200)
                .input(dust, GermaniumOxide)
                .fluidInputs(Hydrogen.getFluid(2000))
                .output(ingot, Germanium)
                .fluidOutputs(Steam.getFluid(2000))
                .buildAndRegister();

        // GeO2 + C = Ge + CO2
        BLAST_RECIPES.recipeBuilder().duration(200)
                .EUt(1200)
                .input(dust, GermaniumOxide)
                .input(dust, Carbon)
                .output(ingot, Germanium)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .buildAndRegister();
    }
}
