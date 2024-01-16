package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.Materials.Hydrogen;
import static gregtech.api.unification.material.info.MaterialFlags.DECOMPOSITION_BY_CENTRIFUGING;
import static gregtech.api.unification.material.info.MaterialFlags.DISABLE_DECOMPOSITION;
import static gregtech.api.unification.material.info.MaterialIconSet.DULL;
import static gregtech.api.util.GTUtility.gregtechId;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.unification.material.Material;

public class AluminiumChainMaterials {

    public static int init(int id) {
        Alumina = new Material.Builder(id++, gregtechId("alumina"))
                .dust()
                .fluid()
                .flags(DISABLE_DECOMPOSITION)
                .components(Aluminium, 2, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        PotassiumAluminate = new Material.Builder(id++, gregtechId("potassium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Potassium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        SodiumAluminate = new Material.Builder(id++, gregtechId("sodium_aluminate"))
                .dust()
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 1, Aluminium, 1, Oxygen, 3)
                .iconSet(DULL)
                .colorAverage()
                .build();

        BauxiteResidue = new Material.Builder(id++, gregtechId("bauxite_residue"))
                .dust()
                .components(Ilmenite, 2, Rutile, 1)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .colorAverage()
                .build();

        PotashTreatedBauxite = new Material.Builder(id++, gregtechId("potash_treated_bauxite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(PotassiumAluminate, 1, BauxiteResidue, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        SodaTreatedBauxite = new Material.Builder(id++, gregtechId("soda_treated_bauxite"))
                .dust()
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .components(SodiumAluminate, 1, BauxiteResidue, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        AluminiumHydroxide = new Material.Builder(id++, gregtechId("aluminium_hydroxide"))
                .dust()
                .liquid(new FluidBuilder().temperature(573))
                .components(Aluminium, 1, Oxygen, 3, Hydrogen, 3)
                .flags(DISABLE_DECOMPOSITION)
                .colorAverage()
                .build();
        AluminiumHydroxide.setFormula("Al(OH)3", true);

        TreatedSodiumAluminate = new Material.Builder(id++, gregtechId("treated_sodium_aluminate"))
                .fluid()
                .components(AluminiumHydroxide, 7, SodiumHydroxide, 3, DistilledWater, 1)
                .iconSet(DULL)
                .colorAverage()
                .build();

        TreatedPotassiumAluminate = new Material.Builder(id++, gregtechId("treated_potassium_aluminate"))
                .fluid()
                .components(AluminiumHydroxide, 7, PotassiumHydroxide, 3, DistilledWater, 6)
                .iconSet(DULL)
                .colorAverage()
                .build();

        DriedTreatedSodiumAluminate = new Material.Builder(id++, gregtechId("dried_treated_sodium_aluminate"))
                .dust()
                .components(AluminiumHydroxide, 7, SodiumHydroxide, 3)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .iconSet(DULL)
                .colorAverage()
                .build();

        DriedTreatedPotassiumAluminate = new Material.Builder(id++, gregtechId("dried_treated_potassium_aluminate"))
                .dust()
                .components(AluminiumHydroxide, 7, PotassiumHydroxide, 3)
                .flags(DECOMPOSITION_BY_CENTRIFUGING)
                .iconSet(DULL)
                .colorAverage()
                .build();

        AluminiumFluoride = new Material.Builder(id++, gregtechId("aluminium_fluoride"))
                .dust()
                .liquid(new FluidBuilder().temperature(1560))
                .colorAverage()
                .build();
        AluminiumFluoride.setFormula("AlF3", true);

        Cryolite = new Material.Builder(id++, gregtechId("cryolite"))
                .liquid(new FluidBuilder().temperature(1285))
                .colorAverage()
                .build();
        Cryolite.setFormula("Na3AlF6", true);

        HexafluorosilicAcid = new Material.Builder(id++, gregtechId("hexafluorosilic_acid"))
                .fluid()
                .colorAverage()
                .build();
        HexafluorosilicAcid.setFormula("H2SiF6", true);

        return id;
    }
}
