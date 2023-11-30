package tkcy.simpleaddon.loaders.recipe;

import tkcy.simpleaddon.common.TKCYSAConfigHolder;
import tkcy.simpleaddon.loaders.recipe.chains.ChromiteChain;
import tkcy.simpleaddon.loaders.recipe.chains.GoldChain;
import tkcy.simpleaddon.loaders.recipe.chains.IronChain;
import tkcy.simpleaddon.loaders.recipe.chains.TungstenChain;
import tkcy.simpleaddon.loaders.recipe.handlers.MiscChemicals;
import tkcy.simpleaddon.loaders.recipe.handlers.Roasting;
import tkcy.simpleaddon.loaders.recipe.handlers.TKCYSAMaterialRecipeHandler;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        TKCYSAMaterialRecipeHandler.register();

        MiscChemicals.init();

        if (TKCYSAConfigHolder.chains.enableChromiteChain) ChromiteChain.init();
        if (TKCYSAConfigHolder.chains.enableGoldChain) GoldChain.init();
        if (TKCYSAConfigHolder.chains.enableIronChain) IronChain.init();
        if (TKCYSAConfigHolder.chains.enableTungstenChain) TungstenChain.init();

        Roasting.init();
    }
}
