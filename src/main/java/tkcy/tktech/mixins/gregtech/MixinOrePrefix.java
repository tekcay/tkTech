package tkcy.tktech.mixins.gregtech;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.tktech.api.unification.properties.ChiralMaterialProperty;

@Mixin(value = OrePrefix.class, remap = false)
public abstract class MixinOrePrefix {

    @ModifyReturnValue(method = "getLocalNameForItem", at = @At(value = "RETURN"))
    private String addPrefix(String original, @NotNull Material material) {
        ChiralMaterialProperty property = ChiralMaterialProperty.INSTANCE.getProperty(material);
        return original;
        // if (property == null) return original;
        // return property.applyPrefix(original);
    }
}
