package tkcy.simpleaddon.loaders.recipe.handlers;

import static gregtech.api.unification.material.Materials.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;
import tkcy.simpleaddon.api.utils.TKCYSAUtil;

public class PrimitiveCastingHandler {

    public static void init() {
        generateRecipes(Iron);
        generateRecipes(Zinc);
        generateRecipes(Steel);
        generateRecipes(Copper);
        generateRecipes(Tin);
        generateRecipes(Bronze);
        generateRecipes(RedAlloy);
        generateRecipes(TinAlloy);
    }

    private static void generateRecipes(Material material) {
        GTLog.logger.info("material.getUnlocalizedName() : " + material.getUnlocalizedName());

        orePrefixes.stream()
                .filter(orePrefix -> orePrefix.doGenerateItem(material))
                .forEach(orePrefix -> consumer.accept(material, orePrefix));
    }

    private static final BiConsumer<Material, OrePrefix> consumer = (material, orePrefix) -> handler(material,
            orePrefix, TKCYSAUtil.getFluidAmountFromOrePrefix(orePrefix));

    private static final List<OrePrefix> orePrefixes = new ArrayList<>();

    static {
        orePrefixes.add(OrePrefix.ingot);
        orePrefixes.add(OrePrefix.plate);
        orePrefixes.add(OrePrefix.stick);
        orePrefixes.add(OrePrefix.stickLong);
        orePrefixes.add(OrePrefix.gear);
        orePrefixes.add(OrePrefix.gearSmall);
        orePrefixes.add(OrePrefix.bolt);
        orePrefixes.add(OrePrefix.ring);
    }

    private static void handler(Material material, OrePrefix orePrefix, int fluidAmount) {
        TKCYSARecipeMaps.CASTING.recipeBuilder()
                .fluidInputs(material.getFluid(fluidAmount))
                .notConsumable(orePrefix, Brick)
                .output(orePrefix, material)
                .duration(getDuration(material, fluidAmount))
                .buildAndRegister();
    }

    private static int getDuration(Material material, int fluidOreAmount) {
        return (int) (material.getMass() * fluidOreAmount / 20);
    }
}
