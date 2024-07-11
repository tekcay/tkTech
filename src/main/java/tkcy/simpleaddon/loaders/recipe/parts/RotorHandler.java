package tkcy.simpleaddon.loaders.recipe.parts;

import static gregtech.api.GTValues.LV;
import static gregtech.api.GTValues.VA;

import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.RotorProperty;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RotorHandler {

    public static void init() {
        OrePrefix.rotor.addProcessingHandler(PropertyKey.ROTOR, RotorHandler::processRotors);
    }

    private static void processRotors(OrePrefix orePrefix, Material material, RotorProperty rotorProperty) {
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(VA[LV])
                .duration((int) material.getMass())
                .input(OrePrefix.ring, material)
                .input(OrePrefix.screw, material)
                .fluidInputs(Materials.SolderingAlloy.getFluid(36))
                .output(orePrefix, material)
                .buildAndRegister();
    }
}
