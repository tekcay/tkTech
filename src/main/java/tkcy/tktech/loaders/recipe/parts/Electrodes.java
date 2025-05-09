package tkcy.tktech.loaders.recipe.parts;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;
import tkcy.tktech.modules.ElectrodeModule;

@UtilityClass
public class Electrodes {

    public static void init() {
        ElectrodeModule.electrodeMaterials.stream()
                .map(Electrodes::electrode)
                .map(Electrodes::cathode)
                .forEach(Electrodes::anode);
    }

    private static Material electrode(Material material) {
        RecipeMaps.LASER_ENGRAVER_RECIPES.recipeBuilder()
                .input(OrePrefix.stickLong, material)
                .notConsumable(OrePrefix.lens, getLensMaterial(material))
                .output(TkTechOrePrefix.electrode, material)
                .duration((int) (material.getMass() * 10))
                .EUt(30)
                .buildAndRegister();
        return material;
    }

    private static Material getLensMaterial(Material electordeMaterial) {
        MaterialIconSet iconSet = electordeMaterial.getMaterialIconSet();

        if (iconSet == MaterialIconSet.BRIGHT) {
            return Materials.Sapphire;
        } else if (iconSet == MaterialIconSet.SHINY) {
            return Materials.Ruby;
        } else if (iconSet == MaterialIconSet.DULL) {
            return Materials.Emerald;
        } else if (iconSet == MaterialIconSet.METALLIC) {
            return Materials.Diamond;
        } else if (iconSet == MaterialIconSet.MAGNETIC) {
            return Materials.Glass;
        }
        return Materials.Glass;
    }

    private static void anode(Material material) {
        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TkTechOrePrefix.cathode, material)
                .output(TkTechOrePrefix.anode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();
    }

    private static Material cathode(Material material) {
        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TkTechOrePrefix.anode, material)
                .output(TkTechOrePrefix.cathode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();

        RecipeMaps.POLARIZER_RECIPES.recipeBuilder()
                .input(TkTechOrePrefix.electrode, material)
                .output(TkTechOrePrefix.cathode, material)
                .duration((int) (material.getMass() * 3))
                .EUt(30)
                .buildAndRegister();

        return material;
    }
}
