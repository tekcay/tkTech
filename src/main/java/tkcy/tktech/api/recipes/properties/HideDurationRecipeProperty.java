package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;

public class HideDurationRecipeProperty extends RecipeProperty<Boolean> implements IRecipePropertyHelper<Boolean> {

    public static final String KEY = RecipePropertiesKey.HIDE_DURATION_KEY;
    private static HideDurationRecipeProperty INSTANCE;

    private HideDurationRecipeProperty() {
        super(KEY, Boolean.class);
    }

    @NotNull
    public static HideDurationRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HideDurationRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public int getInfoHeight(@NotNull Object value) {
        return 0;
    }

    @Override
    public boolean hideDuration() {
        return true;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagByte((byte) (castValue(value) ? 1 : 0));
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return ((NBTTagByte) nbt).getByte() == 1;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(Boolean valueToTest,
                                                      RecipeBuilder<?> recipeBuilder, Runnable recipeInvalidator) {
        return recipeBuilder;
    }

    @Override
    public Predicate<Boolean> testSuppliedValue() {
        return value -> value == getDefaultValue();
    }

    @Override
    public Boolean getDefaultValue() {
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "must be true";
    }

    @Override
    public RecipeProperty<Boolean> getProperty() {
        return this;
    }
}
