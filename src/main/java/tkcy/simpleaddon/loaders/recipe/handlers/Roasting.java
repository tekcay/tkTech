package tkcy.simpleaddon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.dust;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.*;

import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;

import akka.japi.Function;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class Roasting {

    public static void init() {
        chalcopyrite();

        registerRecipe(Chalcocite, 1, CupricOxide, 2, 2000);
        registerRecipe(Pyrite, 2, BandedIron, 1, 2000);
        registerRecipe(Realgar, 1, Arsenopyrite, 2, 2000);
        registerRecipe(Tetrahedrite, 1, RoastedTetrahedrite, 1, 2000);
        registerRecipe(Stibnite, 1, AntimonyTrioxide, 1, 1500);
        registerRecipe(Cobaltite, 1, RoastedCobaltite, 1, 1000);
        registerRecipe(Sphalerite, 1, Zincite, 1, 1000);
        registerRecipe(Pentlandite, 1, Garnierite, 1, 2000);
        registerRecipe(Galena, 1, RoastedGalena, 1, 1000);
        registerRecipe(Kesterite, 1, RoastedKesterite, 1, 4000);
        registerRecipe(Stannite, 1, RoastedStannite, 1, 4000);
        registerRecipe(Arsenopyrite, 1, RoastedArsenopyrite, 1, 4000);
        registerRecipe(Bornite, 1, RoastedBornite, 1, 4000);
        registerRecipe(Molybdenite, 1, MolybdenumTrioxide, 1, 2000);
    }

    private static void registerRecipe(Material material1, int amount1, Material material2, int amount2,
                                       int sulfurDioxideAmount) {
        if (material2 != Zincite && material2 != Garnierite && material2 != MolybdenumTrioxide) {
            centrifuge(material2);
        }

        TKCYSARecipeMaps.PRIMITIVE_ROASTING.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .duration(20 * 20)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Air.getFluid(sulfurDioxideAmount * 2))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, material1, amount1)
                .output(dust, material2, amount2)
                .fluidInputs(Oxygen.getFluid(sulfurDioxideAmount))
                .fluidOutputs(SulfurDioxide.getFluid(sulfurDioxideAmount))
                .duration(20 * 30)
                .EUt(80)
                .buildAndRegister();
    }

    private static void chalcopyrite() {
        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Chalcopyrite)
                .input(dust, SiliconDioxide)
                .output(dust, RoastedChalcopyrite)
                .fluidInputs(Air.getFluid(2000 * 2))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .duration(20 * 100)
                .EUt(80)
                .buildAndRegister();

        RecipeMaps.BLAST_RECIPES.recipeBuilder()
                .input(dust, Chalcopyrite)
                .output(dust, SiliconDioxide)
                .fluidInputs(Oxygen.getFluid(4000))
                .fluidOutputs(SulfurDioxide.getFluid(2000))
                .duration(20 * 50)
                .EUt(80)
                .buildAndRegister();
    }

    public static List<ItemStack> getStacksFromMaterialComposition(Material material) {
        return material.getMaterialComponents().stream()
                .map(materialStack -> OreDictUnifier.get(dust, materialStack.material, (int) materialStack.amount))
                .collect(Collectors.toList());
    }

    private static final Function<Material, Long> con = material -> material
            .getMaterialComponents()
            .stream()
            .mapToLong(materialStack -> materialStack.amount)
            .sum();

    public static int getAmountComponentsSum(Material material) throws Exception {
        return Math.toIntExact((con.apply(material)));
    }

    private static void centrifuge(Material material) {
        try {
            RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                    .input(dust, material, getAmountComponentsSum(material))
                    .outputs(getStacksFromMaterialComposition(material))
                    .duration(100)
                    .EUt(20)
                    .buildAndRegister();
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
