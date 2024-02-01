package tkcy.simpleaddon.loaders.recipe.parts;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import tkcy.simpleaddon.modules.CablesModule;

import java.util.Map;

public class CableHandler {

    public static void init() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(material -> material.hasProperty(PropertyKey.WIRE))
                .forEach(CableHandler::removeShapedCableAndWireDuplicate);
    }

    private static void removeShapedCableAndWireDuplicate(Material material) {
        for (Map.Entry<OrePrefix, OrePrefix> cableToWire : CablesModule.cableToWireMap.entrySet()) {
            ModHandler.removeRecipeByOutput(OreDictUnifier.get(cableToWire.getKey(), material, 1));
            ModHandler.removeRecipeByOutput(OreDictUnifier.get(cableToWire.getValue(), material, 1));
            ModHandler.removeRecipeByOutput(OreDictUnifier.get(cableToWire.getValue(), material, 2));
        }
    }
}
