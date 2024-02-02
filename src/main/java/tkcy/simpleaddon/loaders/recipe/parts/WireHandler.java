package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.api.GTValues.ULV;
import static gregtech.api.GTValues.VA;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.WireProperties;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.modules.CablesModule;

public class WireHandler {

    public static void init() {
        CablesModule.cableToWireMap.values().stream()
                .filter(orePrefix -> orePrefix != OrePrefix.wireGtSingle)
                .forEach(orePrefix -> orePrefix.addProcessingHandler(PropertyKey.WIRE, WireHandler::assembleWires));
    }

    private static void assembleWires(OrePrefix orePrefix, Material material, WireProperties wireProperties) {
        List<OrePrefix> orePrefixes = new ArrayList<>(CablesModule.cableToWireMap.values());
        int multiplier = (int) Math.pow(2, orePrefixes.indexOf(orePrefix));
        if (multiplier == 1) multiplier += 1;

        TKCYSALog.logger.info(String.format("orePrefix : %s, multiplier : %d", orePrefix, multiplier));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
                .EUt(VA[ULV])
                .duration((int) material.getMass())
                .circuitMeta(multiplier)
                .input(OrePrefix.wireGtSingle, material, multiplier)
                .output(orePrefix, material)
                .buildAndRegister();
    }
}
