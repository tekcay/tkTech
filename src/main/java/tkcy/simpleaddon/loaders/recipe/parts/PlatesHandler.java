package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.api.unification.ore.OrePrefix.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.IngotProperty;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.modules.ToolsModule;

@UtilityClass
public class PlatesHandler {

    public static void init() {
        plate.addProcessingHandler(PropertyKey.INGOT, PlatesHandler::primitiveProcessPlate);
    }

    private static final List<Material> excludeMaterials = new ArrayList<>() {

        {
            add(Materials.Wood);
            add(Materials.TreatedWood);
            add(Materials.Epoxy);
            add(Materials.ReinforcedEpoxyResin);
            add(Materials.Rubber);
            add(TKCYSAMaterials.Ceramic);
        }
    };

    private static void primitiveProcessPlate(OrePrefix orePrefix, Material material, IngotProperty ingotProperty) {
        if (material.hasFlag(TKCYSAMaterialFlags.IS_POLYMER) || excludeMaterials.contains(material)) return;

        TKCYSARecipeMaps.PARTS_WORKING.recipeBuilder()
                .input(ingot, material, 2)
                .output(orePrefix, material)
                .output(dustSmall, material, 4)
                .duration(10)
                .tool(ToolsModule.GtTool.HARD_HAMMER)
                .toolUses(1 + (int) material.getMass() / 20)
                .buildAndRegister();

        if (!plateDouble.doGenerateItem(material)) return;

        TKCYSARecipeMaps.PARTS_WORKING.recipeBuilder()
                .tool(ToolsModule.GtTool.HARD_HAMMER)
                .toolUses(2 * (1 + (int) material.getMass() / 20))
                .input(orePrefix, material, 3)
                .output(OrePrefix.plateDouble, material)
                .output(dustSmall, material, 4)
                .duration(20)
                .buildAndRegister();
    }
}
