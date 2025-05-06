package tkcy.tktech.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.blocks.BlockWireCoil.CoilType.NICHROME;
import static tkcy.tktech.api.TkTechValues.SECOND;
import static tkcy.tktech.api.unification.materials.TkTechMaterials.*;
import static tkcy.tktech.api.utils.CollectionHelper.buildMap;
import static tkcy.tktech.modules.PetroChemModule.*;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

@UtilityClass
public class HarderCracking {

    public static void init() {
        transferControllerShapedRecipe();
        addRecipes();
    }

    private static void transferControllerShapedRecipe() {
        ModHandler.removeRecipeByOutput(MetaTileEntities.CRACKER.getStackForm());
        ModHandler.addShapedRecipe(true, "tkcysa_cracking_unit",
                TkTechMetaTileEntities.CRACKING_UNIT.getStackForm(),
                "CEC", "PHP", "CEC",
                'C', MetaBlocks.WIRE_COIL.getItemVariant(NICHROME),
                'E', MetaItems.ELECTRIC_PUMP_HV, 'P',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());
    }

    private static void addRecipes() {
        buildMap(desulfurizedFuels, lightlyHydroCracked).forEach(HarderCracking::addLightlyHydroCrackedRecipes);
        buildMap(desulfurizedFuels, lightlySteamCracked).forEach(HarderCracking::addLightlySteamCrackedRecipes);
        buildMap(desulfurizedFuels, severelyHydroCracked).forEach(HarderCracking::addSeverelyHydroCrackedRecipes);
        buildMap(desulfurizedFuels, severelySteamCracked).forEach(HarderCracking::addSeverelySteamCrackedRecipes);
        buildMap(hydrocarbonMaterials, hydroCrackedHydrocarbonMaterials)
                .forEach(HarderCracking::addModeratelyHydroCrackedRecipes);
        buildMap(hydrocarbonMaterials, steamCrackedHydrocarbonMaterials)
                .forEach(HarderCracking::addModeratelySteamCrackedRecipes);

        addMethaneCrackingRecipes();
        addDistillationRecipes();
    }

    private static void addMethaneCrackingRecipes() {
        // 2 CH4 + H2O -> 2 CO + 3 H2
        addLightlySteamCrackedRecipes(Methane, LightlySteamCrackedMethane);
        // CH4 + H2O -> CO + 3 H2
        addModeratelySteamCrackedRecipes(Methane, ModeratelySteamCrackedMethane);
        // CH4 + 2 H2O -> CO2 + 4 H2
        addSeverelySteamCrackedRecipes(Methane, SeverelySteamCrackedMethane);
    }

    private static void addLightlyHydroCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(4 * SECOND)
                .circuitMeta(1)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(1000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addLightlySteamCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(4 * SECOND)
                .circuitMeta(1)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(2000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelyHydroCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(4 * SECOND)
                .circuitMeta(2)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(2000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelySteamCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(4 * SECOND)
                .circuitMeta(2)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(4000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelyHydroCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(4 * SECOND)
                .circuitMeta(3)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(4000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelySteamCrackedRecipes(Material input, Material output) {
        TkTechRecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(4 * SECOND)
                .circuitMeta(3)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(8000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addDistillationRecipes() {
        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(LightlySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(1000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(1500))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(ModeratelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(2000))
                .fluidOutputs(CarbonMonoxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(3000))
                .duration(120).EUt(120).buildAndRegister();

        DISTILLATION_RECIPES.recipeBuilder()
                .fluidInputs(SeverelySteamCrackedMethane.getFluid(1000))
                .fluidOutputs(DistilledWater.getFluid(4000))
                .fluidOutputs(CarbonDioxide.getFluid(1000))
                .fluidOutputs(Hydrogen.getFluid(4000))
                .duration(120).EUt(120).buildAndRegister();
    }
}
