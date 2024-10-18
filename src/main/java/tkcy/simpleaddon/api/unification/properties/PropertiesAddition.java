package tkcy.simpleaddon.api.unification.properties;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule.missingFluidPropertyMaterial;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertiesAddition {

    public static void addFluidProperty(Material material) {
        // material.setProperty(PropertyKey.FLUID, new FluidProperty());
    }

    public static void init() {
        // Germanium
        Germanium.setProperty(PropertyKey.DUST, new DustProperty());
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        addFluidProperty(Germanium);

        Carbon.setProperty(PropertyKey.INGOT, new IngotProperty());
        missingFluidPropertyMaterial.forEach(PropertiesAddition::addFluidProperty);
    }
}
