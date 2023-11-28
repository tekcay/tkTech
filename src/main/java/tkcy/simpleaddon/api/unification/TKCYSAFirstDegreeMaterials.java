package tkcy.simpleaddon.api.unification;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

import gregtech.api.unification.material.Material;

public final class TKCYSAFirstDegreeMaterials {

    private TKCYSAFirstDegreeMaterials() {}

    public static void init() {
        // Chromite Chain
        SodiumChromate = new Material.Builder(4001, gregtechId("sodium_chromate"))
                .dust()
                .color(0xe6d62c)
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(4002, gregtechId("sodium_dichromate"))
                .dust()
                .color(0xdb822e)
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(4003, gregtechId("chromium_oxide"))
                .dust()
                .color(0x69c765)
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);
    }
}
