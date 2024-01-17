package tkcy.simpleaddon.api.utils;

import static gregtech.api.unification.material.Materials.Iron;

import java.util.function.Function;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

import tkcy.simpleaddon.TekCaySimpleAddon;

public final class TKCYSAUtil {

    public static @NotNull ResourceLocation tkcysa(@NotNull String path) {
        return new ResourceLocation(TekCaySimpleAddon.MODID, path);
    }

    public static int getFluidAmountFromOrePrefix(@NotNull OrePrefix orePrefix) {
        return (int) (GTValues.L * orePrefix.getMaterialAmount(Iron) / GTValues.M);
    }

    public static FluidStack getFluidStackFromOrePrefix(@NotNull OrePrefix orePrefix, @NotNull Material material) {
        return material.getFluid(getFluidAmountFromOrePrefix(orePrefix));
    }

    public static FluidStack generateFluidStackFromMaterialStack(@NotNull MaterialStack materialStack) {
        return new FluidStack(materialStack.material.getFluid(), (int) materialStack.amount);
    }

    public static FluidStack generateFluidStackFromMaterialStack(@NotNull MaterialStack materialStack,
                                                                 int multiplier) {
        long fluidAmount = materialStack.amount * multiplier;
        return new FluidStack(materialStack.material.getFluid(), (int) fluidAmount);
    }

    public static int getAmountComponentsSum(@NotNull Material material) {
        return Math.toIntExact(getAmountComponentsSum.apply(material));
    }

    private static final Function<Material, Long> getAmountComponentsSum = material -> material
            .getMaterialComponents()
            .stream()
            .mapToLong(materialStack -> materialStack.amount)
            .sum();

    private TKCYSAUtil() {}
}
