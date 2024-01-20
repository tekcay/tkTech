package tkcy.simpleaddon.loaders.recipe.handlers;

import static tkcy.simpleaddon.api.TKCYSAValues.SECOND;

import java.util.function.Predicate;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.recipes.CastingInfo;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.MaterialHelper;
import tkcy.simpleaddon.api.utils.TKCYSALog;

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
                // .filter(isNotMoldMaterial)
                .filter(Material::hasFluid)
                .filter(MaterialHelper.hasPlateFlag)
                // .filter(doesGenerateMoldOutputs)
                .forEach(PrimitiveCastingHandler::loopAroundMoldMaterials);
    }

    private static final Predicate<Material> isNotMoldMaterial = material -> !CastingInfo.MOLD_MATERIALS
            .contains(material);

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
        TKCYSALog.logger.info("###############################");
        int fluidAmount = MaterialHelper.getOrePrefixFluidAmount(orePrefix);
        TKCYSALog.logger.info(String.format("\nfluidInput : %s\n%d K\nmoldMaterial : %s \n%d K\norePrefix : %s",
                input.getLocalizedName(), MaterialHelper.getMaterialFluidTemperature.apply(input),
                moldMaterial.getLocalizedName(), MaterialHelper.getMaterialFluidTemperature.apply(moldMaterial),
                orePrefix.name()));

        int duration = getDuration(input, fluidAmount);
        TKCYSALog.logger.info("duration : " + duration);

        int multiplier = CastingInfo.getFluidMaterialGapMultiplier(moldMaterial, input);
        TKCYSALog.logger.info("multiplier : " + multiplier);

        duration *= multiplier;

        if (duration == 0) throw new IllegalArgumentException();

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
}
