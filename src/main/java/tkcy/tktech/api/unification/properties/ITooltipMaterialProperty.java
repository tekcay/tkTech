package tkcy.tktech.api.unification.properties;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.PropertyKey;

public interface ITooltipMaterialProperty<T extends ITooltipMaterialProperty<T>> extends IExtraMaterialProperty<T> {

    List<PropertyKey<? extends ITooltipMaterialProperty<?>>> TOOLTIP_MATERIAL_PROPERTIES = new ArrayList<>();

    static void addTooltips(@NotNull Material material, List<String> tooltips) {
        ITooltipMaterialProperty.TOOLTIP_MATERIAL_PROPERTIES.stream()
                .filter(material::hasProperty)
                .map(material::getProperty)
                .map(iTooltipMaterialProperty -> iTooltipMaterialProperty.createTooltip(material))
                .forEach(tooltips::addAll);
    }

    List<String> createTooltip(@NotNull Material material);
}
