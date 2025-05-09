package tkcy.tktech.api.utils;

import static gregtech.api.unification.material.Materials.Iron;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.tktech.TkTech;

public final class TkTechUtil {

    public static @NotNull ResourceLocation tktech(@NotNull String path) {
        return new ResourceLocation(TkTech.MODID, path);
    }

    public static int getFluidAmountFromOrePrefix(@NotNull OrePrefix orePrefix) {
        return (int) (GTValues.L * orePrefix.getMaterialAmount(Iron) / GTValues.M);
    }

    public static FluidStack getFluidStackFromOrePrefix(@NotNull OrePrefix orePrefix, @NotNull Material material) {
        return material.getFluid(getFluidAmountFromOrePrefix(orePrefix));
    }
}
