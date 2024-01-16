package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import org.jetbrains.annotations.ApiStatus;

import gregtech.api.unification.material.Materials;

@ApiStatus.Internal
public class FlagsAddition {

    public static void init() {
        Zinc.addFlags(GENERATE_LONG_ROD);
        Brick.addFlags(GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_GEAR, GENERATE_PLATE, GENERATE_RING,
                GENERATE_SMALL_GEAR);
        TKCYSAMaterialFlags.GENERATE_ALL.forEach(f -> Steel.addFlags(f));
        Materials.EXT2_METAL.forEach(f -> Brick.addFlags(f));
    }
}
