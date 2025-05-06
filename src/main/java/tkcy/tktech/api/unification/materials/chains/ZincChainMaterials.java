package tkcy.tktech.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.ZincLeachingResidue;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.ZincSulfate;
import static tkcy.tktech.api.utils.TkTechUtil.tktech;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ZincChainMaterials {

    public static int init(int id) {
        ZincSulfate = new Material.Builder(id++, tktech("zinc_sulfate"))
                .dust()
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        ZincLeachingResidue = new Material.Builder(id++, tktech("zinc_leaching_residue"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        return id;
    }
}
