package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.util.GTUtility.gregtechId;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;

public class PlatinumGroupChainMaterials {

    public static int register(int startId) {
        TKCYSAMaterials.TreatedPlatinumGroupSludge = new Material.Builder(startId++,
                gregtechId("treated_platinum_group_sludge"))
                        .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                        .components(Materials.PlatinumGroupSludge, 5, Materials.AquaRegia, 1)
                        .colorAverage()
                        .build();
        TKCYSAMaterials.TreatedPlatinumGroupSludge.setFormula("");

        return startId;
    }
}
