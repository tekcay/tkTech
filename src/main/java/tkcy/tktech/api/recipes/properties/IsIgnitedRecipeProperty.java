package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;

public class IsIgnitedRecipeProperty extends RecipeProperty<Boolean>
                                     implements IRecipePropertyHelper<Boolean> {

    public static final String KEY = RecipePropertiesKey.IS_IGNITED;
    private static IsIgnitedRecipeProperty INSTANCE;

    private IsIgnitedRecipeProperty() {
        super(KEY, Boolean.class);
    }

    @NotNull
    public static IsIgnitedRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IsIgnitedRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagInt(castValue(value) ? 1 : 0);
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return ((NBTTagInt) nbt).getInt() == 1;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tktech.recipe.requires_ignition"), x, y, color);
    }

    @Override
    public Predicate<Boolean> testSuppliedValue() {
        return Boolean::booleanValue;
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Boolean valueToTest,
                                                      RecipeBuilder<?> recipeBuilder, Runnable recipeInvalidator) {
        recipeBuilder.applyProperty(getProperty(), valueToTest);
        return recipeBuilder;
    }

    @Override
    public Boolean getDefaultValue() {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Not valid!";
    }

    @Override
    public RecipeProperty<Boolean> getProperty() {
        return this;
    }
}
