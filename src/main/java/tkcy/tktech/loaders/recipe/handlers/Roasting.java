package tkcy.tktech.loaders.recipe.handlers;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.helpers.RecipeRemovalHelper;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.TimeUtil;

@UtilityClass
public class Roasting {

    public static void init() {
        RecipeRemovalHelper.removeRecipeByInput(RecipeMaps.BLAST_RECIPES, dust, Galena);

        roast(Chalcocite, 1, CupricOxide, 2, 2000);
        roast(Realgar, 1, Arsenopyrite, 2, 2000);
        roast(Tetrahedrite, 1, RoastedTetrahedrite, 1, 2000);
        roast(Cobaltite, 1, RoastedCobaltite, 1, 1000);
        roast(Sphalerite, 1, Zincite, 1, 1000);
        roast(Galena, 1, RoastedGalena, 1, 1000);
        roast(Kesterite, 1, RoastedKesterite, 1, 4000);
        roast(Stannite, 1, RoastedStannite, 1, 4000);
        roast(Arsenopyrite, 1, RoastedArsenopyrite, 1, 4000);
        roast(Bornite, 1, RoastedBornite, 1, 4000);

        chalcopyrite();
        cinnabar();
    }

    private static void roast(Material material1, int amount1, Material material2, int amount2,
                              int sulfurDioxideAmount) {
        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Air.getFluid(sulfurDioxideAmount * 2))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 50)
                .buildAndRegister();

        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Oxygen.getFluid(sulfurDioxideAmount))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 30)
                .buildAndRegister();
    }

    private static void chalcopyrite() {
        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, Chalcopyrite)
                .input(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Air.getFluid(2000 * 2))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .duration(20 * 100)
                .buildAndRegister();

        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, Chalcopyrite)
                .output(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Oxygen.getFluid(4000))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .duration(20 * 50)
                .buildAndRegister();
    }

    private static void cinnabar() {
        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .duration(TimeUtil.seconds(10))
                .fluidInputs(Air.getFluid(4000))
                .input(dust, Cinnabar)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .fluidOutputs(Mercury.getFluid(L))
                .buildAndRegister();

        TkTechRecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .duration(TimeUtil.seconds(8))
                .fluidInputs(Oxygen.getFluid(1000))
                .input(dust, Cinnabar)
                .fluidOutputs(SulfurDioxide.getFluid(1000))
                .fluidOutputs(Mercury.getFluid(L))
                .buildAndRegister();
    }
}
