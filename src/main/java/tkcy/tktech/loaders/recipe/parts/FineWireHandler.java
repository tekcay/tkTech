package tkcy.tktech.loaders.recipe.parts;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FineWireHandler {

    public static void init() {
        OrePrefix.wireFine.addProcessingHandler(PropertyKey.INGOT, FineWireHandler::processFineWire);
    }

    private static void processFineWire(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass())
                .input(OrePrefix.foil, material)
                .output(orePrefix, material, 2)
                .buildAndRegister();
    }
}
