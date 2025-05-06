package tkcy.tktech.api.unification.materials.other;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.tktech.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Chemicals {

    public static int register(int startId) {
        SodiumNitrite = new Material.Builder(startId++, tkcysa("sodium_nitrite"))
                .dust()
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 2)
                .colorAverage()
                .build();

        SodiumNitriteSolution = new Material.Builder(startId++, tkcysa("sodium_nitrite_solution"))
                .fluid()
                .components(SodiumNitrite, 1, Water, 1)
                .colorAverage()
                .build();

        LiquidDinitrogenTrioxide = new Material.Builder(startId++, tkcysa("liquid_dinitrogen_trioxide"))
                .liquid(new FluidBuilder().temperature(140))
                .components(Nitrogen, 2, Oxygen, 3)
                .colorAverage()
                .build();

        return startId;
    }
}
