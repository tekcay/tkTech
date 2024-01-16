package tkcy.simpleaddon.api.unification.materials;

import gregtech.api.unification.material.Material;

import tkcy.simpleaddon.api.unification.MaterialIconAddition;
import tkcy.simpleaddon.api.unification.MaterialsFormula;
import tkcy.simpleaddon.api.unification.RoastingMaterials;
import tkcy.simpleaddon.api.unification.TKCYSAFirstDegreeMaterials;
import tkcy.simpleaddon.api.unification.materials.chains.*;
import tkcy.simpleaddon.api.unification.properties.PropertiesAddition;

public class TKCYSAMaterials {

    public static void init() {
        // 4000 - 4200
        TKCYSAFirstDegreeMaterials.init();

        // 4201
        int id = 4200;
        id = RoastingMaterials.init(id);
        id = AluminiumChainMaterials.init(id);
        id = GoldChainMaterials.init(id);
        id = ChromiteChainMaterials.init(id);
        id = ZincChainMaterials.init(id);
        id = GermaniumChainMaterials.init(id);
        id = SmallChains.init(id);

        id = AlloysMaterials.init(id);

        MaterialsFormula.init();

        PropertiesAddition.init();

        MaterialIconAddition.init();
    }

    // Chromite chain
    public static Material SodiumChromate;
    public static Material SodiumDichromate;
    public static Material ChromiumOxide;
    public static Material SodiumSulfate;
    public static Material Alumina;

    // Gold chain
    public static Material PreciousMetal;
    public static Material GoldAlloy;
    public static Material GoldLeach;
    public static Material CopperLeach;
    public static Material ChloroauricAcid;
    public static Material PotassiumBisulfate;
    public static Material PotassiumMetaBisulfite;
    public static Material PotassiumHydroxide;

    // Iron
    public static Material PigIron;

    // Tungsten Chain
    public static Material TungstenOxide;

    // Roasting
    public static Material Kesterite;
    public static Material Stannite;
    public static Material Arsenopyrite;
    public static Material MolybdenumTrioxide;

    public static Material RoastedTetrahedrite;
    public static Material RoastedCobaltite;
    public static Material RoastedGalena;
    public static Material SilverOxide;
    public static Material RoastedChalcopyrite;
    public static Material RoastedKesterite;
    public static Material RoastedStannite;
    public static Material RoastedArsenopyrite;
    public static Material RoastedBornite;

    // Zinc chain
    public static Material ZincSulfate;
    public static Material ZincLeachingSolution;

    // Germanium chain
    public static Material ZincLeachingResidue;
    public static Material GermanicAcid;
    public static Material IronSulfate;
    public static Material GermaniumSulfide;
    public static Material GermaniumOxide;
    public static Material GermaniumTetrachloride;
    public static Material Argyrodite;

    // BauxiteChain
    public static Material PotassiumAluminate;
    public static Material SodiumAluminate;
    public static Material BauxiteResidue;
    public static Material AluminiumFluoride;
    public static Material Cryolite;
    public static Material HexafluorosilicAcid;
    public static Material AluminiumHydroxide;

    // Alloys
    public static Material GalvanizedSteel;
    public static Material Monel;
}
