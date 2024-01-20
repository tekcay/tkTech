package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.material.Materials.Steel;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

import tkcy.simpleaddon.api.recipes.RecipeRemovalHelper;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class HarderMachineCasings {

    public static void init() {
        removeAllRecipes(0);
        removeAllRecipes(1);
        removeAllRecipes(2);
        removeAllRecipes(3);
        removeAllRecipes(4);
        removeAllRecipes(5);
        removeAllRecipes(6);

        addAssemblingRecipe(Steel, 0);
        addAssemblingRecipe(GalvanizedSteel, 1);
        addAssemblingRecipe(Mangalloy, 2);
        addAssemblingRecipe(StainlessSteel, 3);
        addAssemblingRecipe(BT6, 4);
        addAssemblingRecipe(Talonite, 5);
        addAssemblingRecipe(HastelloyN, 6);

        ulvMachineCasing();
    }

    /**
     * Remove all GTCEu recipes.
     * 
     * @param tier as tier 0 corresponds to ULV tier etc.
     */
    private static void removeAllRecipes(int tier) {
        removeAssemblerRecipe(tier);
        removeShapedRecipe(tier);
    }

    private static void ulvMachineCasing() {
        ModHandler.addShapedRecipe("machine_casing_ulv", getMachineCasingItemStack(0),
                "XXX", "XwX", "XXX", 'X', new UnificationEntry(OrePrefix.plate, Steel));

        TKCYSARecipeMaps.ADVANCED_ASSEMBLING.recipeBuilder()
                .input(OrePrefix.plate, Steel, 8)
                .fluidInputs(Materials.Tin.getFluid(GTValues.L))
                .outputs(getMachineCasingItemStack(0))
                .circuitMeta(8)
                .EUt((int) ((GTValues.V[0]) / 2))
                .duration(20 * 10)
                .buildAndRegister();
    }

    private static void addAssemblingRecipe(Material input, int tier) {
        TKCYSARecipeMaps.ADVANCED_ASSEMBLING.recipeBuilder()
                .input(OrePrefix.plate, input, 8)
                .fluidInputs(Materials.SolderingAlloy.getFluid(GTValues.L))
                .outputs(getMachineCasingItemStack(tier))
                .circuitMeta(8)
                .EUt((int) ((GTValues.V[tier]) / 2))
                .duration(20 * 10)
                .buildAndRegister();
    }

    private static ItemStack getMachineCasingItemStack(int tier) {
        return MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.values()[tier]);
    }

    private static void removeAssemblerRecipe(int tier) {
        RecipeRemovalHelper.removeRecipeByOutputs(RecipeMaps.ASSEMBLER_RECIPES, getMachineCasingItemStack(tier));
    }

    private static void removeShapedRecipe(int tier) {
        ModHandler.removeRecipeByOutput(getMachineCasingItemStack(tier));
    }
}
