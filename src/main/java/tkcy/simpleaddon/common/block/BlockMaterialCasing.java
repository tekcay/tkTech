package tkcy.simpleaddon.common.block;

import org.jetbrains.annotations.NotNull;

import gregtech.common.blocks.properties.PropertyMaterial;

import tkcy.simpleaddon.api.unification.iconset.TKCYSAMaterialIconType;

public abstract class BlockMaterialCasing extends BlockMaterial {

    public static BlockMaterialCasing create(gregtech.api.unification.material.Material[] materials) {
        return new BlockMaterialCasing("casing") {

            @Override
            public @NotNull PropertyMaterial getVariantProperty() {
                return PropertyMaterial.create("variant", materials);
            }
        };
    }

    private BlockMaterialCasing(String translationKey) {
        super(TKCYSAMaterialIconType.casing, translationKey);
    }
}
