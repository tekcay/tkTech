package tkcy.simpleaddon.api.recipes.builders;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;
import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;

@UtilityClass
public class RecipeBuilderHelpers {

    /**
     * WARNING!!
     * @param recipePropertyStorage
     * @param recipeProperty WARNING!! The instance must be used!
     */
    public static void build(IRecipePropertyStorage recipePropertyStorage, RecipeProperty<Integer> recipeProperty) {

        if (recipePropertyStorage == null) recipePropertyStorage = new RecipePropertyStorage();
        if (recipePropertyStorage.hasRecipeProperty(recipeProperty)) {
            if (recipePropertyStorage.getRecipePropertyValue(recipeProperty, 0) <= 0) {
                recipePropertyStorage.store(recipeProperty, 0);
            }
        } else {
            recipePropertyStorage.store(recipeProperty, 0);
        }
    }
}
