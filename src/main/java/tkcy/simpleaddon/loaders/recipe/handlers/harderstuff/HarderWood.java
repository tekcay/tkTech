package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.unification.material.Materials.Wood;
import static tkcy.simpleaddon.common.block.BlockStrippedWood.StrippedWoodType.*;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockGregPlanks;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.common.block.BlockStrippedWood;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

/**
 * Pun intended
 */
@UtilityClass
public class HarderWood {

    /**
     * To add as a mixin in {@link gregtech.loaders.recipe.WoodRecipeLoader} ?
     */
    public static void register() {
        lk(new ItemStack(Blocks.LOG, 1), OAK_STRIPPED, new ItemStack(Blocks.PLANKS, 1));
        lk(new ItemStack(Blocks.LOG, 1, 1), SPRUCE_STRIPPED, new ItemStack(Blocks.PLANKS, 1, 1));
        lk(new ItemStack(Blocks.LOG, 1, 2), BIRCH_STRIPPED, new ItemStack(Blocks.PLANKS, 1, 2));
        lk(new ItemStack(Blocks.LOG, 1, 3), JUNGLE_STRIPPED, new ItemStack(Blocks.PLANKS, 1, 3));
        lk(new ItemStack(Blocks.LOG2, 1), ACACIA_STRIPPED, new ItemStack(Blocks.PLANKS, 1, 4));
        lk(new ItemStack(Blocks.LOG2, 1, 1), DARK_OAK_STRIPPED, new ItemStack(Blocks.PLANKS, 1, 5));
        lk(new ItemStack(MetaBlocks.RUBBER_LOG), RUBBER_STRIPPED,
                MetaBlocks.PLANKS.getItemVariant(BlockGregPlanks.BlockType.RUBBER_PLANK));
    }

    private static void lk(ItemStack logStack, BlockStrippedWood.StrippedWoodType strippedWood, ItemStack plankStack) {
        TKCYSARecipeMaps.WOOD_WORKSHOP_RECIPES.recipeBuilder()
                .inputBlockInWorld(logStack)
                .outputBlockInWorld(
                        TKCYSAMetaBlocks.STRIPPED_WOOD.getItemVariant(strippedWood))
                .tool(ToolsModule.GtTool.AXE)
                .toolUses(7)
                .output(OrePrefix.dust, Wood)
                .hideDuration()
                .hideEnergy()
                .buildAndRegister();

        TKCYSARecipeMaps.WOOD_WORKSHOP_RECIPES.recipeBuilder()
                .inputBlockInWorld(
                        TKCYSAMetaBlocks.STRIPPED_WOOD.getItemVariant(strippedWood))
                .outputBlockInWorld(plankStack)
                .tool(ToolsModule.GtTool.SAW)
                .toolUses(7)
                .output(OrePrefix.dust, Wood)
                .hideDuration()
                .hideEnergy()
                .buildAndRegister();
    }
}
