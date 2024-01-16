package tkcy.simpleaddon.api.unification.properties;

import static gregtech.api.unification.material.Materials.Brick;
import static gregtech.api.unification.material.Materials.Germanium;

import gregtech.api.unification.material.properties.*;

public class PropertiesAddition {

    public static void init() {
        // Germanium
        Germanium.setProperty(PropertyKey.DUST, new DustProperty());
        Germanium.setProperty(PropertyKey.INGOT, new IngotProperty());
        Germanium.setProperty(PropertyKey.FLUID, new FluidProperty());

        // Brick
        Brick.setProperty(PropertyKey.FLUID_PIPE, new FluidPipeProperties(2500, 1, false, false,
                false, false));
    }
}
