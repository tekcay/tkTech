package tkcy.tktech.api.unification.properties;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.MaterialProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.TextComponentUtil;

import crafttweaker.annotations.ZenRegister;
import lombok.Getter;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.MaterialPropertiesHelper;

@ZenClass("mods.tktech.api.unification.properties.ToxicMaterialProperty")
@ZenRegister
@Getter
public class ToxicMaterialProperty extends MaterialPropertiesHelper<ToxicMaterialProperty> {

    private ToxicMaterialProperty() {}

    public static ToxicMaterialProperty INSTANCE = new ToxicMaterialProperty();

    @Override
    public void verifyProperty(MaterialProperties properties) {
        if (!BooleanHelper.doesAnyMatch(properties::hasProperty, PropertyKey.DUST, PropertyKey.FLUID)) {
            throw new IllegalStateException(
                    "ChemicalStructureProperty requires atleast DUST property or FLUID property!");
        }
    }

    @Override
    public PropertyKey<ToxicMaterialProperty> getPropertyKey() {
        return TkTechMaterialPropertyKeys.TOXIC;
    }

    @ZenMethod
    public static void addToxicMaterialProperty(Material material) {
        material.getProperties().setProperty(
                TkTechMaterialPropertyKeys.TOXIC,
                new ToxicMaterialProperty());
    }

    public static List<String> createToxicMaterialPropertyTooltip(@NotNull Material material) {
        List<String> tooltips = new ArrayList<>();
        if (material.hasProperty(TkTechMaterialPropertyKeys.TOXIC)) {
            if (material.hasFluid()) {
                tooltips.add(TextComponentUtil
                        .translationWithColor(TextFormatting.RED, I18n.format("tktech.toxic_material_property.tooltip"))
                        .getFormattedText());
            }
        }
        return tooltips;
    }
}
