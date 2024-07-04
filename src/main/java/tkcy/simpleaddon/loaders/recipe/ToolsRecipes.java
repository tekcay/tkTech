package tkcy.simpleaddon.loaders.recipe;

import static gregtech.loaders.recipe.handlers.ToolRecipeHandler.addToolRecipe;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.crafting.ToolHeadReplaceRecipe;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
@UtilityClass
public class ToolsRecipes {

    public static void init() {
        ToolHeadReplaceRecipe.setToolHeadForTool(TKCYSAOrePrefix.toolTipSolderingIron, TKCYSAToolItems.SOLDERING_IRON);

        TKCYSAOrePrefix.toolTipSolderingIron.addProcessingHandler(PropertyKey.INGOT,
                ToolsRecipes::processSolderingIronTool);
        TKCYSAOrePrefix.toolTipSolderingIron.addProcessingHandler(PropertyKey.INGOT,
                ToolsRecipes::processSolderingIronTip);
    }

    private static void processSolderingIronTool(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        addToolRecipe(material, TKCYSAToolItems.SOLDERING_IRON, false,
                "TBf", "Rd ", "  P",
                'T', new UnificationEntry(TKCYSAOrePrefix.toolTipSolderingIron, material),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Rubber),
                'R', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                'B', new UnificationEntry(OrePrefix.bolt, material));
    }

    private static void processSolderingIronTip(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        ModHandler.addShapedRecipe(String.format("%s_%s", TKCYSAOrePrefix.toolTipSolderingIron.name(), material),
                OreDictUnifier.get(TKCYSAOrePrefix.toolTipSolderingIron, material),
                "RB", "Bf",
                'R', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                'B', new UnificationEntry(OrePrefix.bolt, material));
    }
}
