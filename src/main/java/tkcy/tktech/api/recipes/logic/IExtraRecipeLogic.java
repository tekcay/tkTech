package tkcy.tktech.api.recipes.logic;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.api.recipes.logic.containers.IRecipeLogicContainer;

public interface IExtraRecipeLogic {

    @NotNull
    IRecipeLogicContainer setRecipeLogicContainer();

    @NotNull
    IRecipeLogicContainer getRecipeLogicContainer();
}
