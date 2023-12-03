package tkcy.simpleaddon.loaders.recipe;

import tkcy.simpleaddon.common.TKCYSAConfigHolder;
import tkcy.simpleaddon.loaders.recipe.chains.*;
import tkcy.simpleaddon.loaders.recipe.handlers.Alloys;
import tkcy.simpleaddon.loaders.recipe.handlers.MiscChemicals;
import tkcy.simpleaddon.loaders.recipe.handlers.Roasting;
import tkcy.simpleaddon.loaders.recipe.handlers.TKCYSAMaterialRecipeHandler;
import tkcy.simpleaddon.loaders.recipe.handlers.harderstuff.HarderHull;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        TKCYSAMaterialRecipeHandler.register();

        MiscChemicals.init();

        if (TKCYSAConfigHolder.harderStuff.enableGalvanizedSteel) Alloys.init();
        HarderHull.init();

        if (TKCYSAConfigHolder.chains.enableCopperChain) CopperChains.init();
        if (TKCYSAConfigHolder.chains.enableChromiteChain) ChromiteChain.init();
        if (TKCYSAConfigHolder.chains.enableGoldChain) GoldChain.init();
        if (TKCYSAConfigHolder.chains.enableIronChain) IronChain.init();
        if (TKCYSAConfigHolder.chains.enableTungstenChain) TungstenChain.init();
        if (TKCYSAConfigHolder.chains.enableRoastingChain) Roasting.init();
        if (TKCYSAConfigHolder.chains.enableZincChain) ZincChain.init();
        if (TKCYSAConfigHolder.chains.enableGermaniumChain) GermaniumChain.init();
    }
}
