package tkcy.tktech.api.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import java.util.List;
import java.util.function.Function;

import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Material;

import tkcy.tktech.api.utils.TKCYSAUtil;

public interface MaterialMetaTileEntity {

    Material getMaterial();

    static ResourceLocation getMetaTileEntityId(String baseMetaTileEntity, Material material) {
        return TKCYSAUtil.tkcysa(baseMetaTileEntity + material.getName());
    }

    static <T extends MetaTileEntity> void registerMaterialMetaTileEntity(List<Material> materials,
                                                                          T[] materialMetaTileEntities,
                                                                          int startId,
                                                                          Function<Material, T> mteSupplier) {
        int index = 0;
        for (Material material : materials) {
            materialMetaTileEntities[index] = registerMetaTileEntity(startId, mteSupplier.apply(material));
            index++;
            startId++;
        }
    }
}
