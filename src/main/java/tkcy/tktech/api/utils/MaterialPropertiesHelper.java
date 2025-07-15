package tkcy.tktech.api.utils;

import org.jetbrains.annotations.NotNull;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.PropertyKey;

public abstract class MaterialPropertiesHelper<T extends IMaterialProperty> implements IMaterialProperty {

    protected abstract PropertyKey<T> getPropertyKey();

    public T getProperty(@NotNull Material material) {
        return material.getProperty(getPropertyKey());
    }
}
