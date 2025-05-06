package tkcy.tktech.modules.storagemodule;

import static gregtech.api.util.RelativeDirection.*;
import static gregtech.api.util.RelativeDirection.UP;
import static tkcy.tktech.api.metatileentities.MaterialMetaTileEntity.getMetaTileEntityId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Nullable;

import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.metatileentities.MaterialMetaTileEntity;
import tkcy.tktech.api.metatileentities.RepetitiveSide;
import tkcy.tktech.api.unification.materials.TKCYSAMaterials;
import tkcy.tktech.common.metatileentities.TKCYSAMetaTileEntities;
import tkcy.tktech.common.metatileentities.multiprimitive.MetaTileEntityMultiblockChest;
import tkcy.tktech.common.metatileentities.multiprimitive.MetaTileEntityMultiblockCrate;
import tkcy.tktech.common.metatileentities.multiprimitive.MetaTileEntityMultiblockTank;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableChestValve;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableCrateValve;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableTankValve;

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

    public static MetaTileEntityModulableChestValve getChestValve(Material material) {
        return getMaterialMetaTileEntity(material, CRATE_MATERIALS.size(),
                TKCYSAMetaTileEntities.MODULABLE_CHEST_VALVES);
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

    public static MetaTileEntityModulableChestValve initChestValve(Material material) {
        return new MetaTileEntityModulableChestValve(getMetaTileEntityId("modulable_chest_valve.", material), material);
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

    private static MetaTileEntityMultiblockChest initModulableChest(Material material, boolean isLarge) {
        String baseResource = isLarge ? "modulable_large_chest." : "modulable_chest.";
        return new MetaTileEntityMultiblockChest(getMetaTileEntityId(baseResource, material), material, isLarge);
    }

    public static MetaTileEntityMultiblockChest initModulableLargeChest(Material material) {
        return initModulableChest(material, true);
    }

    public static MetaTileEntityMultiblockChest initModulableChest(Material material) {
        return initModulableChest(material, false);
    }

    private static MetaTileEntityMultiblockCrate initModulableCrate(Material material, boolean isLarge) {
        String baseResource = isLarge ? "modulable_large_crate." : "modulable_crate.";
        return new MetaTileEntityMultiblockCrate(getMetaTileEntityId(baseResource, material), material, isLarge);
    }

    public static FactoryBlockPattern getLargeTankPattern(RepetitiveSide repetitiveSideMeteTileEntity) {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("  XXX  ", " XXXXX ", "XXXXXXX", "XXXXXXX", "XXXXXXX", " XXXXX ", "  XXX  ")
                .aisle("  XSX  ", " XAAAX ", "XAAAAAX", "XAAAAAX", "XAAAAAX", " XAAAX ", "  XXX  ")
                .aisle("  XXX  ", " XAAAX ", "XAAAAAX", "XAAIAAX", "XAAAAAX", " XAAAX ", "  XXX  ")
                .setRepeatable(repetitiveSideMeteTileEntity.getMinSideLength(),
                        repetitiveSideMeteTileEntity.getMaxSideLength())
                .aisle("  XXX  ", " XXXXX ", "XXXXXXX", "XXXXXXX", "XXXXXXX", " XXXXX ", "  XXX  ");
    }

    public static FactoryBlockPattern getTankPattern(RepetitiveSide repetitiveSideMeteTileEntity) {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XSX", "X X", "XXX")
                .aisle("XXX", "XIX", "XXX")
                .setRepeatable(repetitiveSideMeteTileEntity.getMinSideLength(),
                        repetitiveSideMeteTileEntity.getMaxSideLength())
                .aisle("XXX", "XXX", "XXX");
    }
}
