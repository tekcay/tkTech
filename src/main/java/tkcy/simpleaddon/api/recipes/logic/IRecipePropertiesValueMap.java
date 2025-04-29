package tkcy.simpleaddon.api.recipes.logic;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;

public interface IRecipePropertiesValueMap {

    void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters);
}
