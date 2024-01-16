package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

public class FluorineChainMaterials {

    public static int init(int id) {
        // FluorineChain

        HydrogenFluoride = new Material.Builder(id++, gregtechId("hydrogen_fluoride"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Fluorine, 1)
                .colorAverage()
                .build();

        PotassiumBifluoride = new Material.Builder(id++, gregtechId("potassium_bifluoride"))
                .dust()
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID)
                        .temperature(512))
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Fluorine, 1, HydrogenFluoride, 1)
                .colorAverage()
                .build();

        Fluorite = new Material.Builder(id++, gregtechId("fluorite"))
                .dust().ore()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .colorAverage()
                .build();

        CalciumSulfate = new Material.Builder(id++, gregtechId("calcium_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        LithiumFluoride = new Material.Builder(id++, gregtechId("lithium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        SodiumFluoride = new Material.Builder(id++, gregtechId("sodium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        PotassiumFluoride = new Material.Builder(id++, gregtechId("potassium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        LithiumHydroxide = new Material.Builder(id++, gregtechId("lithium_hydroxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        return id;
    }
}
