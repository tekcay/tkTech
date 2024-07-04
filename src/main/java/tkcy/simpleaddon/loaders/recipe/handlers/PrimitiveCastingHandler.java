package tkcy.simpleaddon.loaders.recipe.handlers;

import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.CastingInfo;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.MaterialHelper;
import tkcy.simpleaddon.api.utils.TKCYSALog;

@UtilityClass
public class PrimitiveCastingHandler {

    public static void init() {
        try {
            generate();
        } catch (IllegalArgumentException e) {
            TKCYSALog.logger.error("ERROR");
        }
    }

    private static void generate() {
        GregTechAPI.materialManager.getRegisteredMaterials()
                .stream()
                .filter(Material::hasFluid)
                .filter(MaterialHelper.hasPlateFlag)
                .forEach(PrimitiveCastingHandler::loopAroundMoldMaterials);
    }

    private static void loopAroundMoldMaterials(Material input) {
        CastingInfo.CASTING_INFOS
                .stream()
                .filter(castingInfo -> castingInfo.canHandleFluidMaterial(input))
                .forEach(castingInfo -> generate(input, castingInfo.getMoldMaterial()));
    }

    private static void generate(Material input, Material moldMaterial) {
        CastingInfo.MATERIALS_OREPREFIXES_MOLDS
                .getSecond()
                .stream()
                .filter(orePrefix -> orePrefix.doGenerateItem(input))
                .forEach(orePrefix -> recipe(input, moldMaterial, orePrefix));
    }

    private static void recipe(Material input, Material moldMaterial, OrePrefix orePrefix) {
        int fluidAmount = MaterialHelper.getOrePrefixFluidAmount(orePrefix);
        int duration = getDuration(input, fluidAmount);
        int multiplier = CastingInfo.getFluidMaterialGapMultiplier(moldMaterial, input);

        duration *= multiplier;

        if (duration == 0) {
            sendErrorMessage(input, moldMaterial, orePrefix, fluidAmount, multiplier);
            throw new IllegalArgumentException();
        }

        TKCYSARecipeMaps.CASTING.recipeBuilder()
                .fluidInputs(input.getFluid(fluidAmount))
                .notConsumable(orePrefix, moldMaterial)
                .output(orePrefix, input)
                .duration(duration)
                .buildAndRegister();
    }

    private static int getDuration(Material material, int fluidOreAmount) {
        return Math.max(1, (int) (material.getMass() * fluidOreAmount / SECOND));
    }

    private static void sendErrorMessage(Material input, Material moldMaterial, OrePrefix orePrefix, int fluidAmount,
                                         int multiplier) {
        TKCYSALog.logger.error(String.format(
                "Primitive casting recipe for %s %s with the mold %s returned a duration of 0. Error details :" +
                        "fluidAmount = %d" +
                        "duration before multiplier = %d" +
                        "multiplier = %d" +
                        input.getLocalizedName(),
                orePrefix.name(),
                moldMaterial.getLocalizedName(),
                orePrefix.name(),
                fluidAmount,
                getDuration(input, fluidAmount),
                multiplier));
        throw new IllegalArgumentException();
    }
}
