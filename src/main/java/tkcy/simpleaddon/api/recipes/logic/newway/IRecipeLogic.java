package tkcy.simpleaddon.api.recipes.logic.newway;

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

public interface IRecipeLogic extends IRecipePropertiesValueMap {

    /**
     * Adds itemStacks that are not in the inventory but in the recipe properties of the provided {@code recipe}.
     * This happens after those recipe properties were ensured.
     * @param recipe
     * @param inputInventory the {@code handler} to add the missing stacks to.
     * @return whether it succeeded.
     */
    boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory);

    boolean hasRecipeLogicType(RecipeLogicType recipeLogicType);

    boolean canRecipeLogicProgress();

    void resetLogic();

    void serializeRecipeLogic(@NotNull NBTTagCompound compound);

    void deserializeRecipeLogic(@NotNull NBTTagCompound compound);

    void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks);

    void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                             IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory);

    @NotNull
    AbstractRecipeLogic getAbstractRecipeLogic();

    @Nullable
    IRecipeLogic getInstance(RecipeLogicType recipeLogicType);

    default MetaTileEntity getMetaTileEntity() {
        return getAbstractRecipeLogic().getMetaTileEntity();
    }

    default World getWorld() {
        return getMetaTileEntity().getWorld();
    }
}
