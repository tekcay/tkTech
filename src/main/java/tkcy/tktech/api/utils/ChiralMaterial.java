package tkcy.tktech.api.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import gregtech.api.unification.material.Material;

import crafttweaker.annotations.ZenRegister;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.unification.properties.ChiralMaterialProperty;

@ZenClass("mods.tktech.api.utils.ChiralMaterial")
@ZenRegister
@AllArgsConstructor
@Builder
@Getter
public class ChiralMaterial {

    private String materialName;
    private Material racemic, enantiomer1, enantiomer2;
    private int id;
    private boolean useLetterPrefix;
    private BiFunction<String, Integer, Material.Builder> materialBuilderSupplier;

    @ZenMethod
    public static ChiralMaterialBuilder builder() {
        return new ChiralMaterialBuilder();
    }

    private Material material(String materialName) {
        return materialBuilderSupplier.apply(materialName, id++).build();
    }

    private Material material(String materialName, String enantiomer) {
        return material(materialName + "." + enantiomer);
    }

    @ZenMethod
    public void register() {
        ChiralMaterialProperty.ChiralSign chiralSign = useLetterPrefix ? ChiralMaterialProperty.ChiralSign.LETTER :
                ChiralMaterialProperty.ChiralSign.SIGN;

        racemic = material(materialName);
        enantiomer1 = material(materialName, chiralSign.getEnantiomer1Prefix());
        enantiomer2 = material(materialName, chiralSign.getEnantiomer2Prefix());
        ChiralMaterialProperty.addChiralProperty(this);
    }
}
