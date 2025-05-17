package tkcy.tktech.api.recipes.logic.containers;

import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.tktech.api.recipes.logic.IRecipePropertiesValueMap;
import tkcy.tktech.api.recipes.logic.RecipeLogicType;
import tkcy.tktech.api.recipes.properties.DurationModifierRecipeProperty;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;

@Getter
@AllArgsConstructor
public class RandomDurationRecipeLogic implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private final AbstractRecipeLogic abstractRecipeLogic;

    public float getDurationModifierFromRecipe(@NotNull Recipe recipe) {
        return DurationModifierRecipeProperty.getInstance().getValueFromRecipe(recipe);
    }

    public void setDuration(@NotNull Recipe recipe) {
        double durationModifier = getDurationModifierFromRecipe(recipe);
        durationModifier *= Math.random();

        int recipeDuration = getAbstractRecipeLogic().getMaxProgress();
        int recipeValue = (int) (recipeDuration * durationModifier);

        if (durationModifier > 1) recipeDuration += recipeValue;
        else recipeDuration -= recipeValue;

        getAbstractRecipeLogic().setMaxProgress(recipeDuration);
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.RANDOM_DURATION;
    }

    @Override
    public void postSetupRecipe(@NotNull Recipe recipe) {
        setDuration(recipe);
    }

    @Override
    public @Nullable IRecipeLogicContainer getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.RANDOM_DURATION ? this : null;
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {}
}
