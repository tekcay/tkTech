package tkcy.tktech.common.block;

import org.jetbrains.annotations.NotNull;

import gregtech.common.blocks.properties.PropertyMaterial;

import tkcy.tktech.api.unification.iconset.TKCYSAMaterialIconType;

public abstract class BlockMaterialCasing extends BlockMaterial {

    public static BlockMaterialCasing create(gregtech.api.unification.material.Material[] materials) {
        return new BlockMaterialCasing() {

            @Override
            public @NotNull PropertyMaterial getVariantProperty() {
                return PropertyMaterial.create("variant", materials);
            }
        };
    }

    private BlockMaterialCasing() {
        super(TKCYSAMaterialIconType.casing);
    }
}
