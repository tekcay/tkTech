package tkcy.simpleaddon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class AluminiumChain {

    public static void init() {

        // HexafluorosilicAcid
        // 6 HF + SiO2 -> H2SiF6 + 2 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SiliconDioxide)
                .fluidInputs(HydrofluoricAcid.getFluid(6000))
                .fluidOutputs(HexafluorosilicAcid.getFluid(1000))
                .fluidOutputs(Water.getFluid(2000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        // AluminiumFluoride

        // 2 Al + 3 F2 -> 2 AlF3
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Aluminium, 2)
                .fluidOutputs(Alumina.getFluid(GTValues.L / 2))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        // 5 Al2O3 + 9 H2SiF6 -> 8 AlF3 + 3 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, Alumina, 5)
                .fluidInputs(HexafluorosilicAcid.getFluid(9000))
                .fluidOutputs(AluminiumFluoride.getFluid(8000))
                .fluidOutputs(Water.getFluid(3000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        // Cryolite
        // 18 NaOH + 5 Al2O3 + 24 HF -> 20 Cryolite + 27 H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .input(dust, SodiumHydroxide, 18)
                .input(dust, Alumina, 5)
                .fluidInputs(HydrofluoricAcid.getFluid(24000))
                .fluidOutputs(Water.getFluid(27000))
                .fluidOutputs(Cryolite.getFluid(20000))
                .duration(100)
                .EUt(30)
                .buildAndRegister();

        // STEP 1

        // Bauxite + 1.5 KOH -> 2 KAlO2 + (2 FeTiO3 + TiO2) tinyDust
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .fluidInputs(Steam.getFluid(2000))
                .input(dust, Bauxite)
                .input(dustSmall, PotassiumHydroxide, 6)
                .output(dust, PotashTreatedBauxite)
                .fluidOutputs(DistilledWater.getFluid(200))
                .duration(30 * SECOND)
                .EUt(15)
                .buildAndRegister();

        // Bauxite + 1.5 NaOH -> 2 NaAlO2 + (2 FeTiO3 + TiO2) tinyDust
        RecipeMaps.AUTOCLAVE_RECIPES.recipeBuilder()
                .fluidInputs(Steam.getFluid(2000))
                .input(dust, Bauxite)
                .input(dustSmall, SodiumHydroxide, 6)
                .output(dust, SodaTreatedBauxite)
                .fluidOutputs(DistilledWater.getFluid(200))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // STEP 2

        // 4 KAlO2 + 6 H2O -> 7 Al(OH)3 + 3 KOH
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, PotassiumAluminate, 4)
                .fluidInputs(DistilledWater.getFluid(6000))
                .fluidOutputs(TreatedPotassiumAluminate.getFluid(1000))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // 4 NaAlO2 + 6 H2O -> 7 Al(OH)3 + 3 NaOH
        CHEMICAL_BATH_RECIPES.recipeBuilder()
                .input(dust, SodiumAluminate, 4)
                .fluidInputs(DistilledWater.getFluid(6000))
                .fluidOutputs(TreatedSodiumAluminate.getFluid(1000))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // DRYING

        TKCYSARecipeMaps.DRYING.recipeBuilder()
                .fluidInputs(TreatedPotassiumAluminate.getFluid(1000))
                .fluidOutputs(Steam.getFluid(8000))
                .output(dust, DriedTreatedPotassiumAluminate)
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        TKCYSARecipeMaps.DRYING.recipeBuilder()
                .fluidInputs(TreatedSodiumAluminate.getFluid(1000))
                .fluidOutputs(Steam.getFluid(8000))
                .output(dust, DriedTreatedSodiumAluminate)
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // STEP 3
        // 2 Al(OH)3 -> Al2O3 (l) + 3 H2O

        EXTRACTOR_RECIPES.recipeBuilder()
                .input(dust, AluminiumHydroxide, 2)
                .fluidOutputs(Alumina.getFluid(GTValues.L))
                .duration(75 * SECOND)
                .EUt(30)
                .buildAndRegister();

        // STEP 3
        // 2 Al2O3 + 3 C -> 4 Al + 3 CO2

        TKCYSARecipeMaps.ADVANCED_ELECTROLYSIS.recipeBuilder()
                .fluidInputs(Alumina.getFluid(GTValues.L * 2))
                .fluidInputs(AluminiumFluoride.getFluid(2))
                .fluidInputs(Cryolite.getFluid(1))
                .input(stickLong, Carbon, 3)
                .notConsumable(stickLong, Carbon, 64)
                .output(dust, Aluminium, 4)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .duration(75 * SECOND)
                .EUt(60)
                .buildAndRegister();
    }
}
