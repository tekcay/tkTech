package tkcy.simpleaddon.loaders.recipe.parts;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.modules.ToolsModule;

public class PrimitivePartsHandler {

    public static void init() {
        TKCYSARecipeMaps.PARTS_WORKING.recipeBuilder()
                .tool(ToolsModule.GtTool.HARD_HAMMER)
                .toolUses(3)
                .input(OrePrefix.ingot, Materials.Bronze, 2)
                .output(OrePrefix.plate, Materials.Bronze)
                .output(OrePrefix.dust, Materials.Bronze)
                .duration(20)
                .buildAndRegister();
    }
}
