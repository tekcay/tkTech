package tkcy.tktech.loaders.recipe;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.tktech.api.metatileentities.cleanroom.AdvancedCleanroomType;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class RecipesTests {

    public static void register() {
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.DamascusSteel)
                .fluidInputs(Materials.Epichlorohydrin.getFluid(1000))
                .output(OrePrefix.dust, Materials.Cadmium)
                .EUt(30)
                .duration(500)
                .cleanroom(AdvancedCleanroomType.ARGON_CLEANROOM)
                .buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Cadmium)
                .fluidInputs(Materials.Epichlorohydrin.getFluid(1000))
                .output(OrePrefix.dust, Materials.Cadmium)
                .EUt(30)
                .duration(500)
                .cleanroom(AdvancedCleanroomType.NITROGEN_CLEANROOM)
                .buildAndRegister();

        TkTechRecipeMaps.CHEMICAL_BENCH_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Cadmium)
                .fluidInputs(Materials.Epichlorohydrin.getFluid(100))
                .fluidOutputs(Materials.Acetone.getFluid(100))
                .EUt(20)
                .duration(400, 0.5f)
                .buildAndRegister();

//        TkTechRecipeMaps.CHEMICAL_BENCH_RECIPES.recipeBuilder()
//                .inputFluidChemical(Materials.Benzene, 100)
//                .inputFluidChemical(Materials.Toluene, 100)
//                .outputFluidChemical(Materials.Epichlorohydrin, 100)
//                // .fluidInputs(Materials.Benzene.getFluid(100))
//                // .fluidInputs(Materials.Toluene.getFluid(100))
//                // .fluidOutputs(Materials.Ethanol.getFluid(100))
//                .EUt(20)
//                .duration(400, 0.5f)
//                .buildAndRegister();

    }
}
