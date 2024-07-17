package tkcy.simpleaddon.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static gregtech.common.metatileentities.MetaTileEntities.registerSimpleMetaTileEntity;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.machines.ToolLogicMetaTileEntity;
import tkcy.simpleaddon.api.metatileentities.MaterialMetaTileEntity;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.api.utils.TKCYSAUtil;
import tkcy.simpleaddon.common.metatileentities.electric.*;
import tkcy.simpleaddon.common.metatileentities.multiblockpart.BrickFluidHatch;
import tkcy.simpleaddon.common.metatileentities.multiblockpart.BrickItemBus;
import tkcy.simpleaddon.common.metatileentities.multiprimitive.*;
import tkcy.simpleaddon.common.metatileentities.primitive.AnvilMetatileEntity;
import tkcy.simpleaddon.common.metatileentities.primitive.PrimitiveCasting;
import tkcy.simpleaddon.common.metatileentities.steam.SteamDustMixer;
import tkcy.simpleaddon.common.metatileentities.steam.SteamMelter;
import tkcy.simpleaddon.common.metatileentities.storage.MetaTileEntityModulableCrateValve;
import tkcy.simpleaddon.common.metatileentities.storage.MetaTileEntityModulableTankValve;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@UtilityClass
public class TKCYSAMetaTileEntities {

    public static PrimitiveRoastingOven PRIMITIVE_ROASTING_OVEN;
    public static FluidPrimitiveBlastFurnace FLUID_PRIMITIVE_BLAST_FURNACE;
    public static Electrolyzer ELECTROLYZER;
    public static AssemblingMachine ASSEMBLING_MACHINE;
    public static PrimitiveCasting PRIMITIVE_CASTING;
    public static BrickFluidHatch[] BRICK_FLUID_HATCH = new BrickFluidHatch[2];
    public static BrickItemBus[] BRICK_ITEM_BUS = new BrickItemBus[2];
    public static Dryer DRYER;
    public static GasRelease GAS_RELEASE;
    public static AlloyingCrucible PRIMITIVE_ALLOYING_CRUCIBLE;
    public static HydrogenationUnitMTE HYDROGENATION_UNIT_MTE;
    public static CrackingUnitMte CRACKING_UNIT;
    public static SteamDustMixer STEAM_DUST_MIXER;
    public static SteamMelter STEAM_MELTER;
    public static ToolLogicMetaTileEntity PARTS_WORKER_MTE;
    public static SimpleMachineMetaTileEntity[] CLUSTER_MILLS = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] COMPONENT_ASSEMBLER_MTE = new SimpleMachineMetaTileEntity[6];
    public static SimpleMachineMetaTileEntity[] ROLLING_MILL = new SimpleMachineMetaTileEntity[6];

    public static MetaTileEntityModulableTankValve[] MODULABLE_TANK_VALVES = new MetaTileEntityModulableTankValve[StorageModule.TANK_MATERIALS
            .size()];
    public static MetaTileEntityModulableCrateValve[] MODULABLE_CRATE_VALVES = new MetaTileEntityModulableCrateValve[StorageModule.CRATE_MATERIALS
            .size()];

    public static ModulableTank[] MODULABLE_TANKS = new ModulableTank[StorageModule.TANK_MATERIALS.size()];
    public static ModulableTank[] MODULABLE_LARGE_TANKS = new ModulableTank[StorageModule.TANK_MATERIALS.size()];

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

        PRIMITIVE_ALLOYING_CRUCIBLE = registerMetaTileEntity(4011,
                new AlloyingCrucible(tkcysa("alloying_crucible")));

        registerSimpleMetaTileEntity(
                CLUSTER_MILLS, 4012, "cluster_mill",
                TKCYSARecipeMaps.CLUSTER_MILL_RECIPES, Textures.ASSEMBLER_OVERLAY,
                true, TKCYSAUtil::tkcysa, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(
                COMPONENT_ASSEMBLER_MTE, 4018, "component_assembler",
                TKCYSARecipeMaps.COMPONENT_ASSEMBING, Textures.ASSEMBLER_OVERLAY,
                true, TKCYSAUtil::tkcysa, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(ROLLING_MILL, 4024, "rolling_mill", TKCYSARecipeMaps.ROLLING_RECIPES,
                TKCYSATextures.ROLLING_MILL_OVERLAY, true, TKCYSAUtil::tkcysa, null);

        HYDROGENATION_UNIT_MTE = registerMetaTileEntity(4100, new HydrogenationUnitMTE(tkcysa("hydrogenation_unit")));
        CRACKING_UNIT = registerMetaTileEntity(4101, new CrackingUnitMte(tkcysa("cracking_unit")));
        STEAM_DUST_MIXER = registerMetaTileEntity(4102, new SteamDustMixer(tkcysa("steam_dust_mixer")));
        STEAM_MELTER = registerMetaTileEntity(4103, new SteamMelter(tkcysa("steam_melter")));
        PARTS_WORKER_MTE = registerMetaTileEntity(4104,
                new AnvilMetatileEntity(tkcysa("anvil"), TKCYSARecipeMaps.ANVIL_RECIPES));

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_TANKS, 4200,
                StorageModule::initModulableTank);
        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_LARGE_TANKS, 4210,
                StorageModule::initModulableLargeTank);
        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_TANK_VALVES, 4220,
                StorageModule::initTankValve);

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_CRATE_VALVES,
                4230, StorageModule::initCrateValve);
    }
}
