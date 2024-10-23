package tkcy.simpleaddon.loaders.recipe;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

public class CapabilityContainersTesting {

    public static void test() {
        TKCYSARecipeMaps.HEAT_PRODUCING_RECIPES.recipeBuilder()
                .outputHeat(30)
                .input(OrePrefix.dust, Charcoal)
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .duration(60)
                .EUt(1)
                .buildAndRegister();

        TKCYSARecipeMaps.HEATING_CONSUMING_RECIPES.recipeBuilder()
                .inputHeat(30)
                .input(OrePrefix.dust, Tin)
                .fluidOutputs(Tin.getFluid(500))
                .duration(60)
                .EUt(1)
                .buildAndRegister();

        TKCYSARecipeMaps.HEATING_CONSUMING_RECIPES.recipeBuilder()
                .inputHeat(30)
                .temperature(30)
                .input(OrePrefix.dust, Iron)
                .fluidOutputs(Iron.getFluid(500))
                .duration(60)
                .EUt(1)
                .buildAndRegister();
    }
}
