package tkcy.simpleaddon.loaders.recipe.alloys;

import static gregtech.api.unification.material.Materials.*;

import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class DustMixing {

    public static void init() {
        TKCYSARecipeMaps.DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Copper, 3)
                .input(OrePrefix.dust, Tin, 1)
                .output(OrePrefix.dust, Bronze, 4)
                .EUt(10)
                .duration((int) Bronze.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Steel, 1)
                .input(OrePrefix.dust, Tin, 1)
                .output(OrePrefix.dust, TinAlloy, 2)
                .EUt(10)
                .duration((int) TinAlloy.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Copper, 1)
                .input(OrePrefix.dust, Redstone, 4)
                .output(OrePrefix.dust, RedAlloy, 5)
                .EUt(10)
                .duration((int) RedAlloy.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Bronze, 2)
                .input(OrePrefix.dust, Lead)
                .output(OrePrefix.dust, Potin, 5)
                .EUt(10)
                .duration((int) Potin.getMass())
                .buildAndRegister();
    }
}
