package tkcy.tktech.api.unification.ore;

import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrePrefixAdditions {

    public static void init() {
        TkTechOrePrefix.toolTipSolderingIron.processOreRegistration(Materials.Steel);
    }
}
