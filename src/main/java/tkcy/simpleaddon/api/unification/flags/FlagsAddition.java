package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.material.info.MaterialFlags.GENERATE_LONG_ROD;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class FlagsAddition {

    public static void init() {
        Zinc.addFlags(GENERATE_LONG_ROD);
        TKCYSAMaterialFlags.GENERATE_ALL.forEach(f -> Steel.addFlags(f));
    }
}
