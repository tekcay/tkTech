package tkcy.simpleaddon.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import tkcy.simpleaddon.common.metatileentities.electric.AssemblingMachine;
import tkcy.simpleaddon.common.metatileentities.electric.Dryer;
import tkcy.simpleaddon.common.metatileentities.electric.Electrolyzer;
import tkcy.simpleaddon.common.metatileentities.multiblockpart.BrickFluidHatch;
import tkcy.simpleaddon.common.metatileentities.multiblockpart.BrickItemBus;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.FluidPrimitiveBlastFurnace;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.GasRelease;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.PrimitiveRoastingOven;
import tkcy.simpleaddon.common.metatileentities.primitive.PrimitiveCasting;

public final class TKCYSAMetaTileEntities {

    public static PrimitiveRoastingOven PRIMITIVE_ROASTING_OVEN;
    public static FluidPrimitiveBlastFurnace FLUID_PRIMITIVE_BLAST_FURNACE;
    public static Electrolyzer ELECTROLYZER;
    public static AssemblingMachine ASSEMBLING_MACHINE;
    public static PrimitiveCasting PRIMITIVE_CASTING;
    public static BrickFluidHatch[] BRICK_FLUID_HATCH = new BrickFluidHatch[2];
    public static BrickItemBus[] BRICK_ITEM_BUS = new BrickItemBus[2];
    public static Dryer DRYER;
    public static GasRelease GAS_RELEASE;

    private TKCYSAMetaTileEntities() {}

    public static void init() {
        PRIMITIVE_ROASTING_OVEN = registerMetaTileEntity(4000,
                new PrimitiveRoastingOven(tkcysa("primitive_roasting_oven")));
        ELECTROLYZER = registerMetaTileEntity(4001, new Electrolyzer(tkcysa("electrolyzer")));
        ASSEMBLING_MACHINE = registerMetaTileEntity(4002, new AssemblingMachine(tkcysa("assembler")));
        FLUID_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(4003,
                new FluidPrimitiveBlastFurnace(tkcysa("fluid_primitive_blast_furnace")));
        PRIMITIVE_CASTING = registerMetaTileEntity(4004, new PrimitiveCasting(tkcysa("primitive_casting")));
        BRICK_FLUID_HATCH[0] = registerMetaTileEntity(4005,
                new BrickFluidHatch(tkcysa("brick_fluid_input_hatch"), false));
        BRICK_FLUID_HATCH[1] = registerMetaTileEntity(4006,
                new BrickFluidHatch(tkcysa("brick_fluid_output_hatch"), true));
        BRICK_ITEM_BUS[0] = registerMetaTileEntity(4007,
                new BrickItemBus(tkcysa("brick_item_input_bus"), false));
        BRICK_ITEM_BUS[1] = registerMetaTileEntity(4008,
                new BrickItemBus(tkcysa("brick_item_output_bus"), true));
        DRYER = registerMetaTileEntity(4009, new Dryer(tkcysa("dryer")));
        GAS_RELEASE = registerMetaTileEntity(4010, new GasRelease(tkcysa("gas_release")));
    }
}
