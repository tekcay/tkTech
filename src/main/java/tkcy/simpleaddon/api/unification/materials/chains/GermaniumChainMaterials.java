package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.fluids.attribute.FluidAttributes.ACID;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GermaniumChainMaterials {

    public static int init(int id) {
        Argyrodite = new Material.Builder(id++, tkcysa("argyrodite"))
                .ore()
                .dust()
                .fluid()
                .components(Silver, 8, Germanium, 1, Sulfur, 6)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumTetrachloride = new Material.Builder(id++, tkcysa("germanium_tetrachloride"))
                .liquid(new FluidBuilder().attributes(ACID))
                .components(Germanium, 1, Chlorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        ZincLeachingSolution = new Material.Builder(id++, tkcysa("zinc_leaching_solution"))
                .liquid(new FluidBuilder().attributes(ACID))
                .components(SulfuricAcid, 1, Water, 1, Germanium, 1, Iron, 1)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumSulfide = new Material.Builder(id++, tkcysa("germanium_sulfide"))
                .dust()
                .components(Germanium, 1, Sulfur, 2)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumOxide = new Material.Builder(id++, tkcysa("germanium_oxide"))
                .dust()
                .components(Germanium, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        return id++;
    }
}
