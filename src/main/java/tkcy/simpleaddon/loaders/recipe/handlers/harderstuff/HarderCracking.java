package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.GTValues.*;
import static gregtech.api.recipes.RecipeMaps.*;
import static gregtech.api.unification.material.Materials.*;
import static gregtech.common.blocks.BlockWireCoil.CoilType.CUPRONICKEL;
import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;
import static tkcy.simpleaddon.api.utils.CollectionHelper.buildMap;
import static tkcy.simpleaddon.modules.PetroChemModule.*;

import java.util.Arrays;
import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.RecipeHelper;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

public class HarderCracking {

    private static final List<RecipeMap<?>> chemRecipeMaps = Arrays.asList(CHEMICAL_RECIPES, LARGE_CHEMICAL_RECIPES);

    public static void init() {
        chemReactorCrackingRecipesRemoval();
        GTRecipeHandler.removeAllRecipes(CRACKING_RECIPES);
        transferControllerShapedRecipe();
        addRecipes();
    }

    private static void transferControllerShapedRecipe() {
        ModHandler.removeRecipeByOutput(MetaTileEntities.CRACKER.getStackForm());
        ModHandler.addShapedRecipe(true, "tkcysa_cracking_unit",
                TKCYSAMetaTileEntities.CRACKING_UNIT.getStackForm(),
                "CEC", "PHP", "CEC",
                'C', MetaBlocks.WIRE_COIL.getItemVariant(CUPRONICKEL),
                'E', MetaItems.ELECTRIC_PUMP_HV, 'P',
                new UnificationEntry(OrePrefix.circuit, MarkerMaterials.Tier.HV),
                'H', MetaTileEntities.HULL[GTValues.HV].getStackForm());
    }

    private static void addRecipes() {
        buildMap(desulfurizedFuels, lightlyHydroCracked).forEach(HarderCracking::addLightlyHydroCrackedRecipes);
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
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(1000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addLightlySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(VA[MV])
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(2000))
                .coil(BlockWireCoil.CoilType.KANTHAL)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelyHydroCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(2000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addModeratelySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(300)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(4000))
                .coil(BlockWireCoil.CoilType.NICHROME)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelyHydroCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Hydrogen.getFluid(4000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void addSeverelySteamCrackedRecipes(Material input, Material output) {
        TKCYSARecipeMaps.CRACKING.recipeBuilder()
                .EUt(600)
                .duration(SECOND)
                .fluidInputs(input.getFluid(500))
                .fluidInputs(Steam.getFluid(8000))
                .coil(BlockWireCoil.CoilType.RTM_ALLOY)
                .fluidOutputs(output.getFluid(500))
                .buildAndRegister();
    }

    private static void chemReactorCrackingRecipesRemoval() {
        desulfurizedFuels.forEach(HarderCracking::removeLightlyAndModeraltelyCrackingRecipes);
        hydrocarbonMaterials.forEach(HarderCracking::removeModeratelyCrackingRecipes);

        RecipeHelper.tryToRemoveRecipeWithCircuitConfig(CHEMICAL_RECIPES, VA[HV], 1, Water.getFluid(2000),
                Methane.getFluid(2000));
    }

    private static void removeLightlyAndModeraltelyCrackingRecipes(Material material) {
        for (RecipeMap<?> recipeMap : chemRecipeMaps) {
            // Lightly
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 1, Hydrogen.getFluid(1000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 1, Steam.getFluid(1000),
                    material.getFluid(1000));

            // Severely
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Hydrogen.getFluid(3000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 3, Steam.getFluid(1000),
                    material.getFluid(1000));
        }
    }

    private static void removeModeratelyCrackingRecipes(Material material) {
        for (RecipeMap<?> recipeMap : chemRecipeMaps) {

            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Hydrogen.getFluid(2000),
                    material.getFluid(500));
            RecipeHelper.tryToRemoveRecipeWithCircuitConfig(recipeMap, VA[LV], 2, Steam.getFluid(1000),
                    material.getFluid(1000));
        }
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
