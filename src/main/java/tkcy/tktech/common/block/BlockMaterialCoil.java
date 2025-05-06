package tkcy.tktech.common.block;

import org.jetbrains.annotations.NotNull;

import gregtech.common.blocks.properties.PropertyMaterial;

import tkcy.tktech.api.unification.iconset.TKCYSAMaterialIconType;

public abstract class BlockMaterialCoil extends BlockMaterial {

    public static BlockMaterialCoil create(gregtech.api.unification.material.Material[] materials) {
        return new BlockMaterialCoil() {

            @Override
            public @NotNull PropertyMaterial getVariantProperty() {
                return PropertyMaterial.create("variant", materials);
            }
        };
    }

    private BlockMaterialCoil() {
        super(TKCYSAMaterialIconType.coil);
    }
}
