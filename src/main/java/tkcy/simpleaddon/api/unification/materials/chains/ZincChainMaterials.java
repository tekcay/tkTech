package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.ZincLeachingResidue;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.ZincSulfate;

import gregtech.api.unification.material.Material;

public class ZincChainMaterials {

    public static int init(int id) {
        ZincSulfate = new Material.Builder(id++, gregtechId("zinc_sulfate"))
                .dust()
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        ZincLeachingResidue = new Material.Builder(id++, gregtechId("zinc_leaching_residue"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        return id;
    }
}
