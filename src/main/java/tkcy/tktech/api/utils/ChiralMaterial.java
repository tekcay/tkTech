package tkcy.tktech.api.utils;

import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.stack.MaterialStack;

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

    private String baseMaterialName;
    private String modid;
    private Material racemic, enantiomer1, enantiomer2;
    private int baseId;
    private boolean useLetterPrefix;
    private MaterialStack[] components;

    @ZenMethod
    public static ChiralMaterialBuilder chiralMaterialBuilder() {
        return new ChiralMaterialBuilder();
    }

    @ZenMethod
    public static ChiralMaterialBuilder chiralMaterialBuilder(String modid, String baseMaterialName, int baseId) {
        return ChiralMaterial.builder()
                .modid(modid)
                .baseMaterialName(baseMaterialName)
                .baseId(baseId);
    }

    private Material material(String materialName) {
        return new Material.Builder(baseId++, new ResourceLocation(modid, materialName))
                .colorAverage()
                .dust()
                .components(components)
                .build();
    }

    private Material material(String materialName, String enantiomer) {
        return material(materialName + "." + enantiomer);
    }

    @ZenMethod
    public void register() {
        ChiralMaterialProperty.ChiralSign chiralSign = useLetterPrefix ? ChiralMaterialProperty.ChiralSign.LETTER :
                ChiralMaterialProperty.ChiralSign.SIGN;

        racemic = material(baseMaterialName);
        enantiomer1 = material(baseMaterialName, chiralSign.getEnantiomer1Prefix());
        enantiomer2 = material(baseMaterialName, chiralSign.getEnantiomer2Prefix());
        ChiralMaterialProperty.addChiralProperty(this);
    }
}
