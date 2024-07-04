package tkcy.simpleaddon.modules;

import static gregtech.api.unification.material.Materials.*;

import java.util.Arrays;
import java.util.List;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ElectrodeModule {

    public static List<Material> electrodeMaterials = Arrays.asList(
            Gold,
            Copper,
            Nickel,
            Zinc,
            Steel,
            Palladium,
            Platinum,
            Rhodium,
            Iridium,
            Rubidium,
            Molybdenum,
            Iron,
            Carbon,
            Manganese);
}
