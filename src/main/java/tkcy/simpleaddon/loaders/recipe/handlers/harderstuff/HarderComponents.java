package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.metatileentities.MetaTileEntities.HULL;
import static tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps.COMPONENT_ASSEMBING;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.GalvanizedSteel;
import static tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix.curvedPlate;
import static tkcy.simpleaddon.common.item.TKCYSAMetaItems.*;
import static tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities.COMPONENT_ASSEMBLER_MTE;
import static tkcy.simpleaddon.modules.ComponentsModule.*;

import net.minecraftforge.fluids.FluidStack;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;

public class HarderComponents {

    public static void init() {
        addULVComponentsShapedRecipes();
        addComponentAssemblerRecipe();
        motors();
        robotsArms();
        pumpsAndConveyors();
        pistons();
        fieldGenerators();
        emitters();
        sensors();
        fluidRegulators();
    }

    private static void addULVComponentsShapedRecipes() {
        ModHandler.addShapedRecipe("electric_motor_ulv", ELECTRIC_MOTOR_ULV.getStackForm(), "CWR", "WMW", "VWC",
                'C', new UnificationEntry(cableGtSingle, Lead),
                'W', new UnificationEntry(wireGtSingle, RedAlloy),
                'R', new UnificationEntry(stick, TinAlloy),
                'V', new UnificationEntry(curvedPlate, TinAlloy),
                'M', new UnificationEntry(stick, SteelMagnetic));

        ModHandler.addShapedRecipe("electric_pump_ulv", ELECTRIC_PUMP_ULV.getStackForm(), "SRC", "dXw", "VMS",
                'S', new UnificationEntry(screw, TinAlloy),
                'X', new UnificationEntry(rotor, TinAlloy),
                'R', new UnificationEntry(ring, Rubber),
                'V', new UnificationEntry(curvedPlate, TinAlloy),
                'C', new UnificationEntry(cableGtSingle, Lead),
                'M', ELECTRIC_MOTOR_ULV.getStackForm());

        ModHandler.addShapedRecipe("conveyor_module_ulv", CONVEYOR_MODULE_ULV.getStackForm(), "RRR", "MCM", "RRR",
                'R', new UnificationEntry(plate, Rubber),
                'C', new UnificationEntry(cableGtSingle, Lead),
                'M', ELECTRIC_MOTOR_ULV.getStackForm());

        ModHandler.addShapedRecipe(true, "electric_piston_ulv", ELECTRIC_PISTON_ULV.getStackForm(), "SPP", "dRR", "SMG",
                'P', new UnificationEntry(plate, Steel),
                'S', new UnificationEntry(screw, TinAlloy),
                'R', new UnificationEntry(stick, TinAlloy),
                'G', new UnificationEntry(gearSmall, TinAlloy),
                'M', ELECTRIC_MOTOR_ULV.getStackForm());
    }

    private static void addComponentAssemblerRecipe() {
        ModHandler.addShapedRecipe(true, "component_assembler_lv", COMPONENT_ASSEMBLER_MTE[1].getStackForm(),
                "PCP", "AHA", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.ULV),
                'W', new UnificationEntry(cableGtSingle, Lead),
                'H', HULL[1].getStackForm(),
                'A', ROBOT_ARM_ULV.getStackForm(),
                'P', ELECTRIC_PISTON_ULV.getStackForm());

        ModHandler.addShapedRecipe(true, "component_assembler_mv", COMPONENT_ASSEMBLER_MTE[2].getStackForm(),
                "PCP", "AHA", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.LV),
                'W', new UnificationEntry(cableGtDouble, Tin),
                'H', HULL[2].getStackForm(),
                'A', ROBOT_ARM_LV.getStackForm(),
                'P', ELECTRIC_PISTON_LV.getStackForm());

        ModHandler.addShapedRecipe(true, "component_assembler_hv", COMPONENT_ASSEMBLER_MTE[3].getStackForm(),
                "PCP", "AHA", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.MV),
                'W', new UnificationEntry(cableGtQuadruple, Copper),
                'H', HULL[3].getStackForm(),
                'A', ROBOT_ARM_MV.getStackForm(),
                'P', ELECTRIC_PISTON_MV.getStackForm());

        ModHandler.addShapedRecipe(true, "component_assembler_ev", COMPONENT_ASSEMBLER_MTE[4].getStackForm(),
                "PCP", "AHA", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.HV),
                'W', new UnificationEntry(cableGtOctal, Gold),
                'H', HULL[4].getStackForm(),
                'A', ROBOT_ARM_HV.getStackForm(),
                'P', ELECTRIC_PISTON_HV.getStackForm());

        ModHandler.addShapedRecipe(true, "component_assembler_iv", COMPONENT_ASSEMBLER_MTE[5].getStackForm(),
                "PCP", "AHA", "WCW",
                'C', new UnificationEntry(circuit, MarkerMaterials.Tier.EV),
                'W', new UnificationEntry(cableGtHex, Aluminium),
                'H', HULL[5].getStackForm(),
                'A', ROBOT_ARM_EV.getStackForm(),
                'P', ELECTRIC_PISTON_EV.getStackForm());
    }

    private static final FluidStack input = Materials.SolderingAlloy.getFluid(72);

    private static void motors() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Tin, 2)
                .input(stick, GalvanizedSteel, 2)
                .input(stick, SteelMagnetic)
                .input(wireGtSingle, Copper, 4)
                .input(curvedPlate, GalvanizedSteel)
                .fluidInputs(input)
                .outputs(ELECTRIC_MOTOR_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Copper, 2)
                .input(stick, Aluminium, 2)
                .input(stick, SteelMagnetic)
                .input(wireGtDouble, Cupronickel, 4)
                .input(curvedPlate, Aluminium)
                .fluidInputs(input)
                .outputs(ELECTRIC_MOTOR_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtDouble, Silver, 2)
                .input(stick, StainlessSteel, 2)
                .input(stick, SteelMagnetic)
                .input(wireGtDouble, Electrum, 4)
                .input(curvedPlate, StainlessSteel)
                .fluidInputs(input)
                .outputs(ELECTRIC_MOTOR_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtDouble, Aluminium, 2)
                .input(stick, Titanium, 2)
                .input(stick, NeodymiumMagnetic)
                .input(wireGtDouble, Kanthal, 4)
                .input(curvedPlate, Titanium)
                .fluidInputs(input)
                .outputs(ELECTRIC_MOTOR_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtDouble, Tungsten, 2)
                .input(stick, TungstenSteel, 2)
                .input(stick, NeodymiumMagnetic)
                .input(wireGtDouble, Graphene, 4)
                .input(curvedPlate, Tungsten)
                .fluidInputs(input)
                .outputs(ELECTRIC_MOTOR_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    public static void pistons() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, GalvanizedSteel, 2)
                .input(cableGtSingle, Tin, 2)
                .input(plateDouble, GalvanizedSteel, 3)
                .input(gearSmall, GalvanizedSteel)
                .inputs(ELECTRIC_MOTOR_LV.getStackForm())
                .fluidInputs(input)
                .outputs(ELECTRIC_PISTON_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Aluminium, 2)
                .input(cableGtSingle, Copper, 2)
                .input(plateDouble, Aluminium, 3)
                .input(gearSmall, Aluminium)
                .inputs(ELECTRIC_MOTOR_MV.getStackForm())
                .fluidInputs(input)
                .outputs(ELECTRIC_PISTON_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, StainlessSteel, 2)
                .input(cableGtSingle, Gold, 2)
                .input(plateDouble, StainlessSteel, 3)
                .input(gearSmall, StainlessSteel)
                .fluidInputs(input)
                .inputs(ELECTRIC_MOTOR_HV.getStackForm())
                .outputs(ELECTRIC_PISTON_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Titanium, 2)
                .input(cableGtSingle, Aluminium, 2)
                .input(plateDouble, Titanium, 3)
                .input(gearSmall, Titanium)
                .fluidInputs(input)
                .inputs(ELECTRIC_MOTOR_EV.getStackForm())
                .outputs(ELECTRIC_PISTON_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, TungstenSteel, 2)
                .input(cableGtSingle, Tungsten, 2)
                .input(plateDouble, TungstenSteel, 3)
                .input(gearSmall, TungstenSteel)
                .inputs(ELECTRIC_MOTOR_IV.getStackForm())
                .fluidInputs(input)
                .outputs(ELECTRIC_PISTON_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void pumpsAndConveyors() {
        for (Material material : getLvAcceptedRubberMaterials()) {
            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Tin)
                    .input(pipeNormalFluid, GalvanizedSteel)
                    .input(screw, TinAlloy)
                    .input(rotor, TinAlloy)
                    .input(curvedPlate, GalvanizedSteel)
                    .input(ring, material, 2)
                    .inputs(ELECTRIC_MOTOR_LV.getStackForm())
                    .fluidInputs(input)
                    .outputs(ELECTRIC_PUMP_LV.getStackForm())
                    .duration(100).EUt(VA[LV]).buildAndRegister();

            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Tin)
                    .inputs(ELECTRIC_MOTOR_LV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(input)
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_LV.getStackForm())
                    .duration(100).EUt(VA[LV]).buildAndRegister();
        }

        for (Material material : getMvAcceptedRubberMaterials()) {
            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Copper)
                    .input(pipeNormalFluid, Aluminium)
                    .input(screw, Aluminium)
                    .input(rotor, Aluminium)
                    .input(ring, material, 2)
                    .input(curvedPlate, Aluminium)
                    .fluidInputs(input)
                    .inputs(ELECTRIC_MOTOR_MV.getStackForm())
                    .outputs(ELECTRIC_PUMP_MV.getStackForm())
                    .duration(100).EUt(VA[MV]).buildAndRegister();

            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Copper)
                    .inputs(ELECTRIC_MOTOR_MV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(input)
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_MV.getStackForm())
                    .duration(100).EUt(VA[MV]).buildAndRegister();
        }

        for (Material material : getHvAcceptedRubberMaterials()) {
            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Gold)
                    .input(pipeNormalFluid, StainlessSteel)
                    .input(screw, GalvanizedSteel)
                    .input(rotor, GalvanizedSteel)
                    .input(ring, material, 2)
                    .input(curvedPlate, StainlessSteel)
                    .fluidInputs(input)
                    .inputs(ELECTRIC_MOTOR_HV.getStackForm())
                    .outputs(ELECTRIC_PUMP_HV.getStackForm())
                    .duration(100).EUt(VA[HV]).buildAndRegister();

            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Gold)
                    .inputs(ELECTRIC_MOTOR_HV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(input)
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_HV.getStackForm())
                    .duration(100).EUt(VA[HV]).buildAndRegister();
        }

        for (Material material : getEvAcceptedRubberMaterials()) {
            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Aluminium)
                    .input(pipeNormalFluid, Titanium)
                    .input(screw, StainlessSteel)
                    .input(rotor, StainlessSteel)
                    .input(ring, material, 2)
                    .input(curvedPlate, Titanium)
                    .fluidInputs(input)
                    .inputs(ELECTRIC_MOTOR_EV.getStackForm())
                    .outputs(ELECTRIC_PUMP_EV.getStackForm())
                    .duration(100).EUt(VA[EV]).buildAndRegister();

            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Aluminium)
                    .inputs(ELECTRIC_MOTOR_EV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(input)
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_EV.getStackForm())
                    .duration(100).EUt(VA[EV]).buildAndRegister();
        }

        for (Material material : getIvAcceptedRubberMaterials()) {
            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Tungsten)
                    .input(pipeNormalFluid, TungstenSteel)
                    .input(screw, TungstenSteel)
                    .input(rotor, TungstenSteel)
                    .input(ring, material, 2)
                    .input(curvedPlate, TungstenCarbide)
                    .fluidInputs(input)
                    .inputs(ELECTRIC_MOTOR_IV.getStackForm())
                    .outputs(ELECTRIC_PUMP_IV.getStackForm())
                    .duration(100).EUt(VA[IV]).buildAndRegister();

            COMPONENT_ASSEMBING.recipeBuilder()
                    .input(cableGtSingle, Tungsten)
                    .inputs(ELECTRIC_MOTOR_IV.getStackForm(2))
                    .input(plate, material, 6)
                    .fluidInputs(input)
                    .circuitMeta(1)
                    .outputs(CONVEYOR_MODULE_IV.getStackForm())
                    .duration(100).EUt(VA[IV]).buildAndRegister();
        }
    }

    public static void robotsArms() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Tin, 3)
                .input(stick, GalvanizedSteel, 2)
                .inputs(ELECTRIC_MOTOR_LV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_LV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.LV)
                .fluidInputs(input)
                .outputs(ROBOT_ARM_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Copper, 3)
                .input(stick, Aluminium, 2)
                .inputs(ELECTRIC_MOTOR_MV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_MV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.MV)
                .fluidInputs(input)
                .outputs(ROBOT_ARM_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Gold, 3)
                .input(stick, StainlessSteel, 2)
                .inputs(ELECTRIC_MOTOR_HV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_HV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV)
                .fluidInputs(input)
                .outputs(ROBOT_ARM_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Aluminium, 3)
                .input(stick, Titanium, 2)
                .inputs(ELECTRIC_MOTOR_EV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_EV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.EV)
                .fluidInputs(input)
                .outputs(ROBOT_ARM_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(cableGtSingle, Tungsten, 3)
                .input(stick, TungstenSteel, 2)
                .inputs(ELECTRIC_MOTOR_IV.getStackForm(2))
                .inputs(ELECTRIC_PISTON_IV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.IV)
                .fluidInputs(input)
                .outputs(ROBOT_ARM_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void fieldGenerators() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(gem, EnderPearl)
                .input(plateDouble, GalvanizedSteel, 2)
                .input(circuit, MarkerMaterials.Tier.LV, 2)
                .input(wireGtQuadruple, ManganesePhosphide, 4)
                .input(wireFine, Copper, 4)
                .fluidInputs(input)
                .outputs(FIELD_GENERATOR_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(gem, EnderEye)
                .input(plateDouble, Aluminium, 3)
                .input(circuit, MarkerMaterials.Tier.MV, 2)
                .input(wireGtQuadruple, MagnesiumDiboride, 4)
                .input(wireFine, Gold, 4)
                .fluidInputs(input)
                .outputs(FIELD_GENERATOR_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(QUANTUM_EYE)
                .input(plateDouble, StainlessSteel, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .input(wireGtQuadruple, MercuryBariumCalciumCuprate, 4).fluidInputs(input)
                .input(wireFine, Gold, 4)
                .outputs(FIELD_GENERATOR_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(gem, NetherStar)
                .input(plateDouble, Titanium, 5)
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(wireGtQuadruple, UraniumTriplatinum, 4).fluidInputs(input)
                .input(wireFine, Platinum, 4)
                .outputs(FIELD_GENERATOR_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(QUANTUM_STAR)
                .input(plateDouble, TungstenSteel, 6)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(wireGtQuadruple, SamariumIronArsenicOxide, 4)
                .input(wireFine, Palladium, 4)
                .fluidInputs(input)
                .outputs(FIELD_GENERATOR_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void sensors() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Brass)
                .input(curvedPlate, Steel, 4)
                .input(circuit, MarkerMaterials.Tier.LV, 2)
                .input(gem, Quartzite)
                .fluidInputs(input)
                .outputs(SENSOR_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Electrum)
                .input(curvedPlate, Aluminium, 4)
                .input(circuit, MarkerMaterials.Tier.MV, 2)
                .input(gemFlawless, Diamond)
                .fluidInputs(input)
                .outputs(SENSOR_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Chrome)
                .input(curvedPlate, StainlessSteel, 4)
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .input(gem, Emerald)
                .fluidInputs(input)
                .outputs(SENSOR_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Platinum)
                .input(curvedPlate, Titanium, 4)
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(lens, Platinum)
                .fluidInputs(input)
                .outputs(SENSOR_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Iridium)
                .input(curvedPlate, TungstenSteel, 4)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(lens, Palladium)
                .fluidInputs(input)
                .outputs(SENSOR_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void emitters() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Brass, 4)
                .input(cableGtSingle, Tin, 2)
                .input(circuit, MarkerMaterials.Tier.LV, 2)
                .input(gem, Quartzite)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(EMITTER_LV.getStackForm())
                .duration(100).EUt(VA[LV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Electrum, 4)
                .input(cableGtDouble, Copper, 2)
                .input(circuit, MarkerMaterials.Tier.MV, 2)
                .input(gemFlawless, Diamond)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(EMITTER_MV.getStackForm())
                .duration(100).EUt(VA[MV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Chrome, 4)
                .input(cableGtQuadruple, Gold, 2)
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .input(gem, Emerald)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(EMITTER_HV.getStackForm())
                .duration(100).EUt(VA[HV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Platinum, 4)
                .input(cableGtOctal, Aluminium, 2)
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .input(lens, Platinum)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(EMITTER_EV.getStackForm())
                .duration(100).EUt(VA[EV]).buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .input(stick, Iridium, 4)
                .input(cableGtHex, Tungsten, 2)
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .input(lens, Palladium)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(EMITTER_IV.getStackForm())
                .duration(100).EUt(VA[IV]).buildAndRegister();
    }

    private static void fluidRegulators() {
        COMPONENT_ASSEMBING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_LV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.LV, 2)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(FLUID_REGULATOR_LV.getStackForm())
                .EUt(VA[LV])
                .duration(400)
                .buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_MV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.MV, 2)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(FLUID_REGULATOR_MV.getStackForm())
                .EUt(VA[MV])
                .duration(350)
                .buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_HV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.HV, 2)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(FLUID_REGULATOR_HV.getStackForm())
                .EUt(VA[HV])
                .duration(300)
                .buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_EV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.EV, 2)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(FLUID_REGULATOR_EV.getStackForm())
                .EUt(VA[EV])
                .duration(250)
                .buildAndRegister();

        COMPONENT_ASSEMBING.recipeBuilder()
                .inputs(ELECTRIC_PUMP_IV.getStackForm())
                .input(circuit, MarkerMaterials.Tier.IV, 2)
                .circuitMeta(1)
                .fluidInputs(input)
                .outputs(FLUID_REGULATOR_IV.getStackForm())
                .EUt(VA[IV])
                .duration(200)
                .buildAndRegister();
    }
}
