package tkcy.tktech.api.recipes.logic;

import org.jetbrains.annotations.NotNull;

public interface IExtraRecipeLogic {

    @NotNull
    IRecipeLogicContainer setRecipeLogicContainer();

    @NotNull
    IRecipeLogicContainer getRecipeLogicContainer();
}
