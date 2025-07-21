package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@WorkingTool
public class FailedOutputRecipeProperty extends RecipeProperty<ItemStack>
                                        implements IRecipePropertyHelper<ItemStack> {

    public static final String KEY = RecipePropertiesKey.FAILED_OUTPUT_KEY;
    private static FailedOutputRecipeProperty INSTANCE;

    private FailedOutputRecipeProperty() {
        super(KEY, ItemStack.class);
    }

    @NotNull
    public static FailedOutputRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FailedOutputRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        ItemStack failedOutputStack = castValue(value);
        return failedOutputStack.serializeNBT();
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return new ItemStack((NBTTagCompound) nbt);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public Predicate<ItemStack> testSuppliedValue() {
        return itemStack -> itemStack != null && !itemStack.isEmpty();
    }

    @Override
    public ItemStack getDefaultValue() {
        return ItemStack.EMPTY;
    }

    @Override
    public String getErrorMessage() {
        return "Invalid itemStack for FailedOutputRecipeProperty!";
    }

    @Override
    public RecipeProperty<ItemStack> getProperty() {
        return this;
    }
}
