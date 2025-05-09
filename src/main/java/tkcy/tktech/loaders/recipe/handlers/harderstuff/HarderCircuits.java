package tkcy.tktech.loaders.recipe.handlers.harderstuff;

import static gregtech.api.unification.material.Materials.*;
import static gregtech.api.unification.ore.OrePrefix.cableGtSingle;
import static gregtech.api.unification.ore.OrePrefix.plate;
import static gregtech.common.items.MetaItems.*;
import static gregtech.common.items.MetaItems.ELECTRONIC_CIRCUIT_LV;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.modules.toolmodule.ToolsModule;

@UtilityClass
public class HarderCircuits {

    public static void register() {
        TkTechRecipeMaps.BASIC_ELECTRONIC_RECIPES.recipeBuilder()
                .input(RESISTOR, 2)
                .input(BASIC_CIRCUIT_BOARD)
                .input(VACUUM_TUBE)
                .input(plate, Steel)
                .input(cableGtSingle, RedAlloy, 3)
                .fluidInputs(Tin.getFluid(288))
                .tool(ToolsModule.GtTool.SOLDERING_IRON)
                .toolUses(7)
                .hideDuration()
                .hideEnergy()
                .output(ELECTRONIC_CIRCUIT_LV)
                .buildAndRegister();
    }
}
