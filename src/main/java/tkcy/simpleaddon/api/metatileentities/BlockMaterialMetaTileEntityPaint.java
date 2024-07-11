package tkcy.simpleaddon.api.metatileentities;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.material.Material;

/**
 * To remind to override {@link MetaTileEntity#getPaintingColorForRendering()} by
 * {@code return getPaintingColorForRendering(Material} material)
 */
public interface BlockMaterialMetaTileEntityPaint {

    default int getPaintingColorForRendering(Material material) {
        return material.getMaterialRGB();
    }
}
