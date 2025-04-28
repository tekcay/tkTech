package tkcy.simpleaddon.api.recipes.logic;

import org.jetbrains.annotations.NotNull;

import tkcy.simpleaddon.api.recipes.logic.newway.IRecipeLogicContainer;

public interface IExtraRecipeLogic {

    @NotNull
    IRecipeLogicContainer setRecipeLogicContainer();

    @NotNull
    IRecipeLogicContainer getRecipeLogicContainer();
}
