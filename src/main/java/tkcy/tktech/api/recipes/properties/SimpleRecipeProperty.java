package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;

public abstract class SimpleRecipeProperty extends RecipeProperty<Boolean> implements IRecipePropertyHelper<Boolean> {

    protected SimpleRecipeProperty(String key, Class<Boolean> type) {
        super(key, type);
    }

    public NBTTagCompound serialize(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setBoolean(getKey(), true);
        return nbtTagCompound;
    }

    public boolean deserialize(NBTTagCompound nbtTagCompound) {
        return nbtTagCompound.hasKey(getKey());
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
