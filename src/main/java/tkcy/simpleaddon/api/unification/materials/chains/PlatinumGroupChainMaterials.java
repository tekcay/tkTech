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

        TKCYSAMaterials.RhodiumHydroxide = new Material.Builder(startId++, gregtechId("rhodium_hydroxide"))
                .dust()
                .fluid()
                .components(Materials.Rhodium, 1, Materials.Oxygen, 2, Materials.Hydrogen, 2)
                .colorAverage()
                .build();

        TKCYSAMaterials.ChlororhodicAcid = new Material.Builder(startId++, gregtechId("chlororhodic_acid"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .components(Materials.Hydrogen, 3, Materials.Rhodium, 1, Materials.Chlorine, 6)
                .colorAverage()
                .build();

        TKCYSAMaterials.RhodiumPrecipitate = new Material.Builder(startId, gregtechId("rhodium_precipitate"))
                .dust()
                .color(0x8c2222)
                .build();
        TKCYSAMaterials.RhodiumPrecipitate.setFormula("Rh?");



        return startId;
    }
}
