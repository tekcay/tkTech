package tkcy.tktech.api.unification;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.tktech.api.unification.materials.TKCYSAMaterials.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MaterialsFormula {

    public static void init() {
        ZincLeachingSolution.setFormula("Ge?");
        // GermanicAcid.setFormula("Ge(OH)4", true);
        Hydrogen.setFormula("H2", true);
        Fluorine.setFormula("F2", true);
        Chlorine.setFormula("Cl2", true);
        Iodine.setFormula("I2", true);
        Sulfur.setFormula("S8", true);
        Oxygen.setFormula("O2", true);
        Nitrogen.setFormula("N2", true);
    }
}
