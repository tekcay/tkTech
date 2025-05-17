package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;

public class DurationModifierRecipeProperty extends RecipeProperty<Float> implements IRecipePropertyHelper<Float> {

    public static final String KEY = RecipePropertiesKey.DURATION_MODIFIER_KEY;
    private static DurationModifierRecipeProperty INSTANCE;

    private DurationModifierRecipeProperty() {
        super(KEY, Float.class);
    }

    @NotNull
    public static DurationModifierRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DurationModifierRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagFloat(castValue(value));
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return ((NBTTagFloat) nbt).getInt();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public Predicate<Float> testSuppliedValue() {
        return value -> value > 0;
    }

    @Override
    public Float getDefaultValue() {
        return 1.0f;
    }

    @Override
    public String getErrorMessage() {
        return "It must not be equal to 0";
    }

    @Override
    public RecipeProperty<Float> getProperty() {
        return this;
    }
}
