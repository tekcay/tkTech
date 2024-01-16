package tkcy.simpleaddon.loaders.recipe.handlers;

import static gregtech.api.recipes.RecipeMaps.CHEMICAL_RECIPES;
import static gregtech.api.recipes.RecipeMaps.MIXER_RECIPES;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.PotassiumHydroxide;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.PotassiumMetaBisulfite;

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
    }
}
