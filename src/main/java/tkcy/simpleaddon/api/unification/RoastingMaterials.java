package tkcy.simpleaddon.api.unification;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

public class RoastingMaterials {

    public static int init(int id) {
        // More roastable ores
        Kesterite = new Material.Builder(id++, gregtechId("kesterite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Zinc, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Stannite = new Material.Builder(id++, gregtechId("stannite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Iron, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Arsenopyrite = new Material.Builder(id++, gregtechId("arsenopyrite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Arsenic, 1, Sulfur, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        RoastedTetrahedrite = new Material.Builder(id++, gregtechId("roasted.tetrahedrite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.CupricOxide, 6, Materials.AntimonyTrioxide, 1, BandedIron, 1)
                .color((CupricOxide.getMaterialRGB() * 6 + AntimonyTrioxide.getMaterialRGB() +
                        BandedIron.getMaterialRGB()) / 8)
                .build();

        RoastedCobaltite = new Material.Builder(id++, gregtechId("roasted.cobaltite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CobaltOxide, 2, ArsenicTrioxide, 1)
                .color((CobaltOxide.getMaterialRGB() + ArsenicTrioxide.getMaterialRGB()) / 2)
                .build();

        SilverOxide = new Material.Builder(id++, gregtechId("silver_oxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Silver, 2, Oxygen, 1)
                .colorAverage()
                .build();

        MolybdenumTrioxide = new Material.Builder(id++, gregtechId("molybdenum_trioxide"))
                .dust()
                .fluid()
                .components(Molybdenum, 1, Oxygen, 3)
                .colorAverage()
                .build();

        RoastedGalena = new Material.Builder(id++, gregtechId("roasted.galena"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Massicot, 9, SilverOxide, 6)
                .color((Massicot.getMaterialRGB() * 9 + SilverOxide.getMaterialRGB() * 6) / 15)
                .build();

        RoastedChalcopyrite = new Material.Builder(id++, gregtechId("roasted.chalcopyrite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 1, Ferrosilite, 1)
                .color((CupricOxide.getMaterialRGB() + Ferrosilite.getMaterialRGB()) / 2)
                .build();

        RoastedKesterite = new Material.Builder(id++, gregtechId("roasted.kesterite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 4, Zincite, 1, Cassiterite, 1)
                .color((CupricOxide.getMaterialRGB() + Ferrosilite.getMaterialRGB()) / 2)
                .build();

        RoastedStannite = new Material.Builder(id++, gregtechId("roasted.stannite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 4, BandedIron, 1, Cassiterite, 2)
                .color((CupricOxide.getMaterialRGB() * 4 + BandedIron.getMaterialRGB() +
                        Cassiterite.getMaterialRGB() * 2) / 7)
                .build();

        RoastedArsenopyrite = new Material.Builder(id++, gregtechId("roasted.arsenopyrite"))
                .dust().flags(DISABLE_DECOMPOSITION)
                .components(BandedIron, 1, ArsenicTrioxide, 1)
                .color((BandedIron.getMaterialRGB() + ArsenicTrioxide.getMaterialRGB()) / 2)
                .build();

        RoastedBornite = new Material.Builder(id++, gregtechId("roasted.bornite"))
                .dust().flags(DISABLE_DECOMPOSITION)
                .components(CupricOxide, 10, BandedIron, 1)
                .color((CupricOxide.getMaterialRGB() * 10 + BandedIron.getMaterialRGB()) / 11)
                .build();

        return id;
    }
}
