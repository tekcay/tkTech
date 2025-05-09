package tkcy.tktech.loaders.recipe;

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
import tkcy.tktech.api.unification.ore.TkTechOrePrefix;
import tkcy.tktech.common.item.TkTechToolItems;

@UtilityClass
public class ToolsRecipes {

    public static void init() {
        ToolHeadReplaceRecipe.setToolHeadForTool(TkTechOrePrefix.toolTipSolderingIron, TkTechToolItems.SOLDERING_IRON);

        TkTechOrePrefix.toolTipSolderingIron.addProcessingHandler(PropertyKey.INGOT,
                ToolsRecipes::processSolderingIronTool);
        TkTechOrePrefix.toolTipSolderingIron.addProcessingHandler(PropertyKey.INGOT,
                ToolsRecipes::processSolderingIronTip);
    }

    private static void processSolderingIronTool(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        addToolRecipe(material, TkTechToolItems.SOLDERING_IRON, false,
                "TBf", "Rd ", "  P",
                'T', new UnificationEntry(TkTechOrePrefix.toolTipSolderingIron, material),
                'P', new UnificationEntry(OrePrefix.plate, Materials.Rubber),
                'R', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                'B', new UnificationEntry(OrePrefix.bolt, material));
    }

    private static void processSolderingIronTip(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        ModHandler.addShapedRecipe(String.format("%s_%s", TkTechOrePrefix.toolTipSolderingIron.name(), material),
                OreDictUnifier.get(TkTechOrePrefix.toolTipSolderingIron, material),
                "RB", "Bf",
                'R', new UnificationEntry(OrePrefix.stick, Materials.Iron),
                'B', new UnificationEntry(OrePrefix.bolt, material));
    }
}
