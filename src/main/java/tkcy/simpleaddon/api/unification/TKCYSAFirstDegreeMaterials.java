package tkcy.simpleaddon.api.unification;

import static gregtech.api.fluids.attribute.FluidAttributes.ACID;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.*;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;

public final class TKCYSAFirstDegreeMaterials {

    private TKCYSAFirstDegreeMaterials() {}

    public static void init() {
        // Chromite Chain
        SodiumChromate = new Material.Builder(4001, gregtechId("sodium_chromate"))
                .dust()
                .color(0xe6d62c)
                .build();
        SodiumChromate.setFormula("Na2CrO4", true);

        SodiumDichromate = new Material.Builder(4002, gregtechId("sodium_dichromate"))
                .dust()
                .color(0xdb822e)
                .build();
        SodiumDichromate.setFormula("Na2Cr2O7", true);

        ChromiumOxide = new Material.Builder(4003, gregtechId("chromium_oxide"))
                .dust()
                .color(0x69c765)
                .build();
        ChromiumOxide.setFormula("Cr2O3", true);

        // GoldChain
        PreciousMetal = new Material.Builder(4004, gregtechId("precious_metal"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Gold, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        PreciousMetal.setFormula("Au?");

        GoldAlloy = new Material.Builder(4005, gregtechId("gold_alloy"))
                .ingot()
                .liquid(new FluidBuilder()
                        .temperature(1000))
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.Copper, 3, PreciousMetal, 1)
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldAlloy.setFormula("Cu3Au?", true);

        GoldLeach = new Material.Builder(4006, gregtechId("gold_leach"))
                .fluid()
                .iconSet(SHINY)
                .color(0xB99023)
                .build();
        GoldLeach.setFormula("CuAu?", true);

        CopperLeach = new Material.Builder(4007, gregtechId("copper_leach"))
                .dust()
                .iconSet(SHINY)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 27, Lead, 1, Iron, 1, Nickel, 1, Silver, 1)
                .colorAverage()
                .build();
        CopperLeach.setFormula("Cu?", true);

        PotassiumBisulfate = new Material.Builder(4008, gregtechId("potassium_bisulfate"))
                .dust()
                .iconSet(DULL)
                .components(Potassium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        PotassiumMetaBisulfite = new Material.Builder(4009, gregtechId("potassium_metabisulfite"))
                .dust()
                .iconSet(DULL)
                .components(Potassium, 2, Sulfur, 2, Oxygen, 5)
                .colorAverage()
                .build();

        ChloroauricAcid = new Material.Builder(4010, gregtechId("chloroauric_acid"))
                .liquid(new FluidBuilder()
                        .attributes(ACID))
                .components(Hydrogen, 1, Gold, 1, Chlorine, 4)
                .colorAverage()
                .build();

        PotassiumHydroxide = new Material.Builder(4011, gregtechId("potassium_hydroxide"))
                .dust()
                .components(Potassium, 1, Oxygen, 1, Hydrogen, 1)
                .colorAverage()
                .build();

        PigIron = new Material.Builder(4012, gregtechId("pig_iron"))
                .ingot()
                .flags(MaterialFlags.NO_UNIFICATION)
                .components(Iron, 1, Carbon, 1)
                .colorAverage()
                .build();

        TungstenOxide = new Material.Builder(4013, gregtechId("tungsten_oxide"))
                .dust()
                .components(Tungsten, 1, Oxygen, 3)
                .colorAverage()
                .build();

        ZincSulfate = new Material.Builder(4014, gregtechId("zinc_sulfate"))
                .dust()
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .colorAverage()
                .build();

        ZincLeachingSolution = new Material.Builder(4015, gregtechId("zinc_leaching_solution"))
                .liquid(new FluidBuilder().attributes(ACID))
                .components(SulfuricAcid, 1, Water, 1, Germanium, 1, Iron, 1)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumSulfide = new Material.Builder(4016, gregtechId("germanium_sulfide"))
                .dust()
                .components(Germanium, 1, Sulfur, 2)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumOxide = new Material.Builder(4017, gregtechId("germanium_oxide"))
                .dust()
                .components(Germanium, 1, Oxygen, 2)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        IronSulfate = new Material.Builder(4018, gregtechId("iron_sulfate"))
                .dust()
                .components(Iron, 1, Sulfur, 1, Oxygen, 4)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        ZincLeachingResidue = new Material.Builder(4019, gregtechId("zinc_leaching_residue"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        Argyrodite = new Material.Builder(4020, gregtechId("argyrodite"))
                .ore()
                .dust()
                .fluid()
                .components(Silver, 8, Germanium, 1, Sulfur, 6)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        GermaniumTetrachloride = new Material.Builder(4021, gregtechId("germanium_tetrachloride"))
                .liquid(new FluidBuilder().attributes(ACID))
                .components(Germanium, 1, Chlorine, 4)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();
    }
}
