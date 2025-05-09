package tkcy.tktech.loaders.recipe.parts;

import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static tkcy.tktech.api.unification.ore.TkTechOrePrefix.curvedPlate;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

@UtilityClass
public class CurvedPlateHandler {

    public static void init() {
        TkTechOrePrefix.curvedPlate.addProcessingHandler(CurvedPlateHandler::processCurvedPlate);
        rollingMill();
    }

    private static void rollingMill() {
        MetaTileEntityLoader.registerMachineRecipe(TkTechMetaTileEntities.ROLLING_MILL,
                "WPS", "PHC", "WPS",
                'P', PISTON,
                'S', CABLE,
                'W', new UnificationEntry(OrePrefix.gear, Materials.Steel),
                'P', PLATE,
                'C', CIRCUIT,
                'H', HULL);
    }

    private static void processCurvedPlate(OrePrefix orePrefix, Material material) {
        ModHandler.addShapedRecipe(String.format("%s_%s", material.getUnlocalizedName(), curvedPlate.name()),
                OreDictUnifier.get(curvedPlate, material),
                " h ", " P ", "   ",
                'P', new UnificationEntry(plate, material));

        TkTechRecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .input(plate, material)
                .output(curvedPlate, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
