package tkcy.tktech.api.recipes.recipemaps;

import mezz.jei.config.Config;

/**
 * A simple interface that allows you to build one recipe per page, depending upon the current JEI config.
 */
public interface IJeiConfigBoundRecipeUI extends ICustomRecipeMapUI {

    @Override
    default int getBackgroundHeight() {
        return Config.getMaxRecipeGuiHeight();
    }
}
