package tkcy.tktech.api.recipes.logic;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;

public interface IRecipeLogicContainer extends IRecipePropertiesValueMap {

    @NotNull
    AbstractRecipeLogic getAbstractRecipeLogic();

    @Nullable
    IRecipeLogicContainer getInstance(RecipeLogicType recipeLogicType);

    boolean hasRecipeLogicType(RecipeLogicType recipeLogicType);

    /**
     * Adds itemStacks that are not in the inventory but in the recipe properties of the provided {@code recipe}.
     * This happens after those recipe properties were ensured.
     * 
     * @param inputInventory the {@code handler} to add the missing stacks to.
     * @return whether it succeeded.
     */
    default boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        return true;
    }

    default boolean canRecipeLogicProgress() {
        return true;
    }

    default void resetLogic() {}

    default void invalidate(IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {}

    default void serializeRecipeLogic(@NotNull NBTTagCompound compound) {}

    default void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {}

    default void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks) {}

    default void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                                     IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {}

    default void postSetupRecipe(@NotNull Recipe recipe) {}

    default MetaTileEntity getMetaTileEntity() {
        return getAbstractRecipeLogic().getMetaTileEntity();
    }

    default World getWorld() {
        return getMetaTileEntity().getWorld();
    }
}
