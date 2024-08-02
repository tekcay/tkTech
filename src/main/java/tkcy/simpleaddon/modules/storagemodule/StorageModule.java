package tkcy.simpleaddon.modules.storagemodule;

import static tkcy.simpleaddon.api.metatileentities.MaterialMetaTileEntity.getMetaTileEntityId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.metatileentities.MaterialMetaTileEntity;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.MetaTileEntityMultiblockCrate;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.MetaTileEntityMultiblockTank;
import tkcy.simpleaddon.common.metatileentities.storage.MetaTileEntityModulableCrateValve;
import tkcy.simpleaddon.common.metatileentities.storage.MetaTileEntityModulableTankValve;

@UtilityClass
public class StorageModule {

    public @interface StorageModulable {}

    public static final List<Material> TANK_MATERIALS = new ArrayList<>();

    static {
        TANK_MATERIALS.add(Materials.Steel);
        TANK_MATERIALS.add(Materials.StainlessSteel);
        TANK_MATERIALS.add(Materials.TungstenCarbide);
        TANK_MATERIALS.add(TKCYSAMaterials.GalvanizedSteel);
    }

    public static final List<Material> CRATE_MATERIALS = new ArrayList<>();

    static {
        CRATE_MATERIALS.add(Materials.Steel);
    }

    public static MetaTileEntityModulableTankValve getTankValve(Material material) {
        return getMaterialMetaTileEntity(material, TANK_MATERIALS.size(), TKCYSAMetaTileEntities.MODULABLE_TANK_VALVES);
    }

    public static MetaTileEntityModulableCrateValve getCrateValve(Material material) {
        return getMaterialMetaTileEntity(material, CRATE_MATERIALS.size(),
                TKCYSAMetaTileEntities.MODULABLE_CRATE_VALVES);
    }

    @Nullable
    private static <T extends MaterialMetaTileEntity> T getMaterialMetaTileEntity(Material material, int materials,
                                                                                  T[] metaTileEntities) {
        return IntStream.range(0, materials)
                .boxed()
                .map(i -> metaTileEntities[i])
                .filter(mte -> mte.getMaterial() == material)
                .findFirst()
                .orElse(null);
    }

    public static MetaTileEntityModulableTankValve initTankValve(Material material) {
        return new MetaTileEntityModulableTankValve(getMetaTileEntityId("modulable_tank_valve.", material), material);
    }

    public static MetaTileEntityModulableCrateValve initCrateValve(Material material) {
        return new MetaTileEntityModulableCrateValve(getMetaTileEntityId("modulable_crate_valve.", material), material);
    }

    public static MetaTileEntityMultiblockTank initModulableLargeTank(Material material) {
        return initModulableTank(material, true);
    }

    public static MetaTileEntityMultiblockTank initModulableTank(Material material) {
        return initModulableTank(material, false);
    }

    private static MetaTileEntityMultiblockTank initModulableTank(Material material, boolean isLarge) {
        String baseResource = isLarge ? "modulable_large_tank." : "modulable_tank.";
        return new MetaTileEntityMultiblockTank(getMetaTileEntityId(baseResource, material), material, isLarge);
    }

    public static MetaTileEntityMultiblockCrate initModulableLargeCrate(Material material) {
        return initModulableCrate(material, true);
    }

    public static MetaTileEntityMultiblockCrate initModulableCrate(Material material) {
        return initModulableCrate(material, false);
    }

    private static MetaTileEntityMultiblockCrate initModulableCrate(Material material, boolean isLarge) {
        String baseResource = isLarge ? "modulable_large_crate." : "modulable_crate.";
        return new MetaTileEntityMultiblockCrate(getMetaTileEntityId(baseResource, material), material, isLarge);
    }
}
