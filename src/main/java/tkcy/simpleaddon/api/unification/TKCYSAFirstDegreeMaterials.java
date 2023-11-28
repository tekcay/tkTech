package tkcy.simpleaddon.api.unification;

import gregtech.api.unification.material.Material;

import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

public final class TKCYSAFirstDegreeMaterials {

    private TKCYSAFirstDegreeMaterials() {}

    public static void init() {

        //Chromite Chain
        SodiumChromate = new Material.Builder(3001, gregtechId( "sodium_chromate"))
                .dust()
                .color(0xe6d62c)
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(3002, gregtechId( "sodium_dichromate"))
                .dust()
                .color(0xdb822e)
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(3003, gregtechId( "chromium_oxide"))
                .dust()
                .color(0x69c765)
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);

        SodiumCarbonate = new Material.Builder(3004, gregtechId( "sodium_carbonate"))
                .dust()
                .color(0xdeddd1)
                .build();
        SodiumCarbonate.setFormula("Na2CO3", true);


    }
}
