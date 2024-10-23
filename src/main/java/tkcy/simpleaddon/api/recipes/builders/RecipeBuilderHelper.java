package tkcy.simpleaddon.api.recipes.builders;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;

public interface RecipeBuilderHelper {

    /**
     * @return WARNING!! The instance must be used!
     */
    RecipeProperty<Integer> getRecipeProperty();

    default void build(IRecipePropertyStorage recipePropertyStorage) {
        if (recipePropertyStorage == null) recipePropertyStorage = new RecipePropertyStorage();
        if (recipePropertyStorage.hasRecipeProperty(getRecipeProperty())) {
            if (recipePropertyStorage.getRecipePropertyValue(getRecipeProperty(), 0) <= 0) {
                recipePropertyStorage.store(getRecipeProperty(), 0);
            }
        } else {
            recipePropertyStorage.store(getRecipeProperty(), 0);
        }
    }
}
