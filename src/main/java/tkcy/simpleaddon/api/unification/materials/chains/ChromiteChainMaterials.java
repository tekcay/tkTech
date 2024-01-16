package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.ChromiumOxide;

import gregtech.api.unification.material.Material;

public class ChromiteChainMaterials {

    public static int init(int id) {
        SodiumChromate = new Material.Builder(id++, gregtechId("sodium_chromate"))
                .dust()
                .colorAverage()
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(id++, gregtechId("sodium_dichromate"))
                .dust()
                .colorAverage()
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(id++, gregtechId("chromium_oxide"))
                .dust()
                .colorAverage()
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);

        return id;
    }
}
