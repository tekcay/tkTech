package tkcy.simpleaddon.loaders.recipe.chains;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.PigIron;

public class IronChain {

    public static void init() {

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BandedIron, 5)
                .input(OrePrefix.dust, Coal, 2)
                .output(OrePrefix.ingot, PigIron, 2)
                .duration(20 * 80);

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, Magnetite, 7)
                .input(OrePrefix.dust, Coal, 3)
                .output(OrePrefix.ingot, PigIron, 3)
                .duration(20 * 80);

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BrownLimonite, 5)
                .input(OrePrefix.dust, Coal)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80);

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, YellowLimonite, 5)
                .input(OrePrefix.dust, Coal, 2)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80);

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, PigIron, 3)
                .output(OrePrefix.ingot, Iron)
                .duration(20 * 30);

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, Iron, 1)
                .output(OrePrefix.ingot, WroughtIron)
                .duration(20 * 30);
    }

}
