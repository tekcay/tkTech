package tkcy.tktech.api.unification.properties;

import static tkcy.tktech.api.unification.properties.ChemicalStructureProperty.addChemicalStructureProperty;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MaterialPropertiesAddition {

    public static void addFluidProperty(Material material, int mp) {
        MaterialProperties properties = material.getProperties();
        properties.ensureSet(PropertyKey.FLUID);
        FluidProperty property = properties.getProperty(PropertyKey.FLUID);
        property.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(mp));
    }

    public static void init() {
        Materials.Benzene.getProperties().ensureSet(PropertyKey.DUST);
        addChemicalStructureProperty(Materials.Benzene, 46, 40);
        addChemicalStructureProperty(Materials.Toluene, 70, 41);
    }
}
