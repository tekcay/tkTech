package tkcy.simpleaddon.loaders.recipe;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.metatileentities.cleanroom.AdvancedCleanroomType;

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
    }
}
