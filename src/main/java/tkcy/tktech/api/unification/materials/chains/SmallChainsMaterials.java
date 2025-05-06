package tkcy.tktech.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.PigIron;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.TungstenOxide;
import static tkcy.tktech.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SmallChainsMaterials {

    public static int init(int id) {
        PigIron = new Material.Builder(id++, tkcysa("pig_iron"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1800).attributes())
                .flags(MaterialFlags.NO_UNIFICATION)
                .components(Iron, 1, Carbon, 1)
                .colorAverage()
                .build();

        TungstenOxide = new Material.Builder(id++, tkcysa("tungsten_oxide"))
                .dust()
                .components(Tungsten, 1, Oxygen, 3)
                .colorAverage()
                .build();
        return id;
    }
}
