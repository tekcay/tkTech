package tkcy.simpleaddon.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import tkcy.simpleaddon.common.metatileentities.electric.AssemblingMachine;
import tkcy.simpleaddon.common.metatileentities.electric.Electrolyzer;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.FluidPrimitiveBlastFurnace;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.PrimitiveRoastingOven;

public final class TKCYSAMetaTileEntities {

    public static PrimitiveRoastingOven PRIMITIVE_ROASTING_OVEN;
    public static FluidPrimitiveBlastFurnace FLUID_PRIMITIVE_BLAST_FURNACE;
    public static Electrolyzer ELECTROLYZER;
    public static AssemblingMachine ASSEMBLING_MACHINE;

    private TKCYSAMetaTileEntities() {}

    public static void init() {
        PRIMITIVE_ROASTING_OVEN = registerMetaTileEntity(4000,
                new PrimitiveRoastingOven(tkcysa("primitive_roasting_oven")));
        ELECTROLYZER = registerMetaTileEntity(4001, new Electrolyzer(tkcysa("electrolyzer")));
        ASSEMBLING_MACHINE = registerMetaTileEntity(4002, new AssemblingMachine(tkcysa("assembler")));
        FLUID_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(4003,
                new FluidPrimitiveBlastFurnace(tkcysa("fluid_primitive_blast_furnace")));
    }
}
