package tkcy.simpleaddon.api.unification.ore;

import gregtech.api.unification.material.Materials;

public class OrePrefixAdditions {

    public static void init() {
        TKCYSAOrePrefix.toolTipSolderingIron.processOreRegistration(Materials.Steel);
    }
}
