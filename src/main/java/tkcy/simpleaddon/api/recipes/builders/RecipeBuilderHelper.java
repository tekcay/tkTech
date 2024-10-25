package tkcy.simpleaddon.api.recipes.builders;

import java.util.List;

import gregtech.api.recipes.recipeproperties.IRecipePropertyStorage;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import gregtech.api.recipes.recipeproperties.RecipePropertyStorage;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.InternalAnnotations;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertyHelper;

@UtilityClass
public class RecipeBuilderHelper {

    @SafeVarargs
    public static <T> void build(IRecipePropertyStorage recipePropertyStorage,
                                 RecipePropertyHelper<T>... recipeProperties) {
        for (RecipePropertyHelper<T> helper : recipeProperties) {
            build(recipePropertyStorage, helper);
        }
    }

    public static <T> void build(IRecipePropertyStorage recipePropertyStorage,
                                 List<RecipePropertyHelper<T>> recipeProperties) {
        for (RecipePropertyHelper<T> helper : recipeProperties) {
            build(recipePropertyStorage, helper);
        }
    }

    @InternalAnnotations.NotBreakingBug(value = "            if (recipePropertyHelper.isValueValid().test(value)) {\n" +
            "                recipePropertyStorage.store(instance, defaultValue);\n" +
            "            }")
    private static <T> void build(IRecipePropertyStorage recipePropertyStorage,
                                  RecipePropertyHelper<T> recipePropertyHelper) {
        if (recipePropertyStorage == null) {
            recipePropertyStorage = new RecipePropertyStorage();
        }

        T defaultValue = recipePropertyHelper.getDefaultValue();
        RecipeProperty<T> instance = recipePropertyHelper.getPropertyInstance();

        if (recipePropertyStorage.hasRecipeProperty(instance)) {

            T value = recipePropertyStorage.getRecipePropertyValue(instance, defaultValue);

            if (recipePropertyHelper.isValueValid().test(value)) {
                recipePropertyStorage.store(instance, defaultValue);
            }
        } else recipePropertyStorage.store(instance, defaultValue);
    }
}
