package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FluorineChainMaterials {

    public static int init(int id) {
        // FluorineChain

        HydrogenFluoride = new Material.Builder(id++, tkcysa("hydrogen_fluoride"))
                .gas()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 1, Fluorine, 1)
                .colorAverage()
                .build();

        PotassiumBifluoride = new Material.Builder(id++, tkcysa("potassium_bifluoride"))
                .dust()
                .liquid(new FluidBuilder().attribute(FluidAttributes.ACID)
                        .temperature(512))
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.DULL)
                .components(Potassium, 1, Fluorine, 1, HydrogenFluoride, 1)
                .colorAverage()
                .build();

        Fluorite = new Material.Builder(id++, tkcysa("fluorite"))
                .dust().ore()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Fluorine, 2)
                .colorAverage()
                .build();

        CalciumSulfate = new Material.Builder(id++, tkcysa("calcium_sulfate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .iconSet(MaterialIconSet.BRIGHT)
                .components(Calcium, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        LithiumFluoride = new Material.Builder(id++, tkcysa("lithium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        SodiumFluoride = new Material.Builder(id++, tkcysa("sodium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        PotassiumFluoride = new Material.Builder(id++, tkcysa("potassium_fluoride"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Fluorine, 1)
                .colorAverage()
                .build();

        LithiumHydroxide = new Material.Builder(id++, tkcysa("lithium_hydroxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Lithium, 1, Oxygen, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        return id;
    }
}
