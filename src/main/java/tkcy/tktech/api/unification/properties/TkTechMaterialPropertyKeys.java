package tkcy.tktech.api.unification.properties;

import gregtech.api.unification.material.properties.PropertyKey;

public class TkTechMaterialPropertyKeys {

    public static final PropertyKey<ChemicalStructureProperty> CHEMICAL_STRUCTURE = new PropertyKey<>(
            "chemical_structure", ChemicalStructureProperty.class);

    public static final PropertyKey<ToxicMaterialProperty> TOXIC = new PropertyKey<>(
            "toxic", ToxicMaterialProperty.class);

    public static final PropertyKey<CorrosiveMaterialProperty> CORROSIVE = new PropertyKey<>("corrosive",
            CorrosiveMaterialProperty.class);

    public static final PropertyKey<PhysicalProperties> PHYSICAL_PROPERTIES = new PropertyKey<>(
            "physical_properties",
            PhysicalProperties.class);

    static {
        ITooltipMaterialProperty.TOOLTIP_MATERIAL_PROPERTIES.add(PHYSICAL_PROPERTIES);
        ITooltipMaterialProperty.TOOLTIP_MATERIAL_PROPERTIES.add(TOXIC);
        ITooltipMaterialProperty.TOOLTIP_MATERIAL_PROPERTIES.add(CORROSIVE);
    }
}
