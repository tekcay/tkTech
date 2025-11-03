package tkcy.tktech.api.unification.properties;

import gregtech.api.unification.material.properties.PropertyKey;

public class TkTechMaterialPropertyKeys {

    public static final PropertyKey<ChemicalStructureProperty> CHEMICAL_STRUCTURE = new PropertyKey<>(
            "chemical_structure", ChemicalStructureProperty.class);

    public static final PropertyKey<ToxicMaterialProperty> TOXIC = new PropertyKey<>(
            "toxic", ToxicMaterialProperty.class);
}
