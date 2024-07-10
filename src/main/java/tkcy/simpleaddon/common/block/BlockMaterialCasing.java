package tkcy.simpleaddon.common.block;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.common.blocks.properties.PropertyMaterial;

import tkcy.simpleaddon.api.unification.iconset.TKCYSAMaterialIconType;

public abstract class BlockMaterialCasing extends BlockMaterial {

    public static BlockMaterialCasing build(gregtech.api.unification.material.Material[] materials) {
        return new BlockMaterialCasing() {

            @Override
            MaterialIconType getMaterialIconType() {
                return TKCYSAMaterialIconType.casing;
            }

            @Override
            public @NotNull PropertyMaterial getVariantProperty() {
                return PropertyMaterial.create("variant", materials);
            }
        };
    }

    private BlockMaterialCasing() {
        super(net.minecraft.block.material.Material.IRON);
    }
}
