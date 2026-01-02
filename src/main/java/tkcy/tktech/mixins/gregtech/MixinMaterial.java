package tkcy.tktech.mixins.gregtech;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.IMaterialProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.util.LocalizationUtils;

import tkcy.tktech.api.unification.properties.ChiralMaterialProperty;
import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;

@Mixin(value = Material.class, remap = false)
public abstract class MixinMaterial {

    @Shadow
    public abstract <T extends IMaterialProperty> T getProperty(PropertyKey<T> key);

    @ModifyReturnValue(method = "getLocalizedName", at = @At(value = "RETURN"))
    private String appendChiralityPrefix(String original) {
        ChiralMaterialProperty property = getProperty(TkTechMaterialPropertyKeys.CHIRAL);
        if (property == null) return original;
        original = LocalizationUtils.format(property.getBaseName());
        return property.applyPrefix(original);
    }
}
