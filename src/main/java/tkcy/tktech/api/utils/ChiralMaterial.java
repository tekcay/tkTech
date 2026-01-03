package tkcy.tktech.api.utils;

import net.minecraft.util.ResourceLocation;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.info.MaterialIconSet;
import gregtech.api.unification.stack.MaterialStack;

import crafttweaker.annotations.ZenRegister;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.unification.iconset.TkTechMaterialIconSets;
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

    private Material material(String materialName, MaterialIconSet materialIconSet) {
        return new Material.Builder(baseId++, new ResourceLocation(modid, materialName))
                .colorAverage()
                .dust()
                .iconSet(materialIconSet)
                .components(components)
                .build();
    }

    private Material material(String materialName, String enantiomer, MaterialIconSet materialIconSet) {
        return material(materialName + "." + enantiomer, materialIconSet);
    }

    @ZenMethod
    public void register() {
        ChiralMaterialProperty.ChiralSign chiralSign = useLetterPrefix ? ChiralMaterialProperty.ChiralSign.LETTER :
                ChiralMaterialProperty.ChiralSign.SIGN;

        racemic = material(baseMaterialName, TkTechMaterialIconSets.RACEMIC);
        enantiomer1 = material(baseMaterialName, chiralSign.getEnantiomer1Prefix(),
                TkTechMaterialIconSets.ENANTIOMER_1);
        enantiomer2 = material(baseMaterialName, chiralSign.getEnantiomer2Prefix(),
                TkTechMaterialIconSets.ENANTIOMER_2);

        ChiralMaterialProperty.addChiralProperty(this);
    }
}
