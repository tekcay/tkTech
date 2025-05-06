package tkcy.tktech.loaders.recipe.chains.metals;

import static tkcy.tktech.api.TkTechValues.SECOND;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.unification.materials.TkTechMaterials;
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;

@UtilityClass
public class ManganeseChain {

    public static void init() {
        // Spessartine

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Pyrolusite)
                .input(OrePrefix.dust, Materials.Carbon)
                .fluidInputs(Materials.Air.getFluid(4000))
                .fluidOutputs(Materials.CarbonDioxide.getFluid(1000))
                .fluidOutputs(Materials.Manganese.getFluid(GTValues.L))
                .blastFurnaceTemp(800)
                .EUt(100)
                .duration(20 * SECOND)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Pyrolusite)
                .input(OrePrefix.dust, Materials.Carbon)
                .fluidInputs(Materials.Oxygen.getFluid(1000))
                .fluidOutputs(Materials.CarbonDioxide.getFluid(1000))
                .fluidOutputs(Materials.Manganese.getFluid(GTValues.L))
                .blastFurnaceTemp(800)
                .EUt(100)
                .duration(20 * SECOND)
                .buildAndRegister();

        TkTechRecipeMaps.ADVANCED_ELECTROLYSIS.recipeBuilder()
                .input(OrePrefix.dust, Materials.Pyrolusite)
                .notConsumable(TkTechOrePrefix.cathode, Materials.Manganese)
                .notConsumable(TkTechOrePrefix.anode, Materials.Carbon)
                .notConsumable(Materials.DistilledWater.getFluid(2000))
                .fluidOutputs(Materials.Oxygen.getFluid(1000))
                .output(OrePrefix.dust, Materials.Manganese)
                .duration(SECOND * 60)
                .EUt(100)
                .buildAndRegister();

        // Materials.Spessartine

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .fluidInputs(Materials.SulfuricAcid.getFluid(1000))
                .input(OrePrefix.dust, TkTechMaterials.OxalicAcid)
                .input(OrePrefix.dust, Materials.Spessartine)
                .fluidOutputs(TkTechMaterials.TreatedSpessartine.getFluid(1000))
                .EUt(30)
                .duration(SECOND * 20)
                .buildAndRegister();

        RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                .fluidInputs(TkTechMaterials.TreatedSpessartine.getFluid(2000))
                .output(OrePrefix.dust, Materials.Pyrolusite, 3)
                .EUt(30)
                .duration(SECOND * 20)
                .buildAndRegister();
    }
}
