package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags.*;
import static tkcy.simpleaddon.modules.ComponentsModule.getLvAcceptedRubberMaterials;
import static tkcy.simpleaddon.modules.ElectrodeModule.electrodeMaterials;
import static tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule.alloysMaterials;

import org.jetbrains.annotations.ApiStatus;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.modules.PolymersModule;

@UtilityClass
@ApiStatus.Internal
public class FlagsAddition {

    public static void init() {
        Zinc.addFlags(GENERATE_LONG_ROD);
        Brick.addFlags(GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_GEAR, GENERATE_PLATE, GENERATE_RING,
                GENERATE_SMALL_GEAR);
        Cinnabar.addFlags(DISABLE_DECOMPOSITION);
        Redstone.addFlags(DISABLE_DECOMPOSITION);
        TKCYSAMaterialFlags.GENERATE_ALL.forEach(f -> {
            Steel.addFlags(f);
            StainlessSteel.addFlags(f);
            TungstenCarbide.addFlags(f);
            TKCYSAMaterials.GalvanizedSteel.addFlags(f);
        });

        Materials.EXT2_METAL.forEach(f -> Brick.addFlags(f));

        GENERATE_ALL_NO_UNIF.forEach(flag -> Carbon.addFlags(flag));

        Steel.addFlags(GENERATE_SOLDERING_IRON_TIP);

        addAllAlloyFlags(TinAlloy);

        electrodeMaterials.forEach(FlagsAddition::addElectrodeFlag);
        alloysMaterials.forEach(FlagsAddition::addAlloyFlag);
        getLvAcceptedRubberMaterials().forEach(FlagsAddition::addRingAndPlateFlags);
        PolymersModule.GTCEu_POLYMERS.forEach(material -> material.addFlags(IS_POLYMER));
    }

    public static void addAlloyFlag(Material material) {
        material.addFlags(ALLOY);
    }

    public static void addElectrodeFlag(Material material) {
        material.addFlags(GENERATE_ELECTRODES);
    }

    public static void addRingAndPlateFlags(Material material) {
        material.addFlags(GENERATE_RING, GENERATE_PLATE);
    }

    public static void addAllAlloyFlags(Material material) {
        GENERATE_ALL.forEach(material::addFlags);
    }
}
