package tkcy.tktech.api.unification.properties;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.LocalizationUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.ChiralMaterial;
import tkcy.tktech.mixins.gregtech.MixinMaterial;

/**
 * Used to add chiral sign to material orePrefix items e.g. dusts in localized name. This is done via
 * MaterialProperty checking in
 * the mixin version of {@link Material#getLocalizedName()} in {@link MixinMaterial}.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChiralMaterialProperty implements IExtraMaterialProperty<ChiralMaterialProperty> {

    private static final String baseLangKey = "tktech.chiral_material_property.";

    public static ChiralMaterialProperty INSTANCE = new ChiralMaterialProperty();

    private String chiralPrefix;
    private String baseName;

    public static void addChiralProperty(@NotNull Material material, String chiralPrefix, Material racemicMaterial) {
        INSTANCE.addMaterialProperty(material,
                new ChiralMaterialProperty(chiralPrefix, racemicMaterial.getUnlocalizedName()));
    }

    public static void addChiralProperty(@NotNull Material racemicMaterial, @NotNull Material enantiomer1,
                                         @NotNull Material enantiomer2, ChiralSign chiralSign) {
        ChiralMaterialProperty property = INSTANCE;
        property.addMaterialProperty(racemicMaterial,
                new ChiralMaterialProperty(chiralSign.racemicPrefix, racemicMaterial.getUnlocalizedName()));
        property.addMaterialProperty(enantiomer1,
                new ChiralMaterialProperty(chiralSign.enantiomer1Prefix, racemicMaterial.getUnlocalizedName()));
        property.addMaterialProperty(enantiomer2,
                new ChiralMaterialProperty(chiralSign.enantiomer2Prefix, racemicMaterial.getUnlocalizedName()));
    }

    public static void addChiralProperty(ChiralMaterial chiralMaterial) {
        addChiralProperty(
                chiralMaterial.getRacemic(),
                chiralMaterial.getEnantiomer1(),
                chiralMaterial.getEnantiomer2(),
                chiralMaterial.isUseLetterPrefix() ? ChiralSign.LETTER : ChiralSign.SIGN);
    }

    @Override
    public PropertyKey<ChiralMaterialProperty> getPropertyKey() {
        return TkTechMaterialPropertyKeys.CHIRAL;
    }

    @Override
    public void verifyProperty(MaterialProperties properties) {
        if (!BooleanHelper.doesAnyMatch(properties::hasProperty, PropertyKey.DUST, PropertyKey.FLUID)) {
            throw new IllegalStateException(
                    "ChiralMaterialProperty requires atleast DUST property or FLUID property!");
        }
    }

    public String applyPrefix(String displayName) {
        return getPrefix() + displayName;
    }

    public String getPrefix() {
        String key = baseLangKey + this.chiralPrefix;
        return LocalizationUtils.format(key);
    }

    @AllArgsConstructor
    @Getter
    public enum ChiralSign {

        LETTER("RACEMIC", "R", "S"),
        SIGN("RACEMIC", "PLUS", "MINUS");

        private final String racemicPrefix, enantiomer1Prefix, enantiomer2Prefix;
    }
}
