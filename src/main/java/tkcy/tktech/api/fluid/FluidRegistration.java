package tkcy.tktech.api.fluid;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fluids.Fluid;

import org.jetbrains.annotations.NotNull;

import gregtech.api.fluids.FluidBuilder;
import gregtech.api.fluids.GTFluid;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;

import crafttweaker.annotations.ZenRegister;
import lombok.experimental.UtilityClass;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import tkcy.tktech.TkTech;

@ZenClass("mods.tktech.api.fluid.FluidRegistration")
@ZenRegister
@UtilityClass
public class FluidRegistration {

    public static final List<Material> MATERIALS_TO_ADD_DISTILLED = new ArrayList<>();

    @ZenMethod
    public static void addDistilledFluid(@NotNull Material material) {
        MATERIALS_TO_ADD_DISTILLED.add(material);
    }

    public static void register() {
        FluidRegistration.addDistilledFluid(Materials.Benzene);
        MATERIALS_TO_ADD_DISTILLED.forEach(FluidRegistration::buildDistilledFluid);
    }

    private static void buildDistilledFluid(@NotNull Material material) {
        if (!material.hasFluid()) return;
        Fluid fluid = material.getFluid();
        if (fluid instanceof GTFluid gtFluid) {
            FluidBuilder builder = new FluidBuilder();
            if (!gtFluid.getAttributes().isEmpty()) {
                gtFluid.getAttributes().forEach(builder::attribute);
            }
            builder.temperature(material.getFluid().getTemperature()).build(TkTech.MODID, material,
                    TkTechFluidStorageKeys.DISTILLED);
        }
    }
}
