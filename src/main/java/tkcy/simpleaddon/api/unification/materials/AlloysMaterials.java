package tkcy.simpleaddon.api.unification.materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.modules.alloyingmodule.Alloying;

@Alloying
@UtilityClass
public class AlloysMaterials {

    public static int init(int id) {
        GalvanizedSteel = new Material.Builder(id++, gregtechId("galvanized_steel"))
                .ingot()
                .fluidPipeProperties(2000, 100, true, true, true, false)
                .components(Iron, 9, Zinc, 1)
                .flags(GENERATE_ALL_NO_UNIF)
                .color(0xf5f8fa).iconSet(METALLIC)
                .build();

        Monel = new Material.Builder(id++, gregtechId("monel"))
                .ingot()
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Nickel, 7, Copper, 3)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

        BT6 = new Material.Builder(id++, gregtechId("bt_6"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Cobalt, 8, Chrome, 3, Steel, 3, Vanadium, 1, Tungsten, 4)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Mangalloy = new Material.Builder(id++, gregtechId("mangalloy"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Steel, 18, Manganese, 3)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel600 = new Material.Builder(id++, gregtechId("inconel_600"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Chrome, 1, Steel, 4, Nickel, 5)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel690 = new Material.Builder(id++, gregtechId("inconel_690"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Chrome, 4, Steel, 1, Nickel, 8)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Talonite = new Material.Builder(id++, gregtechId("talonite"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Carbon, 4, Cobalt, 16, Chrome, 28, Tungsten, 1)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        TC4 = new Material.Builder(id++, gregtechId("tc_4"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Aluminium, 3, Titanium, 24, Vanadium, 1)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        return id;
    }
}
