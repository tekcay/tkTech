package tkcy.simpleaddon.loaders.recipe.alloys;

import static gregtech.api.GTValues.*;
import static gregtech.api.unification.material.Materials.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.IntCircuitIngredient;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.RecipeHelper;

public class DustMixing {

    public static void init() {
        removeShaped();
        removeTinAlloyMixer();
        recipeAddition();
        addTinAlloyMixer();
    }

    private static void recipeAddition() {
        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Copper, 3)
                .input(OrePrefix.dust, Tin, 1)
                .output(OrePrefix.dust, Bronze, 4)
                .EUt(10)
                .duration((int) Bronze.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Steel, 1)
                .input(OrePrefix.dust, Tin, 1)
                .output(OrePrefix.dust, TinAlloy, 2)
                .EUt(10)
                .duration((int) TinAlloy.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Copper)
                .input(OrePrefix.dust, Redstone, 4)
                .output(OrePrefix.dust, RedAlloy)
                .EUt(10)
                .duration((int) RedAlloy.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Bronze, 2)
                .input(OrePrefix.dust, Lead)
                .output(OrePrefix.dust, Potin, 9)
                .EUt(10)
                .duration((int) Potin.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Antimony)
                .input(OrePrefix.dust, Tin, 6)
                .input(OrePrefix.dust, Lead, 3)
                .output(OrePrefix.dust, SolderingAlloy, 10)
                .EUt(10)
                .duration((int) SolderingAlloy.getMass())
                .buildAndRegister();

        TKCYSARecipeMaps.PRIMITIVE_DUST_MIXING.recipeBuilder()
                .input(OrePrefix.dust, Zinc)
                .input(OrePrefix.dust, Copper, 3)
                .output(OrePrefix.dust, Brass, 4)
                .EUt(10)
                .duration((int) Brass.getMass())
                .buildAndRegister();
    }

    private static void removeShaped() {
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.dust, Bronze, 3));
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.dust, Brass, 3));
        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.dust, Potin, 8));
    }

    private static void removeTinAlloyMixer() {
        List<ItemStack> itemStacks = new ArrayList<>();
        itemStacks.add(OreDictUnifier.get(OrePrefix.dust, Iron));
        itemStacks.add(OreDictUnifier.get(OrePrefix.dust, Tin));
        itemStacks.add(getCircuitStack(1));

        Recipe recipe = RecipeMaps.MIXER_RECIPES.findRecipe(VA[ULV], itemStacks, RecipeHelper.emptyFluidStack);
        if (recipe != null) RecipeMaps.MIXER_RECIPES.removeRecipe(recipe);
    }

    private static void addTinAlloyMixer() {
        RecipeMaps.MIXER_RECIPES.recipeBuilder()
                .input(OrePrefix.dust, Steel)
                .input(OrePrefix.dust, Tin)
                .output(OrePrefix.dust, TinAlloy)
                .circuitMeta(1)
                .EUt(VA[ULV])
                .duration((int) TinAlloy.getMass())
                .buildAndRegister();
    }

    private static ItemStack getCircuitStack(int config) {
        return IntCircuitIngredient.getIntegratedCircuit(config);
    }
}
