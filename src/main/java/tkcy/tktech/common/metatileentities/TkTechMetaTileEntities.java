package tkcy.tktech.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static gregtech.common.metatileentities.MetaTileEntities.registerSimpleMetaTileEntity;
import static tkcy.tktech.api.utils.TkTechUtil.tktech;

import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.machines.ToolLogicMetaTileEntity;
import tkcy.tktech.api.metatileentities.MaterialMetaTileEntity;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.render.TkTechTextures;
import tkcy.tktech.api.utils.TkTechUtil;
import tkcy.tktech.common.metatileentities.electric.*;
import tkcy.tktech.common.metatileentities.multiblockpart.BrickFluidHatch;
import tkcy.tktech.common.metatileentities.multiblockpart.BrickItemBus;
import tkcy.tktech.common.metatileentities.multiblockpart.MTeBrickMufflerHatch;
import tkcy.tktech.common.metatileentities.multiprimitive.*;
import tkcy.tktech.common.metatileentities.primitive.*;
import tkcy.tktech.common.metatileentities.steam.SteamDustMixer;
import tkcy.tktech.common.metatileentities.steam.SteamMelter;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableChestValve;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableCrateValve;
import tkcy.tktech.common.metatileentities.storage.MetaTileEntityModulableTankValve;
import tkcy.tktech.modules.storagemodule.StorageModule;

@UtilityClass
public class TkTechMetaTileEntities {

    public static PrimitiveRoastingOven PRIMITIVE_ROASTING_OVEN;
    public static FluidPrimitiveBlastFurnace FLUID_PRIMITIVE_BLAST_FURNACE;
    public static MTeElectrolyzer ELECTROLYZER;
    public static MTeAssemblingMachine ASSEMBLING_MACHINE;
    public static MTePrimitiveCasting PRIMITIVE_CASTING;
    public static BrickFluidHatch[] BRICK_FLUID_HATCH = new BrickFluidHatch[2];
    public static BrickItemBus[] BRICK_ITEM_BUS = new BrickItemBus[2];
    public static MTeDryer DRYER;
    public static GasRelease GAS_RELEASE_BRICK;
    public static GasRelease GAS_RELEASE_STEEL;
    public static GasRelease GAS_RELEASE_STAINLESS_STEEL;
    public static AlloyingCrucible PRIMITIVE_ALLOYING_CRUCIBLE;
    public static MTeHydrogenationUnit HYDROGENATION_UNIT;
    public static MTeCrackingUnit CRACKING_UNIT;
    public static SteamDustMixer STEAM_DUST_MIXER;
    public static SteamMelter STEAM_MELTER;
    public static ToolLogicMetaTileEntity PARTS_WORKER_MTE;
    public static MTeAdvancedCleanroom ADVANCED_CLEANROOM;
    public static MTeBasicElectronic BASIC_ELECTRONIC;
    public static MTeWoodWorkshop WOOD_WORKSHOP;
    public static MTePrimitiveBath PRIMITIVE_BATH;
    public static MTeChemicalBench CHEMICAL_BENCH;
    public static MTeBrickMufflerHatch BRICK_MUFFLER_HATCH;
    public static SimpleMachineMetaTileEntity[] CLUSTER_MILLS = new SimpleMachineMetaTileEntity[5];
    public static SimpleMachineMetaTileEntity[] COMPONENT_ASSEMBLERS = new SimpleMachineMetaTileEntity[6];
    public static SimpleMachineMetaTileEntity[] ROLLING_MILLS = new SimpleMachineMetaTileEntity[6];

    public static MetaTileEntityModulableTankValve[] MODULABLE_TANK_VALVES = new MetaTileEntityModulableTankValve[StorageModule.TANK_MATERIALS
            .size()];
    public static MetaTileEntityModulableCrateValve[] MODULABLE_CRATE_VALVES = new MetaTileEntityModulableCrateValve[StorageModule.CRATE_MATERIALS
            .size()];
    public static MetaTileEntityModulableChestValve[] MODULABLE_CHEST_VALVES = new MetaTileEntityModulableChestValve[StorageModule.CRATE_MATERIALS
            .size()];

    public static MetaTileEntityMultiblockTank[] MODULABLE_TANKS = new MetaTileEntityMultiblockTank[StorageModule.TANK_MATERIALS
            .size()];
    public static MetaTileEntityMultiblockTank[] MODULABLE_LARGE_TANKS = new MetaTileEntityMultiblockTank[StorageModule.TANK_MATERIALS
            .size()];
    public static MetaTileEntityMultiblockCrate[] MODULABLE_CRATES = new MetaTileEntityMultiblockCrate[StorageModule.CRATE_MATERIALS
            .size()];
    public static MetaTileEntityMultiblockCrate[] MODULABLE_LARGE_CRATES = new MetaTileEntityMultiblockCrate[StorageModule.CRATE_MATERIALS
            .size()];
    public static MetaTileEntityMultiblockChest[] MODULABLE_CHESTS = new MetaTileEntityMultiblockChest[StorageModule.CRATE_MATERIALS
            .size()];
    public static MetaTileEntityMultiblockChest[] MODULABLE_LARGE_CHESTS = new MetaTileEntityMultiblockChest[StorageModule.CRATE_MATERIALS
            .size()];

    public static void init() {
        PRIMITIVE_ROASTING_OVEN = registerMetaTileEntity(4000,
                new PrimitiveRoastingOven(tktech("primitive_roasting_oven")));

        ELECTROLYZER = registerMetaTileEntity(4001, new MTeElectrolyzer(tktech("electrolyzer")));

        ASSEMBLING_MACHINE = registerMetaTileEntity(4002, new MTeAssemblingMachine(tktech("assembler")));

        FLUID_PRIMITIVE_BLAST_FURNACE = registerMetaTileEntity(4003,
                new FluidPrimitiveBlastFurnace(tktech("fluid_primitive_blast_furnace")));

        PRIMITIVE_CASTING = registerMetaTileEntity(4004, new MTePrimitiveCasting(tktech("primitive_casting")));

        BRICK_FLUID_HATCH[0] = registerMetaTileEntity(4005,
                new BrickFluidHatch(tktech("brick_fluid_input_hatch"), false));

        BRICK_FLUID_HATCH[1] = registerMetaTileEntity(4006,
                new BrickFluidHatch(tktech("brick_fluid_output_hatch"), true));

        BRICK_ITEM_BUS[0] = registerMetaTileEntity(4007,
                new BrickItemBus(tktech("brick_item_input_bus"), false));

        BRICK_ITEM_BUS[1] = registerMetaTileEntity(4008,
                new BrickItemBus(tktech("brick_item_output_bus"), true));

        DRYER = registerMetaTileEntity(4009, new MTeDryer(tktech("dryer")));

        // FREE ID 4010

        PRIMITIVE_ALLOYING_CRUCIBLE = registerMetaTileEntity(4011,
                new AlloyingCrucible(tktech("alloying_crucible")));

        registerSimpleMetaTileEntity(
                CLUSTER_MILLS, 4012, "cluster_mill",
                TkTechRecipeMaps.CLUSTER_MILL_RECIPES, Textures.ASSEMBLER_OVERLAY,
                true, TkTechUtil::tktech, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(
                COMPONENT_ASSEMBLERS, 4018, "component_assembler",
                TkTechRecipeMaps.COMPONENT_ASSEMBING, Textures.ASSEMBLER_OVERLAY,
                true, TkTechUtil::tktech, GTUtility.hvCappedTankSizeFunction);

        registerSimpleMetaTileEntity(ROLLING_MILLS, 4024, "rolling_mill", TkTechRecipeMaps.ROLLING_RECIPES,
                TkTechTextures.ROLLING_MILL_OVERLAY, true, TkTechUtil::tktech, null);

        HYDROGENATION_UNIT = registerMetaTileEntity(4100, new MTeHydrogenationUnit(tktech("hydrogenation_unit")));
        CRACKING_UNIT = registerMetaTileEntity(4101, new MTeCrackingUnit(tktech("cracking_unit")));
        STEAM_DUST_MIXER = registerMetaTileEntity(4102, new SteamDustMixer(tktech("steam_dust_mixer")));
        STEAM_MELTER = registerMetaTileEntity(4103, new SteamMelter(tktech("steam_melter")));
        PARTS_WORKER_MTE = registerMetaTileEntity(4104,
                new MTeAnvil(tktech("anvil")));
        ADVANCED_CLEANROOM = registerMetaTileEntity(4105,
                new MTeAdvancedCleanroom(tktech("advanced_cleanroom")));
        BASIC_ELECTRONIC = registerMetaTileEntity(4106, new MTeBasicElectronic(tktech("basic_electronic")));
        WOOD_WORKSHOP = registerMetaTileEntity(4107, new MTeWoodWorkshop(tktech("wood_workshop")));
        PRIMITIVE_BATH = registerMetaTileEntity(4108, new MTePrimitiveBath(tktech("primitive_bath")));
        CHEMICAL_BENCH = registerMetaTileEntity(4109, new MTeChemicalBench(tktech("chemical_bench")));

        GAS_RELEASE_BRICK = registerMetaTileEntity(4110,
                new GasRelease(tktech("gas_release_brick"),
                        MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PRIMITIVE_BRICKS),
                        Textures.PRIMITIVE_BRICKS, true));
        GAS_RELEASE_STEEL = registerMetaTileEntity(4111,
                new GasRelease(tktech("gas_release_steel"),
                        MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID),
                        Textures.SOLID_STEEL_CASING, false));
        GAS_RELEASE_STAINLESS_STEEL = registerMetaTileEntity(4112,
                new GasRelease(tktech("gas_release_stainless_steel"),
                        MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN),
                        Textures.CLEAN_STAINLESS_STEEL_CASING, false));

        BRICK_MUFFLER_HATCH = registerMetaTileEntity(4113, new MTeBrickMufflerHatch(tktech("brick_muffler_hatch")));

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_TANKS, 4200,
                StorageModule::initModulableTank);
        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_LARGE_TANKS, 4210,
                StorageModule::initModulableLargeTank);
        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.TANK_MATERIALS, MODULABLE_TANK_VALVES, 4220,
                StorageModule::initTankValve);

        /*
         * MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_CRATE_VALVES,
         * 4230, StorageModule::initCrateValve);
         */

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_CRATES,
                4240, StorageModule::initModulableCrate);

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_LARGE_CRATES,
                4250, StorageModule::initModulableLargeCrate);

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_CHESTS,
                4260, StorageModule::initModulableChest);

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_LARGE_CHESTS,
                4270, StorageModule::initModulableLargeChest);

        MaterialMetaTileEntity.registerMaterialMetaTileEntity(StorageModule.CRATE_MATERIALS, MODULABLE_CHEST_VALVES,
                4280, StorageModule::initChestValve);
    }
}
