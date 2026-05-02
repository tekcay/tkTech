package tkcy.tktech.api.recipes.properties;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.properties.RecipeProperty;

public interface ISerializableRecipeProperty<T, U extends RecipeProperty<T> & IRecipePropertyHelper<T>> {

    U getRecipeProperty();

    T castValueI(Object val);

    default NBTTagCompound serialize(@Nullable Recipe recipe, NBTTagCompound tagCompound) {
        if (recipe == null) return tagCompound;
        T value = getRecipeProperty().getValueFromRecipe(recipe);
        if (value == null) return tagCompound;
        tagCompound.setTag(getRecipeProperty().getKey(), getRecipeProperty().serialize(value));
        return tagCompound;
    }

    default NBTTagCompound serialize(NBTTagCompound tagCompound, T value) {
        tagCompound.setTag(getRecipeProperty().getKey(), getRecipeProperty().serialize(value));
        return tagCompound;
    }

    @Nullable
    default T deserialize(NBTTagCompound nbtTagCompound) {
        if (!nbtTagCompound.hasKey(getRecipeProperty().getKey())) return null;
        NBTBase nbt = nbtTagCompound.getTag(getRecipeProperty().getKey());
        return castValueI(getRecipeProperty().deserialize(nbt));
    }
}
