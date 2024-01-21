package tkcy.simpleaddon.loaders.recipe.parts;

import tkcy.simpleaddon.common.TKCYSAConfigHolder;

public class PartsHandler {

    public static void init() {
        Electrodes.init();
        if (TKCYSAConfigHolder.harderStuff.enableHarderCoils) FoilHandler.init();
    }
}
