package tkcy.simpleaddon.loaders.recipe;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.cableGtSingle;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.BASIC_CIRCUIT_BOARD;

import net.minecraft.init.Blocks;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.metatileentities.cleanroom.AdvancedCleanroomType;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.common.block.BlockStrippedWood;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public class RecipesTests {

    public static void register() {
        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.DamascusSteel)
                .fluidInputs(Materials.Epichlorohydrin.getFluid(1000))
                .output(OrePrefix.dust, Materials.Cadmium)
                .EUt(30)
                .duration(500)
                .cleanroom(AdvancedCleanroomType.ARGON_CLEANROOM)
                .buildAndRegister();

        RecipeMaps.CHEMICAL_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Materials.Cadmium)
                .fluidInputs(Materials.Epichlorohydrin.getFluid(1000))
                .output(OrePrefix.dust, Materials.Cadmium)
                .EUt(30)
                .duration(500)
                .cleanroom(AdvancedCleanroomType.NITROGEN_CLEANROOM)
                .buildAndRegister();

        TKCYSARecipeMaps.BASIC_ELECTRONIC_RECIPES.recipeBuilder()
                .input(RESISTOR, 2)
                .input(BASIC_CIRCUIT_BOARD)
                .input(VACUUM_TUBE)
                .input(plate, Steel)
                .input(cableGtSingle, RedAlloy, 3)
                .fluidInputs(Tin.getFluid(288))
                .tool(ToolsModule.GtTool.SOLDERING_IRON)
                .toolUses(7)
                .hideDuration()
                .useAndDisplayEnergy(false)
                .output(ELECTRONIC_CIRCUIT_LV)
                .buildAndRegister();

        /**
         * To add as a mixin in {@link gregtech.loaders.recipe.WoodRecipeLoader} ?
         */
        TKCYSARecipeMaps.WOOD_WORKSHOP_RECIPES.recipeBuilder()
                .inputBlockInWorld(Blocks.LOG)
                .outputBlockInWorld(
                        TKCYSAMetaBlocks.STRIPPED_WOOD.getState(BlockStrippedWood.StrippedWoodType.OAK_STRIPPED))
                .tool(ToolsModule.GtTool.AXE)
                .toolUses(7)
                .output(OrePrefix.dust, Wood)
                .hideDuration()
                .useAndDisplayEnergy(false)
                .buildAndRegister();
    }
}
