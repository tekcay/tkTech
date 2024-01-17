package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.*;
import static tkcy.simpleaddon.modules.AlloyingModule.alloysMaterials;
import static tkcy.simpleaddon.modules.ElectrodeModule.electrodeMaterials;

import org.jetbrains.annotations.ApiStatus;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

@ApiStatus.Internal
public class FlagsAddition {

    public static void init() {
        Zinc.addFlags(GENERATE_LONG_ROD);
        Brick.addFlags(GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_GEAR, GENERATE_PLATE, GENERATE_RING,
                GENERATE_SMALL_GEAR);
        TKCYSAMaterialFlags.GENERATE_ALL.forEach(f -> Steel.addFlags(f));
        Materials.EXT2_METAL.forEach(f -> Brick.addFlags(f));

        Carbon.addFlags(GENERATE_LONG_ROD);

        electrodeMaterials.forEach(FlagsAddition::addElectrodeFlag);
        alloysMaterials.forEach(FlagsAddition::addAlloyFlag);
    }

    public static void addAlloyFlag(Material material) {
        material.addFlags(ALLOY);
    }

    public static void addElectrodeFlag(Material material) {
        material.addFlags(GENERATE_ELECTRODES);
    }
}
