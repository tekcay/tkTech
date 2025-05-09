package tkcy.tktech.api.unification;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_CENTRIFUGING;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;
import static tkcy.tktech.api.utils.TkTechUtil.tktech;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoastingMaterials {

    public static int init(int id) {
        // More roastable ores
        Kesterite = new Material.Builder(id++, tktech("kesterite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Zinc, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Stannite = new Material.Builder(id++, tktech("stannite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Copper, 2, Iron, 1, Tin, 1, Sulfur, 4)
                .iconSet(DULL)
                .colorAverage()
                .build();

        Arsenopyrite = new Material.Builder(id++, tktech("arsenopyrite"))
                .dust().ore()
                .addOreByproducts(Materials.Cobalt, Materials.Copper, Materials.Iron)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iron, 1, Arsenic, 1, Sulfur, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        RoastedTetrahedrite = new Material.Builder(id++, tktech("roasted.tetrahedrite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Materials.CupricOxide, 6, Materials.AntimonyTrioxide, 1, BandedIron, 1)
                .colorAverage()
                .build();

        RoastedCobaltite = new Material.Builder(id++, tktech("roasted.cobaltite"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(CobaltOxide, 2, ArsenicTrioxide, 1)
                .colorAverage()
                .build();

        SilverOxide = new Material.Builder(id++, tktech("silver_oxide"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Silver, 2, Oxygen, 1)
                .colorAverage()
                .build();

        MolybdenumTrioxide = new Material.Builder(id++, tktech("molybdenum_trioxide"))
                .dust()
                .fluid()
                .components(Molybdenum, 1, Oxygen, 3)
                .colorAverage()
                .build();

        RoastedGalena = new Material.Builder(id++, tktech("roasted.galena"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(Massicot, 9, SilverOxide, 6)
                .colorAverage()
                .build();

        RoastedChalcopyrite = new Material.Builder(id++, tktech("roasted.chalcopyrite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(CupricOxide, 1, Ferrosilite, 1)
                .colorAverage()
                .build();

        RoastedKesterite = new Material.Builder(id++, tktech("roasted.kesterite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(CupricOxide, 4, Zincite, 1, Cassiterite, 1)
                .colorAverage()
                .build();

        RoastedStannite = new Material.Builder(id++, tktech("roasted.stannite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(CupricOxide, 4, BandedIron, 1, Cassiterite, 2)
                .colorAverage()
                .build();

        RoastedArsenopyrite = new Material.Builder(id++, tktech("roasted.arsenopyrite"))
                .dust().flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(BandedIron, 1, ArsenicTrioxide, 1)
                .colorAverage()
                .build();

        RoastedBornite = new Material.Builder(id++, tktech("roasted.bornite"))
                .dust().flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(CupricOxide, 10, BandedIron, 1)
                .colorAverage()
                .build();

        return id;
    }
}
