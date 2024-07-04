package tkcy.simpleaddon.loaders.recipe.parts;

import static tkcy.simpleaddon.common.TKCYSAConfigHolder.harderStuff;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.loaders.recipe.ToolsRecipes;

@UtilityClass
public class OreProcessingsHandler {

    public static void init() {
        Electrodes.init();
        ToolsRecipes.init();
        CurvedPlateHandler.init();
        SpringHandler.init();
        if (harderStuff.enablePrimitiveCrafting) PlatesHandler.init();
        if (harderStuff.enableHarderCoils) FoilHandler.init();
        if (harderStuff.enableHarderCable) WireHandler.init();
        if (harderStuff.enableHarderFineWires) FineWireHandler.init();
        if (harderStuff.enableHarderRotors) RotorHandler.init();
    }
}
