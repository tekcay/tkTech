package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.api.utils.number.IsBetweenUtils;
import tkcy.tktech.modules.RecipePropertiesKey;

public class LightRecipeProperty extends RecipeProperty<Byte> implements IRecipePropertyHelper<Byte> {

    public static final String KEY = RecipePropertiesKey.LIGHT;
    private static LightRecipeProperty INSTANCE;

    @NotNull
    public static LightRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LightRecipeProperty();
        }
        return INSTANCE;
    }

    private LightRecipeProperty() {
        super(KEY, Byte.class);
    }

    @Nullable
    public EnumDyeColor getRequiredLightFromRecipe(Recipe recipe) {
        byte value = getValueFromRecipe(recipe);
        return value == getDefaultValue() ? null : EnumDyeColor.values()[value];
    }

    @Override
    public Predicate<Byte> testSuppliedValue() {
        return byt -> IsBetweenUtils.isBetweenInclusive(getDefaultValue(), EnumDyeColor.values().length - 1, byt);
    }

    @Override
    public Byte getDefaultValue() {
        return -1;
    }

    @Override
    public String getErrorMessage() {
        return String.format("LightRecipeProperty must be between incusive %d and %d", getDefaultValue(),
                EnumDyeColor.values().length - 1);
    }

    @Override
    public RecipeProperty<Byte> getProperty() {
        return this;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagByte(castValue(value));
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return ((NBTTagByte) nbt).getByte();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        byte val = castValue(value);
        if (val == getDefaultValue()) {
            minecraft.fontRenderer.drawString(I18n.format("tktech.recipe.requires_dark"), x, y, color);
        } else {
            String lightColor = I18n.format(EnumDyeColor.values()[val].getTranslationKey());
            String text = I18n.format("tktech.recipe.requires_light", lightColor);
            minecraft.fontRenderer.drawString(text, x, y, color);
        }
    }
}
