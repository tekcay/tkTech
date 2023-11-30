package tkcy.simpleaddon.loaders.recipe.chains;

import static gregtech.api.unification.material.Materials.*;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.PigIron;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.ore.OrePrefix;

public class IronChain {

    public static void init() {
        // Ore
        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BandedIron, 5)
                .input(OrePrefix.gem, Coal, 2)
                .output(OrePrefix.ingot, PigIron, 2)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, Magnetite, 7)
                .input(OrePrefix.gem, Coal, 3)
                .output(OrePrefix.ingot, PigIron, 3)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BrownLimonite, 5)
                .input(OrePrefix.gem, Coal)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, YellowLimonite, 5)
                .input(OrePrefix.gem, Coal, 2)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BandedIron, 5)
                .input(OrePrefix.gem, Coke, 2)
                .output(OrePrefix.ingot, PigIron, 2)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, Magnetite, 7)
                .input(OrePrefix.gem, Coke, 3)
                .output(OrePrefix.ingot, PigIron, 3)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, BrownLimonite, 5)
                .input(OrePrefix.gem, Coke)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ore, YellowLimonite, 5)
                .input(OrePrefix.gem, Coke, 2)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 40)
                .buildAndRegister();

        // Crushed
        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, BandedIron, 5)
                .input(OrePrefix.gem, Coal, 2)
                .output(OrePrefix.ingot, PigIron, 2)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, Magnetite, 7)
                .input(OrePrefix.gem, Coal, 3)
                .output(OrePrefix.ingot, PigIron, 3)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, BrownLimonite, 5)
                .input(OrePrefix.gem, Coal)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, YellowLimonite, 5)
                .input(OrePrefix.gem, Coal, 2)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 80)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, BandedIron, 5)
                .input(OrePrefix.gem, Coke, 2)
                .output(OrePrefix.ingot, PigIron, 2)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, Magnetite, 7)
                .input(OrePrefix.gem, Coke, 3)
                .output(OrePrefix.ingot, PigIron, 3)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, BrownLimonite, 5)
                .input(OrePrefix.gem, Coke)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 40)
                .buildAndRegister();

        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.crushed, YellowLimonite, 5)
                .input(OrePrefix.gem, Coke, 2)
                .output(OrePrefix.ingot, PigIron)
                .duration(20 * 40)
                .buildAndRegister();

        // PigIron to Iron
        RecipeMaps.PRIMITIVE_BLAST_FURNACE_RECIPES.recipeBuilder()
                .input(OrePrefix.ingot, PigIron, 2)
                .output(OrePrefix.ingot, Iron)
                .duration(20 * 30)
                .buildAndRegister();
    }
}
