package tkcy.simpleaddon.loaders.recipe.handlers.harderstuff;

import static gregtech.api.unification.material.Materials.Magnalium;
import static gregtech.api.unification.material.Materials.Steel;
import static tkcy.simpleaddon.api.unification.TKCYSAMaterials.GalvanizedSteel;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

public class HarderHull {

    public static void init() {
        machineCasing(Steel, 0);
        machineCasing(GalvanizedSteel, 1);
        machineCasing(Magnalium, 2);
    }

    private static void machineCasing(Material input, int tier) {

        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .input(OrePrefix.plate, input, 8)
                .fluidInputs(Materials.SolderingAlloy.getFluid(GTValues.L))
                .outputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.values()[tier]))
                .circuitMeta(8)
                .EUt((int) ((GTValues.V[tier]) / 2))
                .duration(20 * 10)
                .buildAndRegister();
    }
}
