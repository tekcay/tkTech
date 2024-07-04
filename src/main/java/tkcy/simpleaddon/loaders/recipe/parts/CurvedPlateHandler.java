package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.loaders.recipe.CraftingComponent.*;
import static tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix.curvedPlate;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.loaders.recipe.MetaTileEntityLoader;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

@UtilityClass
public class CurvedPlateHandler {

    public static void init() {
        TKCYSAOrePrefix.curvedPlate.addProcessingHandler(PropertyKey.INGOT, CurvedPlateHandler::processCurvedPlate);
        rollingMill();
    }

    private static void rollingMill() {
        MetaTileEntityLoader.registerMachineRecipe(TKCYSAMetaTileEntities.ROLLING_MILL,
                "WPS", "PHC", "WPS",
                'P', PISTON,
                'S', CABLE,
                'W', new UnificationEntry(OrePrefix.gear, Materials.Steel),
                'P', PLATE,
                'C', CIRCUIT,
                'H', HULL);
    }

    private static void processCurvedPlate(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        ModHandler.addShapedRecipe(String.format("%s_%s", material.getUnlocalizedName(), curvedPlate.name()),
                OreDictUnifier.get(curvedPlate, material),
                " h ", " P ", "   ",
                'P', new UnificationEntry(plate, material));

        TKCYSARecipeMaps.ROLLING_RECIPES.recipeBuilder()
                .input(plate, material)
                .output(curvedPlate, material)
                .duration((int) material.getMass())
                .EUt(24)
                .buildAndRegister();
    }
}
