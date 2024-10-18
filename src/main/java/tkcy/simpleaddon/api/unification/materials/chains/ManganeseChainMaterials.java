package tkcy.simpleaddon.api.unification.materials.chains;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.attribute.FluidAttributes;
import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ManganeseChainMaterials {

    public static int register(int startId) {
        DimethylOxalate = new Material.Builder(startId++, tkcysa("diethyl_oxalate"))
                .fluid()
                .colorAverage()
                .components(Carbon, 6, Hydrogen, 10, Oxygen, 4)
                .build();
        DimethylOxalate.setFormula("(CO2Et)2", true);

        DiethylOxalate = new Material.Builder(startId++, tkcysa("dimethyl_oxalate"))
                .fluid()
                .colorAverage()
                .components(Carbon, 4, Hydrogen, 6, Oxygen, 4)
                .build();
        DiethylOxalate.setFormula("(CO2Me)2", true);

        DryOxalicAcid = new Material.Builder(startId++, tkcysa("dry_oxalic_acid"))
                .dust()
                .colorAverage()
                .components(Carbon, 2, Hydrogen, 2, Oxygen, 4)
                .build();
        DryOxalicAcid.setFormula("(CO2H)2", true);

        OxalicAcid = new Material.Builder(startId++, tkcysa("oxalic_acid"))
                .dust()
                .colorAverage()
                .components(DryOxalicAcid, 1, Water, 1)
                .build();
        OxalicAcid.setFormula(DryOxalicAcid.getChemicalFormula() + ".H2O", true);

        CrudeOxalicAcid = new Material.Builder(startId++, tkcysa("crude_oxalic_acid"))
                .dust()
                .colorAverage()
                .components(DryOxalicAcid, 1, Water, 1)
                .build();
        CrudeOxalicAcid.setFormula(OxalicAcid.getChemicalFormula() + " ?");

        CrudeDiethylOxalate = new Material.Builder(startId++, tkcysa("crude_diethyl_oxalate"))
                .colorAverage()
                .fluid()
                .components(DiethylOxalate, 1, Water, 2, Methanol, 2, Lithium, 2)
                .build();
        CrudeDiethylOxalate.setFormula(OxalicAcid.getChemicalFormula() + " ?");

        CrudeDimethylOxalate = new Material.Builder(startId++, tkcysa("crude_dimethyl_oxalate"))
                .colorAverage()
                .fluid()
                .components(DimethylOxalate, 1, Water, 2, Ethanol, 2, Lithium, 2)
                .build();
        CrudeDimethylOxalate.setFormula(OxalicAcid.getChemicalFormula() + " ?");

        TreatedSpessartine = new Material.Builder(startId++, tkcysa("treated_spessartine"))
                .liquid(new FluidBuilder().attributes(FluidAttributes.ACID))
                .components(Spessartine, 1, SulfuricAcid, 1, OxalicAcid, 1)
                .colorAverage()
                .build();

        return startId;
    }
}
