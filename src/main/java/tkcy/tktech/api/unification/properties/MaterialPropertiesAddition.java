package tkcy.tktech.api.unification.properties;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.*;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.materials.TkTechMaterials;

@UtilityClass
public class MaterialPropertiesAddition {

    public static void addFluidProperty(Material material, int mp) {
        MaterialProperties properties = material.getProperties();
        properties.ensureSet(PropertyKey.FLUID);
        FluidProperty property = properties.getProperty(PropertyKey.FLUID);
        property.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(mp));
    }

    public static void init() {
        ToxicMaterialProperty.addToxicMaterialProperty(Materials.Benzene);
        ToxicMaterialProperty.addToxicMaterialProperty(Materials.CoalGas);
        CorrosiveMaterialProperty.addCorrosiveMaterialProperty(TkTechMaterials.HydrogenFluoride);
        PhysicalProperties.Builder builder = new PhysicalProperties.Builder().autoIgnitionTemperature(503).bp(348)
                .mp(176)
                .flameTemperature(2120);
        PhysicalProperties.addPhysicalMaterialProperty(TkTechMaterials.HydrogenFluoride, builder);
    }
}
