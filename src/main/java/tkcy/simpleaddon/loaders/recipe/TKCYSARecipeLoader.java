package tkcy.simpleaddon.loaders.recipe;

import tkcy.simpleaddon.common.TKCYSAConfigHolder;
import tkcy.simpleaddon.loaders.recipe.alloys.GalvanizedSteel;
import tkcy.simpleaddon.loaders.recipe.chains.*;
import tkcy.simpleaddon.loaders.recipe.handlers.*;
import tkcy.simpleaddon.loaders.recipe.handlers.harderstuff.HarderMachineCasings;

public final class TKCYSARecipeLoader {

    private TKCYSARecipeLoader() {}

    public static void init() {
        TKCYSAMaterialRecipeHandler.register();

        MiscChemicals.init();
        PrimitiveCastingHandler.init();

        MTEs.init();

        if (TKCYSAConfigHolder.harderStuff.enableHarderMachineCasings) {
            GalvanizedSteel.init();
            HarderMachineCasings.init();
        }

        if (TKCYSAConfigHolder.chains.enableCopperChain) CopperChains.init();
        if (TKCYSAConfigHolder.chains.enableChromiteChain) ChromiteChain.init();
        if (TKCYSAConfigHolder.chains.enableGoldChain) GoldChain.init();
        if (TKCYSAConfigHolder.chains.enableIronChain) IronChain.init();
        if (TKCYSAConfigHolder.chains.enableTungstenChain) TungstenChain.init();
        if (TKCYSAConfigHolder.chains.enableRoastingChain) Roasting.init();
        if (TKCYSAConfigHolder.chains.enableZincChain) ZincChain.init();
        if (TKCYSAConfigHolder.chains.enableGermaniumChain) GermaniumChain.init();
        if (TKCYSAConfigHolder.chains.enableAluminiumChain) AluminiumChain.init();
        if (TKCYSAConfigHolder.chains.enableAluminiumChain) FluorineChain.init();
    }
}
