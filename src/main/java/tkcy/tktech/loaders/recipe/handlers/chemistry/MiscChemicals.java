package tkcy.tktech.loaders.recipe.handlers.chemistry;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;
import tkcy.tktech.api.utils.TimeUtil;

@UtilityClass
public class MiscChemicals {

    public static void init() {
        // PotassiumHydroxide
        CHEMICAL_RECIPES.recipeBuilder().duration(100)
                .EUt(100)
                .input(dust, RockSalt, 2)
                .fluidInputs(DistilledWater.getFluid(2000))
                .output(dust, PotassiumHydroxide, 2)
                .fluidOutputs(Hydrogen.getFluid(1000))
                .fluidOutputs(Chlorine.getFluid(1000))
                .buildAndRegister();

        // MetaBisulfite
        MIXER_RECIPES.recipeBuilder().duration(100)
                .EUt(100)
                .fluidInputs(SulfurDioxide.getFluid(1000))
                .input(dust, PotassiumHydroxide)
                .output(dust, PotassiumMetaBisulfite)
                .buildAndRegister();

        // Sodium nitrite production
        // 2 NaOH + NO2 + NO -> 2 NaNO2 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(TimeUtil.seconds(60))
                .EUt(VA[LV])
                .notConsumable(DistilledWater.getFluid(4000))
                .input(dust, SodiumHydroxide, 2)
                .fluidInputs(NitrogenDioxide.getFluid(1000))
                .fluidInputs(NitricOxide.getFluid(1000))
                .fluidOutputs(SodiumNitriteSolution.getFluid(2000))
                .buildAndRegister();

        TkTechRecipeMaps.DRYING.recipeBuilder()
                .duration(TimeUtil.seconds(40))
                .EUt(VA[MV])
                .fluidInputs(SodiumNitriteSolution.getFluid(1000))
                .fluidOutputs(Steam.getFluid(200))
                .output(dust, SodiumNitrite)
                .buildAndRegister();

        // 2 NaOH + N2O3 -> 2 NaNO2 + H2O
        CHEMICAL_RECIPES.recipeBuilder()
                .duration(TimeUtil.seconds(60))
                .EUt(VA[LV])
                .notConsumable(DistilledWater.getFluid(4000))
                .input(dust, SodiumHydroxide, 2)
                .fluidInputs(LiquidDinitrogenTrioxide.getFluid(1000))
                .fluidOutputs(SodiumNitriteSolution.getFluid(2000))
                .buildAndRegister();

        // NO + NO2 -> N2O3
        VACUUM_RECIPES.recipeBuilder()
                .duration(TimeUtil.seconds(60))
                .EUt(VA[MV])
                .fluidInputs(NitrogenDioxide.getFluid(1000))
                .fluidInputs(NitricOxide.getFluid(1000))
                .fluidOutputs(LiquidDinitrogenTrioxide.getFluid(1000))
                .buildAndRegister();

        // 2 NaHSO4 -> Na2S2O8 + H2
        TkTechRecipeMaps.ADVANCED_ELECTROLYSIS.recipeBuilder()
                .EUt(600)
                .duration(20)
                .input(OrePrefix.dust, Materials.SodiumBisulfate)
                .notConsumable(TkTechOrePrefix.anode, Platinum)
                .notConsumable(TkTechOrePrefix.cathode, Platinum)
                .notConsumable(Materials.DistilledWater.getFluid(5000))
                .fluidInputs(Materials.DistilledWater.getFluid(1000))
                .fluidOutputs(Materials.Hydrogen.getFluid(1000))
                .fluidOutputs(Materials.SodiumPersulfate.getFluid(1000))
                .buildAndRegister();
    }
}
