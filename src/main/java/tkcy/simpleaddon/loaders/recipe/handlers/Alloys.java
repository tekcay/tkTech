package tkcy.simpleaddon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps.ADVANCED_ELECTROLYSIS;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.GalvanizedSteel;

import gregtech.api.GTValues;

public class Alloys {

    public static void init() {
        galvanizeSteel();
    }

    private static void galvanizeSteel() {
        ADVANCED_ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Zinc.getFluid(GTValues.L))
                .notConsumable(stickLong, Zinc)
                .input(plate, Steel)
                .output(plate, GalvanizedSteel)
                .EUt(30)
                .buildAndRegister();

        ADVANCED_ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Zinc.getFluid(GTValues.L))
                .notConsumable(stickLong, Zinc)
                .input(stickLong, Steel)
                .output(stickLong, GalvanizedSteel)
                .EUt(30)
                .buildAndRegister();

        ADVANCED_ELECTROLYSIS.recipeBuilder().duration(200)
                .fluidInputs(Zinc.getFluid(GTValues.L / 2))
                .notConsumable(stickLong, Zinc)
                .input(stick, Steel)
                .output(stick, GalvanizedSteel)
                .EUt(30)
                .buildAndRegister();
    }
}
