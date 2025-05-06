package tkcy.tktech.loaders.recipe.parts;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

@UtilityClass
public class SpringHandler {

    public static void init() {
        OrePrefix.spring.addProcessingHandler(PropertyKey.INGOT, SpringHandler::processSpring);
    }

    private static void processSpring(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        TkTechRecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass())
                .input(OrePrefix.stickLong, material)
                .output(orePrefix, material)
                .buildAndRegister();
    }
}
