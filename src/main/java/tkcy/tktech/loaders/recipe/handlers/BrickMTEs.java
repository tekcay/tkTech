package tkcy.tktech.loaders.recipe.handlers;

import static gregtech.api.unification.ore.OrePrefix.*;
import static net.minecraft.init.Blocks.GLASS;

import net.minecraft.item.ItemStack;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.metatileentities.MetaTileEntities;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.materials.TkTechMaterials;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

@UtilityClass
public class BrickMTEs {

    public static void init() {
        brickMultis();
        brickBusHatch();
        singleBlockMachines();
    }

    private static void singleBlockMachines() {
        ModHandler.addShapedRecipe("primitive_casting", TkTechMetaTileEntities.PRIMITIVE_CASTING.getStackForm(),
                "PPP", "P P", "PPP", 'P', new UnificationEntry(plate, Materials.Brick));
    }

    private static void brickMultis() {
        ModHandler.addShapedRecipe("tkcy_assembling_machine", TkTechMetaTileEntities.ASSEMBLING_MACHINE.getStackForm(),
                "BVB", "BCB", "BBB",
                'B', new UnificationEntry(plate, Materials.Steel),
                'V', new UnificationEntry(plate, TkTechMaterials.GalvanizedSteel),
                'C', MetaTileEntities.HULL[1].getStackForm());

        ModHandler.addShapedRecipe("tkcy_electrolysis_unit", TkTechMetaTileEntities.ELECTROLYZER.getStackForm(),
                "BBB", "VCD", "BBB",
                'B', new UnificationEntry(plate, TkTechMaterials.GalvanizedSteel),
                'V', new UnificationEntry(stickLong, Materials.Zinc),
                'D', new UnificationEntry(stickLong, Materials.Copper),
                'C', MetaTileEntities.HULL[1].getStackForm());

        ModHandler.addShapedRecipe("tkcy_primitive_roasting_oven",
                TkTechMetaTileEntities.PRIMITIVE_ROASTING_OVEN.getStackForm(),
                "BBB", "BVB", "BBB",
                'B', new UnificationEntry(ingot, Materials.Brick),
                'V', new UnificationEntry(plate, Materials.Brick));

        ModHandler.addShapedRecipe("tkcy_fluid_primitive_blast_furnace",
                TkTechMetaTileEntities.FLUID_PRIMITIVE_BLAST_FURNACE.getStackForm(),
                "BBB", "BVB", "BBB",
                'V', new UnificationEntry(ingot, Materials.Brick),
                'B', new UnificationEntry(plate, Materials.Brick));
    }

    private static void brickBusHatch() {
        ModHandler.addShapedRecipe("input_brick_item_bus", TkTechMetaTileEntities.BRICK_ITEM_BUS[0].getStackForm(),
                "BCB", "BBB", "BBB",
                'B', new UnificationEntry(ingot, Materials.Brick),
                'C', MetaTileEntities.WOODEN_CRATE.getStackForm());

        ModHandler.addShapedRecipe("output_brick_item_bus", TkTechMetaTileEntities.BRICK_ITEM_BUS[1].getStackForm(),
                "BBB", "BBB", "BCB",
                'B', new UnificationEntry(ingot, Materials.Brick),
                'C', MetaTileEntities.WOODEN_CRATE.getStackForm());

        ModHandler.addShapedRecipe("input_brick_fluid_hatch",
                TkTechMetaTileEntities.BRICK_FLUID_HATCH[0].getStackForm(),
                "BCB", "BBB", "BBB",
                'B', new UnificationEntry(ingot, Materials.Brick),
                'C', new ItemStack(GLASS));

        ModHandler.addShapedRecipe("output_brick_fluid_hatch",
                TkTechMetaTileEntities.BRICK_FLUID_HATCH[1].getStackForm(),
                "BBB", "BBB", "BCB",
                'B', new UnificationEntry(ingot, Materials.Brick),
                'C', new ItemStack(GLASS));
    }
}
