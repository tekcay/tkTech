package tkcy.simpleaddon.api.unification.properties;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule.missingFluidPropertyMaterial;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule;

@UtilityClass
public class PropertiesAddition {

    public static void addFluidProperty(Material material, int mp) {
        MaterialProperties properties = material.getProperties();
        properties.ensureSet(PropertyKey.FLUID);
        FluidProperty property = properties.getProperty(PropertyKey.FLUID);
        property.enqueueRegistration(FluidStorageKeys.LIQUID, new FluidBuilder().temperature(mp));
    }

    public static void init() {
        // Germanium
        Germanium.setProperty(PropertyKey.DUST, new DustProperty());
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        addFluidProperty(Germanium, 938 + 273);
        addFluidProperty(Barium, 727 + 273);

        Carbon.setProperty(PropertyKey.INGOT, new IngotProperty());
    }
}
