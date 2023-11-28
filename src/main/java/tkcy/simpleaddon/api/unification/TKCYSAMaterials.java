package tkcy.simpleaddon.api.unification;

import gregtech.api.unification.material.Material;

public class TKCYSAMaterials {

    public static void init() {
        // 4000 - 4200
        TKCYSAFirstDegreeMaterials.init();

        // 4201
        TKCYSASecondDegreeMaterials.init();

        TKCYSAMaterialFlagAddition.init();
    }

    public static Material SodiumChromate;
    public static Material SodiumDichromate;
    public static Material ChromiumOxide;
    public static Material SodiumSulfate;
    public static Material Alumina;
}
