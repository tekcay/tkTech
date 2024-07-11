package tkcy.simpleaddon.loaders.recipe.chains.metals;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.TungstenOxide;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TungstenChain {

    public static void init() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, TungsticAcid, 7)
                .output(OrePrefix.dust, TungstenOxide, 4)
                .fluidOutputs(Steam.getFluid(3000))
                .EUt(1000)
                .duration(20 * 100)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, TungstenOxide, 2)
                .input(OrePrefix.dust, Carbon, 3)
                .output(OrePrefix.dust, Tungsten, 2)
                .fluidOutputs(CarbonDioxide.getFluid(3000))
                .EUt(900)
                .duration(20 * 100)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, TungstenOxide, 2)
                .fluidInputs(Hydrogen.getFluid(6000))
                .output(OrePrefix.dust, Tungsten)
                .fluidOutputs(Steam.getFluid(6000))
                .EUt(950)
                .duration(20 * 100)
                .buildAndRegister();
    }
}
