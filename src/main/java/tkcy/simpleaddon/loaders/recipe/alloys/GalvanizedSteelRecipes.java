package tkcy.simpleaddon.loaders.recipe.alloys;

import static gregtech.api.unification.material.Materials.Steel;
import static gregtech.api.unification.material.Materials.Zinc;
import static gregtech.api.unification.ore.OrePrefix.*;
import static tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps.ADVANCED_ELECTROLYSIS;
import static tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps.CASTING;
import static tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials.GalvanizedSteel;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.GTValues;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GalvanizedSteelRecipes {

    public static List<OrePrefix> PREFIXES = new ArrayList<>();

    static {
        PREFIXES.add(plate);
        PREFIXES.add(stickLong);
        PREFIXES.add(stick);
        PREFIXES.add(plateDense);
        PREFIXES.add(bolt);
        PREFIXES.add(screw);
        PREFIXES.add(plateDouble);
        PREFIXES.add(ingot);
        PREFIXES.add(ring);
        PREFIXES.add(wireFine);
        PREFIXES.add(foil);
        PREFIXES.add(frameGt);
        PREFIXES.add(gear);
        PREFIXES.add(gearSmall);
        PREFIXES.add(springSmall);
        PREFIXES.add(spring);
        PREFIXES.add(rotor);
        PREFIXES.add(pipeTinyFluid);
        PREFIXES.add(pipeSmallFluid);
        PREFIXES.add(pipeNormalFluid);
        PREFIXES.add(pipeLargeFluid);
        PREFIXES.add(pipeHugeFluid);
        PREFIXES.add(pipeQuadrupleFluid);
        PREFIXES.add(pipeNonupleFluid);
    }

    public static void init() {
        PREFIXES.forEach(GalvanizedSteelRecipes::galvanized);
    }

    private static void galvanized(OrePrefix orePrefix) {
        int count = (int) (GTValues.L * orePrefix.getMaterialAmount(Steel) / GTValues.M);

        CASTING.recipeBuilder().duration((count) * 2)
                .fluidInputs(Zinc.getFluid(count * 2))
                .input(orePrefix, Steel)
                .output(orePrefix, GalvanizedSteel)
                .buildAndRegister();

        ADVANCED_ELECTROLYSIS.recipeBuilder().duration((count))
                .fluidInputs(Zinc.getFluid(count))
                .notConsumable(stickLong, Zinc)
                .input(orePrefix, Steel)
                .output(orePrefix, GalvanizedSteel)
                .EUt(30)
                .buildAndRegister();
    }
}
