package tkcy.simpleaddon.loaders.recipe.parts;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.modules.ElectrodeModule;

public class Electrodes {

    public static void init() {
        ElectrodeModule.electrodeMaterials.stream()
                .peek(Electrodes::electrode)
                .peek(Electrodes::cathode)
                .forEach(Electrodes::anode);
    }

    private static void electrode(Material material) {
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(OrePrefix.stickLong, material)
                .output(TKCYSAOrePrefix.electrode, material)
                .duration((int) (material.getMass() * 10))
                .EUt(30)
                .buildAndRegister();
    }

    private static void anode(Material material) {
        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TKCYSAOrePrefix.cathode, material)
                .output(TKCYSAOrePrefix.anode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();
    }

    private static void cathode(Material material) {
        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TKCYSAOrePrefix.anode, material)
                .output(TKCYSAOrePrefix.cathode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();

        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TKCYSAOrePrefix.electrode, material)
                .output(TKCYSAOrePrefix.cathode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();
    }
}
