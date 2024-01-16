package tkcy.simpleaddon.loaders.recipe.handlers;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import tkcy.simpleaddon.api.recipes.RecipeRemovalHelper;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

public class Roasting {

    public static void init() {
        RecipeRemovalHelper.removeRecipeByInput(RecipeMaps.BLAST_RECIPES, dust, Galena);

        electricBlast(Chalcocite, 1, CupricOxide, 2, 2000);
        electricBlast(Realgar, 1, Arsenopyrite, 2, 2000);
        electricBlast(Tetrahedrite, 1, RoastedTetrahedrite, 1, 2000);
        electricBlast(Cobaltite, 1, RoastedCobaltite, 1, 1000);
        electricBlast(Sphalerite, 1, Zincite, 1, 1000);
        electricBlast(Galena, 1, RoastedGalena, 1, 1000);
        electricBlast(Kesterite, 1, RoastedKesterite, 1, 4000);
        electricBlast(Stannite, 1, RoastedStannite, 1, 4000);
        electricBlast(Arsenopyrite, 1, RoastedArsenopyrite, 1, 4000);
        electricBlast(Bornite, 1, RoastedBornite, 1, 4000);

        primitiveBlast(Chalcopyrite, 1, CupricOxide, 2);
        primitiveBlast(Sphalerite, 1, Zincite, 1);
        primitiveBlast(Galena, 1, RoastedGalena, 1);

        chalcopyrite();
    }

    private static void primitiveBlast(Material material1, int amount1, Material material2, int amount2) {
        TKCYSARecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .duration(20 * 120)
                .buildAndRegister();
    }

    private static void electricBlast(Material material1, int amount1, Material material2, int amount2,
                                      int sulfurDioxideAmount) {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Air.getFluid(sulfurDioxideAmount * 2))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 50)
                .blastFurnaceTemp(1200)
                .EUt(80)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Oxygen.getFluid(sulfurDioxideAmount))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 30)
                .blastFurnaceTemp(1200)
                .EUt(80)
                .buildAndRegister();
    }

    private static void chalcopyrite() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Chalcopyrite)
                .input(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Air.getFluid(2000 * 2))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .blastFurnaceTemp(1200)
                .duration(20 * 100)
                .EUt(80)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Chalcopyrite)
                .output(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Oxygen.getFluid(4000))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .blastFurnaceTemp(1200)
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();
    }
}
