package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.api.unification.material.Materials.Carbon;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;

public class Electrodes {

    /**
     * To use for materials which unification is disabled
     * 
     * @param material
     */
    public static void addElectrodesProcesses(Material material) {
        OrePrefix orePrefix = OrePrefix.stickLong;
        IngotProperty ingotProperty = new IngotProperty();

        electrode(orePrefix, material, ingotProperty);
        anode(orePrefix, material, ingotProperty);
        cathode(orePrefix, material, ingotProperty);
    }

    public static void init() {
        TKCYSAOrePrefix.cathode.addProcessingHandler(PropertyKey.INGOT, Electrodes::cathode);
        TKCYSAOrePrefix.anode.addProcessingHandler(PropertyKey.INGOT, Electrodes::anode);
        TKCYSAOrePrefix.electrode.addProcessingHandler(PropertyKey.INGOT, Electrodes::electrode);

        addElectrodesProcesses(Carbon);
    }

    private static void electrode(OrePrefix orePrefix, Material material, IngotProperty property) {
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(OrePrefix.stickLong, material)
                .output(TKCYSAOrePrefix.electrode, material)
                .duration((int) (material.getMass() * 10))
                .EUt(30)
                .buildAndRegister();
    }

    private static void anode(OrePrefix orePrefix, Material material, IngotProperty property) {
        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TKCYSAOrePrefix.cathode, material)
                .output(TKCYSAOrePrefix.anode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();
    }

    private static void cathode(OrePrefix orePrefix, Material material, IngotProperty property) {
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
