package tkcy.simpleaddon.api.unification;

import gregtech.api.unification.material.Material;

public final class TKCYSAMaterials {

    public static void init() {
        TKCYSAFirstDegreeMaterials.init();

        TKCYSAMaterialFlagAddition.init();
    }

    public static final Material SodiumCarbonate;
    public static final Material SodiumChromate;
    public static final Material SodiumDichromate;
    public static final Material ChromiumOxide;
    public static final Material SodiumSulfate;
    public static final Material Alumina;
}
