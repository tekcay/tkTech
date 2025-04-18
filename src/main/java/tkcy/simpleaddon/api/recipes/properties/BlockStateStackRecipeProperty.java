package tkcy.simpleaddon.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.simpleaddon.api.utils.BlockStateHelper;

public abstract class BlockStateStackRecipeProperty extends RecipeProperty<ItemStack>
                                                    implements IRecipePropertyHelper<ItemStack> {

    protected BlockStateStackRecipeProperty(String key, Class<ItemStack> type) {
        super(key, type);
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        ItemStack blockStack = castValue(value);
        return blockStack.serializeNBT();
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return new ItemStack((NBTTagCompound) nbt);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {}

    @Override
    public Predicate<ItemStack> testSuppliedValue() {
        return itemStack -> {
            IBlockState block = BlockStateHelper.itemStackToBlockState(itemStack);
            return block != null;
        };
    }

    @Override
    public ItemStack getDefaultValue() {
        return ItemStack.EMPTY;
    }

    @Override
    public String getErrorMessage() {
        return "BlockStateRecipeProperty must be provided with an ItemStack that a corresponding Block!";
    }

    @Override
    public RecipeProperty<ItemStack> getProperty() {
        return this;
    }

    @Override
    public boolean areValueEquals(ItemStack recipeValue, Object valueToTest) {
        return recipeValue.isItemEqual(castValue(valueToTest));
    }
}
