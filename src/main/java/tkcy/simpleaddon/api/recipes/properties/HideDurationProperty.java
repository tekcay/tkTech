package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import gregtech.api.recipes.properties.RecipeProperty;
import net.minecraft.client.Minecraft;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.util.EnumValidationResult;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import org.jetbrains.annotations.NotNull;
import tkcy.simpleaddon.modules.RecipePropertiesKey;

public class HideDurationProperty extends RecipeProperty<Boolean> implements RecipePropertyHelper<Boolean> {

    public static final String KEY = RecipePropertiesKey.HIDE_DURATION_KEY;
    private static HideDurationProperty INSTANCE;
    private HideDurationProperty() {
        super(KEY, Boolean.class);
    }

    @NotNull
    public static HideDurationProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HideDurationProperty();
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
    public RecipeBuilder<?> testAndApplyPropertyValue(Boolean valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
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
