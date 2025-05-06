package tkcy.tktech.common.block;

import org.jetbrains.annotations.NotNull;

import gregtech.common.blocks.properties.PropertyMaterial;

import tkcy.tktech.api.unification.iconset.TKCYSAMaterialIconType;

public abstract class BlockMaterialWall extends BlockMaterial {

    public static BlockMaterialWall create(gregtech.api.unification.material.Material[] materials) {
        return new BlockMaterialWall() {

            @Override
            public @NotNull PropertyMaterial getVariantProperty() {
                return PropertyMaterial.create("variant", materials);
            }
        };
    }

    private BlockMaterialWall() {
        super(TKCYSAMaterialIconType.wall);
    }
}
