package tkcy.tktech.loaders.recipe.parts;

import static gregtech.api.GTValues.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.modules.CablesModule;

@UtilityClass
public class WireHandler {

    public static void init() {
        CablesModule.cableToWireMap.values().stream()
                .filter(orePrefix -> orePrefix != OrePrefix.wireGtSingle)
                .forEach(orePrefix -> orePrefix.addProcessingHandler(PropertyKey.WIRE, WireHandler::assembleWires));

        OrePrefix.wireGtSingle.addProcessingHandler(PropertyKey.WIRE, WireHandler::processWireSingle);
    }

    private static void assembleWires(OrePrefix orePrefix, Material material, WireProperties wireProperties) {
        List<OrePrefix> orePrefixes = new ArrayList<>(CablesModule.cableToWireMap.values());

        int multiplier = (int) Math.pow(2, orePrefixes.indexOf(orePrefix));
        if (multiplier == 1) multiplier += 1;

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass() * multiplier)
                .circuitMeta(multiplier)
                .input(OrePrefix.wireGtSingle, material, multiplier)
                .output(orePrefix, material)
                .buildAndRegister();
    }

    private static void processWireSingle(OrePrefix orePrefix, Material material, WireProperties wireProperties) {
        RecipeMaps.WIREMILL_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass())
                .input(OrePrefix.stick, material)
                .output(orePrefix, material)
                .buildAndRegister();
    }
}
