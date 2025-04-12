package tkcy.simpleaddon.loaders.recipe;

import static gregtech.api.recipes.RecipeMaps.*;
import static tkcy.simpleaddon.common.TKCYSAConfigHolder.chains;
import static tkcy.simpleaddon.common.TKCYSAConfigHolder.harderStuff;

import java.util.Arrays;
import java.util.Objects;

import net.minecraft.item.ItemStack;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.GTRecipeHandler;
import gregtech.api.recipes.ModHandler;
import gregtech.common.metatileentities.MetaTileEntities;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.loaders.recipe.alloys.DustMixing;
import tkcy.simpleaddon.loaders.recipe.alloys.GalvanizedSteelRecipes;
import tkcy.simpleaddon.loaders.recipe.alloys.Melting;
import tkcy.simpleaddon.loaders.recipe.chains.chemicals.OxalicAcidChain;
import tkcy.simpleaddon.loaders.recipe.chains.metals.*;
import tkcy.simpleaddon.loaders.recipe.handlers.*;
import tkcy.simpleaddon.loaders.recipe.handlers.chemistry.ChemistryHandler;
import tkcy.simpleaddon.loaders.recipe.handlers.harderstuff.*;

@UtilityClass
public final class TKCYSARecipeLoader {

    public static void latestInit() {
        POLARIZER_RECIPES.setMaxInputs(2);
        BLAST_RECIPES.setMaxFluidOutputs(2);

        ChemistryHandler.init();

        PrimitiveCastingHandler.init();
        GasReleaseHandler.generateRecipes();

        DustMixing.init();
        Melting.init();

        harderStuff();
        chains();
        BrickMTEs.init();
        RecipesTests.register();

        removeGTCEuElectrolyzsis();
        removeGTCEuElectrolyzerMTEs();
    }

    private static void harderStuff() {
        // if (harderStuff.enableAlloyingAndCasting) AlloyingRecipes.init();
        if (harderStuff.enableHarderCoils) HarderCoilsRecipes.init();
        if (harderStuff.enableHarderPolarization) HarderPolarization.init();
        if (harderStuff.enableHarderComponents) HarderComponents.init();
        if (harderStuff.enableHarderHydrogenation) Hydrogenation.init();
        if (harderStuff.enableHarderCracking) HarderCracking.init();
        if (harderStuff.enableHarderMachineCasings) {
            GalvanizedSteelRecipes.init();
            HarderMachineCasings.init();
        }
    }

    private static void chains() {
        OxalicAcidChain.init();
        if (chains.enablePlatinumGroupChains) PlatinumChain.init();
        if (chains.enableCopperChain) CopperChains.init();
        if (chains.enableChromiteChain) ChromiteChain.init();
        if (chains.enableGoldChain) GoldChain.init();
        if (chains.enableIronChain) IronChain.init();
        if (chains.enableTungstenChain) TungstenChain.init();
        if (chains.enableRoastingChain) Roasting.init();
        if (chains.enableZincChain) ZincChain.init();
        if (chains.enableGermaniumChain) GermaniumChain.init();
        if (chains.enableAluminiumChain) AluminiumChain.init();
        if (chains.enableAluminiumChain) FluorineChain.init();
    }

    private static void removeGTCEuElectrolyzsis() {
        GTRecipeHandler.removeAllRecipes(ELECTROLYZER_RECIPES);
    }

    /**
     * Hidden in JEI via {@link //tkcy.simpleaddon.integration.jei.Removals}.
     */
    private static void removeGTCEuElectrolyzerMTEs() {
        Arrays.stream(MetaTileEntities.ELECTROLYZER)
                .filter(Objects::nonNull)
                .map(MetaTileEntity::getStackForm)
                .map(ItemStack::copy)
                .forEach(ModHandler::removeRecipeByOutput);
    }
}
