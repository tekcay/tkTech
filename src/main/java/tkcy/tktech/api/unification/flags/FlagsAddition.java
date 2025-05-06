package tkcy.tktech.api.unification.flags;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.material.info.MaterialFlags.*;
import static tkcy.tktech.api.unification.flags.TkTechMaterialFlags.*;
import static tkcy.tktech.modules.ComponentsModule.getLvAcceptedRubberMaterials;
import static tkcy.tktech.modules.ElectrodeModule.electrodeMaterials;
import static tkcy.tktech.modules.alloyingmodule.AlloyingModule.alloysMaterials;

import org.jetbrains.annotations.ApiStatus;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.materials.TkTechMaterials;
import tkcy.tktech.modules.PolymersModule;
import tkcy.tktech.modules.storagemodule.StorageModule;

@UtilityClass
@ApiStatus.Internal
public class FlagsAddition {

    public static void init() {
        Zinc.addFlags(GENERATE_LONG_ROD);
        Brick.addFlags(GENERATE_LONG_ROD, GENERATE_ROD, GENERATE_GEAR, GENERATE_PLATE, GENERATE_RING,
                GENERATE_SMALL_GEAR);
        Cinnabar.addFlags(DISABLE_DECOMPOSITION);
        Redstone.addFlags(DISABLE_DECOMPOSITION);
        TkTechMaterialFlags.GENERATE_ALL.forEach(f -> {
            Steel.addFlags(f);
            StainlessSteel.addFlags(f);
            TungstenCarbide.addFlags(f);
            TkTechMaterials.GalvanizedSteel.addFlags(f);
        });

        addStorageWallFlag();

        Materials.EXT2_METAL.forEach(f -> Brick.addFlags(f));

        GENERATE_ALL_NO_UNIF.forEach(flag -> Carbon.addFlags(flag));

        Steel.addFlags(GENERATE_SOLDERING_IRON_TIP);

        addAllAlloyFlags(TinAlloy);

        electrodeMaterials.forEach(FlagsAddition::addElectrodeFlag);
        alloysMaterials.forEach(FlagsAddition::addAlloyFlag);
        getLvAcceptedRubberMaterials().forEach(FlagsAddition::addRingAndPlateFlags);
        PolymersModule.GTCEu_POLYMERS.forEach(material -> material.addFlags(IS_POLYMER));
    }

    @StorageModule.StorageModulable
    public static void addStorageWallFlag() {
        StorageModule.CRATE_MATERIALS.forEach(material -> material.addFlags(GENERATE_WALL));
        StorageModule.TANK_MATERIALS.forEach(material -> material.addFlags(GENERATE_WALL));
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
