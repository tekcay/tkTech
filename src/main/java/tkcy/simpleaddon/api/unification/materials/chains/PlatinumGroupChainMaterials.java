package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialFlags;

public class PlatinumGroupChainMaterials {

    public static int register(int startId) {
        TreatedPlatinumGroupSludge = new Material.Builder(startId++,
                gregtechId("treated_platinum_group_sludge"))
                        .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                        .components(PlatinumGroupSludge, 5, AquaRegia, 1)
                        .colorAverage()
                        .build();
        TreatedPlatinumGroupSludge.setFormula("");

        RhodiumHydroxide = new Material.Builder(startId++, gregtechId("rhodium_hydroxide"))
                .dust()
                .fluid()
                .components(Rhodium, 1, Oxygen, 2, Hydrogen, 2)
                .colorAverage()
                .build();
        RhodiumHydroxide.setFormula("Rh(OH)2", true);

        ChlororhodicAcid = new Material.Builder(startId++, gregtechId("chlororhodic_acid"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .components(Hydrogen, 3, Rhodium, 1, Chlorine, 6)
                .colorAverage()
                .build();

        RhodiumPrecipitate = new Material.Builder(startId++, gregtechId("rhodium_precipitate"))
                .dust()
                .color(0x8c2222)
                .build();
        RhodiumPrecipitate.setFormula("Rh?");

        TreatedRhodiumPrecipitate = new Material.Builder(startId++, gregtechId("treated_rhodium_precipitate"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .components(RhodiumPrecipitate, 1, HydrochloricAcid, 4)
                .colorAverage()
                .build();

        HotRhodium = new Material.Builder(startId++, gregtechId("rhodium_hot"))
                .liquid(new FluidBuilder().temperature(1500))
                .flags(MaterialFlags.DISABLE_DECOMPOSITION)
                .components(Rhodium, 1)
                .colorAverage()
                .build();

        return startId;
    }
}
