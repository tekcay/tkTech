package tkcy.simpleaddon.loaders.recipe;

import static tkcy.simpleaddon.common.TKCYSAConfigHolder.chains;
import static tkcy.simpleaddon.common.TKCYSAConfigHolder.harderStuff;

import gregtech.api.recipes.RecipeMaps;

import tkcy.simpleaddon.loaders.recipe.alloys.AlloyingRecipes;
import tkcy.simpleaddon.loaders.recipe.alloys.GalvanizedSteelRecipes;
import tkcy.simpleaddon.loaders.recipe.chains.chemicals.OxalicAcidChain;
import tkcy.simpleaddon.loaders.recipe.chains.metals.*;
import tkcy.simpleaddon.loaders.recipe.handlers.*;
import tkcy.simpleaddon.loaders.recipe.handlers.harderstuff.*;
import tkcy.simpleaddon.loaders.recipe.parts.PartsHandler;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        RecipeMaps.BLAST_RECIPES.setMaxFluidOutputs(2);


        TKCYSAMaterialRecipeHandler.register();

        OxalicAcidChain.init();
        MiscChemicals.init();
        PrimitiveCastingHandler.init();
        PartsHandler.init();
        GasReleaseHandler.generateRecipes();

        if (harderStuff.enableAlloyingAndCasting) AlloyingRecipes.init();
        if (harderStuff.enableHarderCoils) HarderCoilsRecipes.init();
        if (harderStuff.enableHarderPolarization) HarderPolarization.init();
        if (harderStuff.removeTinCircuitRecipes) CircuitRecipes.init();
        if (harderStuff.enableHarderComponents) HarderComponents.init();
        if (harderStuff.enableMethaneCracking) MethaneCracking.init();

        MTEs.init();

        if (harderStuff.enableHarderMachineCasings) {
            GalvanizedSteelRecipes.init();
            HarderMachineCasings.init();
        }

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
}
