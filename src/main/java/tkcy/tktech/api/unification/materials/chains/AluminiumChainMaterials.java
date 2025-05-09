package tkcy.tktech.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_CENTRIFUGING;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;
import static tkcy.tktech.api.utils.TkTechUtil.tktech;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AluminiumChainMaterials {

    public static int init(int id) {
        Alumina = new Material.Builder(id++, tktech("alumina"))
                .dust()
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 2, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        PotassiumAluminate = new Material.Builder(id++, tktech("potassium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        SodiumAluminate = new Material.Builder(id++, tktech("sodium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        BauxiteResidue = new Material.Builder(id++, tktech("bauxite_residue"))
                .dust()
                .components(Ilmenite, 2, Rutile, 1)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .colorAverage()
                .build();

        PotashTreatedBauxite = new Material.Builder(id++, tktech("potash_treated_bauxite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(PotassiumAluminate, 1, BauxiteResidue, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        SodaTreatedBauxite = new Material.Builder(id++, tktech("soda_treated_bauxite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(SodiumAluminate, 1, BauxiteResidue, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        AluminiumHydroxide = new Material.Builder(id++, tktech("aluminium_hydroxide"))
                .dust()
                .liquid(new FluidBuilder().temperature(573))
                .components(Aluminium, 1, Oxygen, 3, Hydrogen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();
        AluminiumHydroxide.setFormula("Al(OH)3", true);

        TreatedSodiumAluminate = new Material.Builder(id++, tktech("treated_sodium_aluminate"))
                .fluid()
                .components(AluminiumHydroxide, 7, SodiumHydroxide, 3, DistilledWater, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        TreatedPotassiumAluminate = new Material.Builder(id++, tktech("treated_potassium_aluminate"))
                .fluid()
                .components(AluminiumHydroxide, 7, PotassiumHydroxide, 3, DistilledWater, 6)
                .iconSet(DULL)
                .colorAverage()
                .build();

        DriedTreatedSodiumAluminate = new Material.Builder(id++, tktech("dried_treated_sodium_aluminate"))
                .dust()
                .components(AluminiumHydroxide, 7, SodiumHydroxide, 3)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .iconSet(DULL)
                .colorAverage()
                .build();

        DriedTreatedPotassiumAluminate = new Material.Builder(id++, tktech("dried_treated_potassium_aluminate"))
                .dust()
                .components(AluminiumHydroxide, 7, PotassiumHydroxide, 3)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .iconSet(DULL)
                .colorAverage()
                .build();

        AluminiumFluoride = new Material.Builder(id++, tktech("aluminium_fluoride"))
                .dust()
                .liquid(new FluidBuilder().temperature(1560))
                .components(Aluminium, 1, Fluorine, 3)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();

        Cryolite = new Material.Builder(id++, tktech("cryolite"))
                .liquid(new FluidBuilder().temperature(1285))
                .colorAverage()
                .components(Sodium, 3, Aluminium, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .build();

        HexafluorosilicAcid = new Material.Builder(id++, tktech("hexafluorosilic_acid"))
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .components(Hydrogen, 2, Silicon, 1, Fluorine, 6)
                .colorAverage()
                .build();

        return id;
    }
}
