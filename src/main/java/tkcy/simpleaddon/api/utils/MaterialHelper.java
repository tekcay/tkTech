package tkcy.simpleaddon.api.utils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

public class MaterialHelper {

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

    public static int getAmountComponentsSum(List<MaterialStack> materialStacks) {
        return materialStacks.stream()
                .mapToLong(material -> material.amount)
                .mapToInt(Math::toIntExact)
                .sum();
    }

    private static final Function<Material, Long> getAmountComponentsSum = material -> material
            .getMaterialComponents()
            .stream()
            .mapToLong(materialStack -> materialStack.amount)
            .sum();

    /**
     * Totally unrealistic, but hey it's minecraft
     */
    private static int getFluidTemperature(@NotNull MaterialStack materialStack) {
        return materialStack.material.hasFluid() ?
                (int) (materialStack.material.getFluid().getTemperature() * materialStack.amount) : 0;
    }

    /**
     * Sets the melting point/fluid temperature via its chemical composition of a material.
     * Does not compute gases such as oxygen etc.
     * <br>
     * Won't do anything if the material does not have a fluid.
     */
    public static void setWeightMp(@NotNull Material material) {
        if (!material.hasFluid()) return;

        List<MaterialStack> components = material.getMaterialComponents();
        List<MaterialStack> filteredComponents = removeGasMaterials(material);

        if (components.size() == filteredComponents.size()) {
            setWeightMp(material, components);
        } else setWeightMp(material, filteredComponents);
    }

    private static List<MaterialStack> removeGasMaterials(@NotNull Material material) {
        return material.getMaterialComponents().stream()
                .filter(materialStack -> materialStack.material.hasFluid())
                .filter(materialStack -> !materialStack.material.getFluid().isGaseous())
                .collect(Collectors.toList());
    }

    private static void setWeightMp(Material material, @NotNull List<MaterialStack> materialStacks) {
        material.getFluid().setTemperature(materialStacks
                .stream()
                .mapToInt(MaterialHelper::getFluidTemperature)
                .sum() / getAmountComponentsSum(materialStacks));
    }

    public static Function<Material, Integer> getMaterialFluidTemperature = material -> material.getFluid()
            .getTemperature();

    public static boolean isMaterialFluidTemperatureColder(Material material1, Material material2) {
        return getMaterialFluidTemperature.apply(material1) <= getMaterialFluidTemperature.apply(material2);
    }

    public static int getOrePrefixFluidAmount(OrePrefix orePrefix) {
        return (int) (GTValues.L * orePrefix.getMaterialAmount(Materials.Steel) / GTValues.M);
    }

    public static Predicate<Material> hasPlateFlag = material -> material.hasFlag(MaterialFlags.GENERATE_PLATE);

    public static final Predicate<Material> isMaterialFluidTemperatureDefault = material -> material.hasFluid() &&
            material.getFluid().getTemperature() != 300;

    public static int getCarbonAmountInMaterial(@NotNull Material material) {
        return material.getMaterialComponents()
                .stream()
                .filter(materialStack -> materialStack.material == Materials.Carbon)
                .mapToLong(materialStack -> materialStack.amount)
                .mapToInt(Math::toIntExact)
                .findFirst()
                .orElse(0);
    }

    @Nullable
    public static Material getMaterialFromFluid(FluidStack fluidStack) {
        return GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(Material::hasFluid)
                .filter(material -> fluidStack.isFluidEqual(material.getFluid(1)))
                .findFirst()
                .orElse(null);
    }
}
