package tkcy.simpleaddon.loaders.recipe.parts;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;

public class SpringHandler {

    public static void init() {
        OrePrefix.spring.addProcessingHandler(PropertyKey.INGOT, SpringHandler::processSpring);
    }

    private static void processSpring(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        TKCYSARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass())
                .input(OrePrefix.stickLong, material)
                .output(orePrefix, material)
                .buildAndRegister();
    }
}
