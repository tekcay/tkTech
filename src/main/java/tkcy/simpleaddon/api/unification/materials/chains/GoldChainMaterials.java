package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.fluids.attribute.FluidAttributes.ACID;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.SHINY;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GoldChainMaterials {

    public static int init(int id) {
        PreciousMetal = new Material.Builder(id++, tkcysa("precious_metal"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

        GoldAlloy = new Material.Builder(id++, tkcysa("gold_alloy"))
                .ingot()
                .liquid(new FluidBuilder()
                        .temperature(1000))
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Copper, 3, PreciousMetal, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldAlloy.setFormula("Cu3Au?", true);

        GoldLeach = new Material.Builder(id++, tkcysa("gold_leach"))
                .fluid()
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldLeach.setFormula("CuAu?", true);

        CopperLeach = new Material.Builder(id++, tkcysa("copper_leach"))
                .dust()
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 27, Lead, 1, Iron, 1, Nickel, 1, Silver, 1)
                .colorAverage()
                .build();
        CopperLeach.setFormula("Cu?", true);

        ChloroauricAcid = new Material.Builder(id++, tkcysa("chloroauric_acid"))
                .liquid(new FluidBuilder()
                        .attributes(ACID))
                .components(Hydrogen, 1, Gold, 1, Chlorine, 4)
                .colorAverage()
                .build();

        return id;
    }
}
