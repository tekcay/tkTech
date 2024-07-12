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
import tkcy.simpleaddon.common.metatileentities.multiblockpart.MetaTileEntityModulableTankValve;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.ModulableTank;

@UtilityClass
public class StorageModule {

    public @interface StorageModulable {}

    public static final List<Material> TANK_MATERIALS = new ArrayList<>();

    static {
        TANK_MATERIALS.add(Materials.TreatedWood);
        TANK_MATERIALS.add(Materials.Steel);
        TANK_MATERIALS.add(Materials.StainlessSteel);
        TANK_MATERIALS.add(Materials.TungstenCarbide);
        TANK_MATERIALS.add(TKCYSAMaterials.GalvanizedSteel);
    }

    public static MetaTileEntityModulableTankValve getValve(Material material) {
        return get(material, TKCYSAMetaTileEntities.MODULABLE_TANK_VALVES);
    }

    @Nullable
    private static <T extends MaterialMetaTileEntity> T get(Material material, T[] metaTileEntities) {
        return IntStream.range(0, TANK_MATERIALS.size())
                .boxed()
                .map(i -> metaTileEntities[i])
                .filter(mte -> mte.getMaterial() == material)
                .findFirst()
                .orElse(null);
    }

    public static MetaTileEntityModulableTankValve initValve(Material material) {
        return new MetaTileEntityModulableTankValve(getMetaTileEntityId("modulable_tank_valve", material), material);
    }

    public static ModulableTank initModulableLargeTank(Material material) {
        return initModulableTank(material, true);
    }

    public static ModulableTank initModulableTank(Material material) {
        return initModulableTank(material, false);
    }

    private static ModulableTank initModulableTank(Material material, boolean isLarge) {
        String baseResource = isLarge ? "modulable_large_tank." : "modulable_tank.";
        return new ModulableTank(getMetaTileEntityId(baseResource, material), material, isLarge);
    }
}
