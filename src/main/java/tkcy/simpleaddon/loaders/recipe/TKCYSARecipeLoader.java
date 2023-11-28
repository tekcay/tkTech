package tkcy.simpleaddon.loaders.recipe;

import tkcy.simpleaddon.loaders.recipe.chains.ChromiteChain;
import tkcy.simpleaddon.loaders.recipe.chains.GoldChain;
import tkcy.simpleaddon.loaders.recipe.handlers.MiscChemicals;
import tkcy.simpleaddon.loaders.recipe.handlers.TKCYSAMaterialRecipeHandler;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        TKCYSAMaterialRecipeHandler.register();
        MiscChemicals.init();
        ChromiteChain.init();
        GoldChain.init();
    }
}
