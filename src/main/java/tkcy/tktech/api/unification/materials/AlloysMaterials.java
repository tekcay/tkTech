package tkcy.tktech.api.unification.materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static tkcy.tktech.api.unification.flags.TKCYSAMaterialFlags.*;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.tktech.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.tktech.modules.alloyingmodule.Alloying;

@Alloying
@UtilityClass
public class AlloysMaterials {

    public static int init(int id) {
        GalvanizedSteel = new Material.Builder(id++, tkcysa("galvanized_steel"))
                .ingot()
                .fluidPipeProperties(2000, 100, true, true, true, false)
                .components(Iron, 9, Zinc, 1)
                .flags(GENERATE_ALL_NO_UNIF)
                .color(0xf5f8fa).iconSet(METALLIC)
                .build();

        Monel = new Material.Builder(id++, tkcysa("monel"))
                .ingot()
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Nickel, 7, Copper, 3)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

        BT6 = new Material.Builder(id++, tkcysa("bt_6"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Cobalt, 8, Chrome, 3, Steel, 3, Vanadium, 1, Tungsten, 4)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Mangalloy = new Material.Builder(id++, tkcysa("mangalloy"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Steel, 18, Manganese, 3)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel600 = new Material.Builder(id++, tkcysa("inconel_600"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Chrome, 1, Steel, 4, Nickel, 5)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Inconel690 = new Material.Builder(id++, tkcysa("inconel_690"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Chrome, 4, Steel, 1, Nickel, 8)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        Talonite = new Material.Builder(id++, tkcysa("talonite"))
                .ingot(2)
                .fluid()
                .flags(GENERATE_ALL)
                .flags(ALLOY)
                .components(Carbon, 4, Cobalt, 16, Chrome, 28, Tungsten, 1)
                .colorAverage()
                .iconSet(METALLIC)
                .build();

        TC4 = new Material.Builder(id++, tkcysa("tc_4"))
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
