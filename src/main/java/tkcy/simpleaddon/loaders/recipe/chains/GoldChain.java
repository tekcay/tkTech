package tkcy.simpleaddon.loaders.recipe.chains;

import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;

public class GoldChain {

    public static void init() {
        // STEP 1
        // 3 Cu + Au? -> Cu3Au?
        BLAST_RECIPES.recipeBuilder().duration(80)
                .EUt(120)
                .input(dust, Copper, 3)
                .input(dust, PreciousMetal)
                .fluidOutputs(GoldAlloy.getFluid(1000))
                .buildAndRegister();

        // STEP 2
        // Cu3Au? + HNO3 -> Cu3Au?(OH) + NO2
        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .input(dust, GoldAlloy, 4)
                .fluidInputs(NitricAcid.getFluid(1000))
                .fluidOutputs(GoldLeach.getFluid(1000))
                .fluidOutputs(NitrogenDioxide.getFluid(1000))
                .buildAndRegister();

        // STEP 3
        // Cu3Au?(OH) + HCl -> HAuCl(OH) + Cu3?

        CHEMICAL_RECIPES.recipeBuilder().duration(80)
                .EUt(30)
                .fluidInputs(GoldLeach.getFluid(4000))
                .fluidInputs(HydrochloricAcid.getFluid(1000))
                .fluidOutputs(ChloroauricAcid.getFluid(1000))
                .output(dust, CopperLeach, 4)
                .buildAndRegister();

        // STEP 4
        // HAuCl(OH) -> Au + H2O + Cl

        CHEMICAL_RECIPES.recipeBuilder().duration(100)
                .EUt(100)
                .notConsumable(stick, Silver)
                .fluidInputs(ChloroauricAcid.getFluid(1000))
                .fluidInputs(DistilledWater.getFluid(1000))
                .input(dustTiny, PotassiumMetaBisulfite)
                .fluidOutputs(Gold.getFluid(GTValues.L))
                .fluidOutputs(DilutedHydrochloricAcid.getFluid(1000))
                .buildAndRegister();

        // Cu3? -> 3Cu + Fe + Ni + Ag + Pb
        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder().duration(100)
                .EUt(100)
                .input(dust, CopperLeach)
                .output(dust, Copper)
                .output(dustTiny, Lead)
                .output(dustTiny, Iron)
                .output(dustTiny, Nickel)
                .output(dustTiny, Silver)
                .buildAndRegister();
    }
}
