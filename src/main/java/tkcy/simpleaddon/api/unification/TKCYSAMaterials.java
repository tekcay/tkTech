package tkcy.simpleaddon.api.unification;

import gregtech.api.unification.material.Material;

public class TKCYSAMaterials {

    public static void init() {
        TKCYSAFirstDegreeMaterials.init();

        TKCYSAMaterialFlagAddition.init();
    }

    public static Material SodiumCarbonate;
    public static Material SodiumChromate;
    public static Material SodiumDichromate;
    public static Material ChromiumOxide;
    public static Material SodiumSulfate;
    public static Material Alumina;
}
