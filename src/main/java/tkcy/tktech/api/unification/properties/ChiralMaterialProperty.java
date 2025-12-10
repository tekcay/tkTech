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

@AllArgsConstructor
@NoArgsConstructor
public class ChiralMaterialProperty implements IExtraMaterialProperty<ChiralMaterialProperty> {

    private static final String baseLangKey = "tktech.chiral_material_property.";

    public static ChiralMaterialProperty INSTANCE = new ChiralMaterialProperty();

    @Getter
    private ChiralMaterialProperty.Optical optical;

    public static void addChiralProperty(@NotNull Material material, ChiralMaterialProperty.Optical optical) {
        INSTANCE.addMaterialProperty(material, new ChiralMaterialProperty(optical));
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
        String key = baseLangKey + this.optical;
        return LocalizationUtils.format(key);
    }

    public enum Optical {
        RACEMIC,
        S,
        R,
        PLUS,
        MINUS
    }
}
