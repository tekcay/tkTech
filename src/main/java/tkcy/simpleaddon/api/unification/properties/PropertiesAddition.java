package tkcy.simpleaddon.api.unification.properties;

import static gregtech.api.unification.material.Materials.Germanium;

import gregtech.api.unification.material.properties.*;

public class PropertiesAddition {

    public static void init() {
        // Germanium
        Germanium.setProperty(PropertyKey.DUST, new DustProperty());
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Germanium.setProperty(PropertyKey.FLUID, new FluidProperty());
    }
}
