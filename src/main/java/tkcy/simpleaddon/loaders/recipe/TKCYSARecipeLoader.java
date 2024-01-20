package tkcy.simpleaddon.loaders.recipe;

import tkcy.simpleaddon.loaders.recipe.alloys.AlloyingRecipes;
import tkcy.simpleaddon.loaders.recipe.alloys.GalvanizedSteelRecipes;
import tkcy.simpleaddon.loaders.recipe.chains.*;
import tkcy.simpleaddon.loaders.recipe.handlers.*;
import tkcy.simpleaddon.loaders.recipe.handlers.harderstuff.HarderMachineCasings;
import tkcy.simpleaddon.loaders.recipe.parts.PartsHandler;

import static tkcy.simpleaddon.common.TKCYSAConfigHolder.chains;
import static tkcy.simpleaddon.common.TKCYSAConfigHolder.harderStuff;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        TKCYSAMaterialRecipeHandler.register();

        MiscChemicals.init();
        PrimitiveCastingHandler.init();
        PartsHandler.init();
        GasReleaseHandler.generateRecipes();

        if (harderStuff.enableAlloyingAndCasting) AlloyingRecipes.init();

        MTEs.init();

        if (harderStuff.enableHarderMachineCasings) {
            GalvanizedSteelRecipes.init();
            HarderMachineCasings.init();
        }

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
