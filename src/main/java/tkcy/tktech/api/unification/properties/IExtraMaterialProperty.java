package tkcy.tktech.api.unification.properties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public interface IExtraMaterialProperty<T extends IMaterialProperty> extends IMaterialProperty {

    PropertyKey<T> getPropertyKey();

    @Nullable
    default T getProperty(@NotNull Material material) {
        if (!material.hasProperty(getPropertyKey())) return null;
        return material.getProperty(getPropertyKey());
    }

    default void addMaterialProperty(Material material, T materialProperty) {
        material.getProperties().setProperty(
                getPropertyKey(),
                materialProperty);
    }
}
