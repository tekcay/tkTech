package tkcy.simpleaddon.loaders.recipe.parts;

import static tkcy.simpleaddon.common.TKCYSAConfigHolder.harderStuff;

public class OreProcessingsHandler {

    public static void init() {
        Electrodes.init();
        if (harderStuff.enableHarderCoils) FoilHandler.init();
        if (harderStuff.enableHarderCable) WireHandler.init();
        if (harderStuff.enableHarderFineWires) FineWireHandler.init();
    }
}
