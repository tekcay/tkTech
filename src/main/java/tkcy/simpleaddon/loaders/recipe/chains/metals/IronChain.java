package tkcy.simpleaddon.loaders.recipe.chains.metals;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.PigIron;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

@UtilityClass
public class IronChain {

    public static void init() {
        primitive(BandedIron, dust, 5, 1, 2);
        primitive(BrownLimonite, dust, 5, 1, 1);
        primitive(YellowLimonite, dust, 5, 1, 1);
        primitive(Magnetite, dust, 7, 3, 3);
        primitive(BandedIron, crushed, 5, 1, 2);
        primitive(BrownLimonite, crushed, 5, 1, 1);
        primitive(YellowLimonite, crushed, 5, 1, 1);
        primitive(Magnetite, crushed, 7, 3, 3);

        blast();
        pigIronToIron();
    }

    private static void primitive(Material ore, OrePrefix prefix, int count, int carbonSourceCount, int pigIronCount) {
        TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST.recipeBuilder()
                .input(prefix, ore, count)
                .input(prefix, Coal, carbonSourceCount)
                .fluidOutputs(PigIron.getFluid(GTValues.L * pigIronCount))
                .duration(20 * 150)
                .buildAndRegister();

        TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST.recipeBuilder()
                .input(OrePrefix.ore, ore, count)
                .input(OrePrefix.gem, Coke, carbonSourceCount)
                .fluidOutputs(PigIron.getFluid(GTValues.L * pigIronCount))
                .duration(20 * 125)
                .buildAndRegister();
    }

    private static void pigIronToIron() {
        TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST.recipeBuilder()
                .fluidInputs(PigIron.getFluid(GTValues.L))
                .fluidOutputs(Iron.getFluid(GTValues.L))
                .duration(20 * 150)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(ingot, PigIron)
                .output(OrePrefix.ingot, Iron)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();
    }

    private static void blast() {
        // Fe2O3 + 7 C -> 4 FeC + 3 CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, BandedIron)
                .input(dust, Carbon, 7)
                .output(ingot, PigIron, 4)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // Fe2O3 + 7 CO -> 2 FeC + 5 CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, BandedIron)
                .fluidInputs(CarbonMonoxide.getFluid(7000))
                .output(ingot, PigIron, 2)
                .fluidOutputs(CarbonDioxide.getFluid(5000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // 3 Fe2O3 + H2 -> 2 Fe3O4 + H2O
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, BandedIron, 3)
                .fluidInputs(Hydrogen.getFluid(2000))
                .output(dust, Magnetite, 2)
                .fluidOutputs(Steam.getFluid(1000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // Fe3O4 + 5 C -> 3 FeC + 2 CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Magnetite)
                .input(dust, Carbon, 5)
                .output(ingot, PigIron, 3)
                .fluidOutputs(CarbonDioxide.getFluid(2000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // Fe3O4 + 10 CO -> 3 FeC + 7 CO2
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Magnetite)
                .fluidInputs(CarbonMonoxide.getFluid(10000))
                .output(ingot, PigIron, 3)
                .fluidOutputs(CarbonDioxide.getFluid(7000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        // Fe3O4 + 8 H2 -> 3 Fe + 4 H2O
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Magnetite)
                .fluidInputs(Hydrogen.getFluid(8000))
                .output(ingot, Iron, 3)
                .fluidOutputs(Steam.getFluid(4000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();
    }
}
