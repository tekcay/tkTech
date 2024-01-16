package tkcy.simpleaddon.api.unification.materials;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.METALLIC;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.GENERATE_ALL_NO_UNIF;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.GalvanizedSteel;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.Monel;

import gregtech.api.unification.material.Material;

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
                .flags(EXT2_METAL)
                .components(Nickel, 7, Copper, 3)
                .flags(DISABLE_DECOMPOSITION)
                .color(0xc1b8a8).iconSet(METALLIC)
                .build();

        return id;
    }
}
