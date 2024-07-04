package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.unification.material.Materials.StainlessSteel;
import static gregtech.api.unification.material.Materials.Steel;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.*;

import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

public class HarderMachineCasings {

    public static void init() {
        addAssemblingRecipe(Steel, 0);
        addAssemblingRecipe(GalvanizedSteel, 1);
        addAssemblingRecipe(Mangalloy, 2);
        addAssemblingRecipe(StainlessSteel, 3);
        addAssemblingRecipe(BT6, 4);
        addAssemblingRecipe(Talonite, 5);
        addAssemblingRecipe(HastelloyN, 6);

        ulvMachineCasing();
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
}
