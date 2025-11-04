package tkcy.tktech.mixins.gregtech;

import java.util.List;
import java.util.function.Supplier;

import net.minecraftforge.fluids.Fluid;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import gregtech.api.fluids.FluidState;
import gregtech.api.unification.material.Material;
import gregtech.api.util.FluidTooltipUtil;

import tkcy.tktech.api.unification.properties.TkTechMaterialPropertyKeys;
import tkcy.tktech.api.unification.properties.ToxicMaterialProperty;

@Mixin(value = FluidTooltipUtil.class, remap = false)
public abstract class MixinFluidTooltipUtil {

    @ModifyReturnValue(method = "createFluidTooltip", at = @At(value = "RETURN"))
    private static Supplier<List<String>> addExtraFluidTooltips2(Supplier<List<String>> supplier,
                                                                 @Nullable Material material, @NotNull Fluid fluid,
                                                                 @NotNull FluidState fluidState) {
        if (material != null && material.hasProperty(TkTechMaterialPropertyKeys.TOXIC)) {
            List<String> tooltips = supplier.get();
            tooltips.addAll(ToxicMaterialProperty.createToxicMaterialPropertyTooltip(material));
            return () -> tooltips;
        } else return supplier;
    }
}
