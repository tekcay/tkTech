package tkcy.simpleaddon.api.metatileentities;

import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;

import tkcy.simpleaddon.api.utils.TKCYSAUtil;

public interface MaterialMetaTileEntity {

    static ResourceLocation getMetaTileEntityId(String baseMetaTileEntity, Material material) {
        return TKCYSAUtil.tkcysa(baseMetaTileEntity + material.getName());
    }

    Material getMaterial();
}
