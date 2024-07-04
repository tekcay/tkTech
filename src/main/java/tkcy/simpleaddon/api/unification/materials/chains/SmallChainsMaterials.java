package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.PigIron;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.TungstenOxide;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SmallChainsMaterials {

    public static int init(int id) {
        PigIron = new Material.Builder(id++, gregtechId("pig_iron"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1800).attributes())
                .flags(MaterialFlags.NO_UNIFICATION)
                .components(Iron, 1, Carbon, 1)
                .colorAverage()
                .build();

        TungstenOxide = new Material.Builder(id++, gregtechId("tungsten_oxide"))
                .dust()
                .components(Tungsten, 1, Oxygen, 3)
                .colorAverage()
                .build();
        return id;
    }
}
