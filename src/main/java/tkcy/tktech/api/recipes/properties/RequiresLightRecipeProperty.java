package tkcy.tktech.api.recipes.properties;

import java.util.Objects;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GregTechAPI;
import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;

public class RequiresLightRecipeProperty extends RecipeProperty<EnumDyeColor> implements
                                         IRecipePropertyHelper<EnumDyeColor>,
                                         ISerializableRecipeProperty<EnumDyeColor, RequiresLightRecipeProperty> {

    public static final String KEY = RecipePropertiesKey.LIGHT;
    private static RequiresLightRecipeProperty INSTANCE;

    @NotNull
    public static RequiresLightRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiresLightRecipeProperty();
            GregTechAPI.RECIPE_PROPERTIES.register(KEY, INSTANCE);
        }
        return INSTANCE;
    }

    private RequiresLightRecipeProperty() {
        super(KEY, EnumDyeColor.class);
    }

    @Override
    public Predicate<EnumDyeColor> testSuppliedValue() {
        return Objects::nonNull;
    }

    @Override
    public EnumDyeColor getDefaultValue() {
        return EnumDyeColor.BLACK;
    }

    @Override
    public String getErrorMessage() {
        return "LightRecipeProperty can not be null";
    }

    @Override
    public RecipeProperty<EnumDyeColor> getProperty() {
        return this;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagInt(castValue(value).ordinal());
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return EnumDyeColor.values()[((NBTTagInt) nbt).getInt()];
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        EnumDyeColor val = castValue(value);
        String lightColor = I18n.format(val.getTranslationKey());
        String text = I18n.format("tktech.recipe.requires_light", lightColor);
        minecraft.fontRenderer.drawString(text, x, y, color);
    }

    @Override
    public RequiresLightRecipeProperty getRecipeProperty() {
        return this;
    }

    @Override
    public EnumDyeColor castValueI(Object val) {
        return castValue(val);
    }
}
