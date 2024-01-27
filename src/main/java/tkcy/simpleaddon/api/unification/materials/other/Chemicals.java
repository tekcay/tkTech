package tkcy.simpleaddon.api.unification.materials.other;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.GTUtility.gregtechId;

import gregtech.api.unification.material.Material;

import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;

public class Chemicals {

    public static int register(int startId) {
        TKCYSAMaterials.SodiumNitrite = new Material.Builder(startId++, gregtechId("sodium_nitrite"))
                .dust()
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 2)
                .colorAverage()
                .build();

        return startId;
    }
}
