package tkcy.tktech.api.unification.materials.chains;

import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;
import static tkcy.tktech.api.utils.TkTechUtil.tktech;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChromiteChainMaterials {

    public static int init(int id) {
        SodiumChromate = new Material.Builder(id++, tktech("sodium_chromate"))
                .dust()
                .colorAverage()
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(id++, tktech("sodium_dichromate"))
                .dust()
                .colorAverage()
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(id++, tktech("chromium_oxide"))
                .dust()
                .colorAverage()
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);

        return id;
    }
}
