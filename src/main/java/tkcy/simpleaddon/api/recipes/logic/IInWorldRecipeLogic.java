package tkcy.simpleaddon.api.recipes.logic;

import java.util.List;
import java.util.Map;

import gregtech.api.capability.IMultipleTankHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.InputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.OutputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.utils.BlockStateHelper;
import tkcy.simpleaddon.api.utils.BooleanHelper;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.api.utils.WorldInteractionsHelper;
import tkcy.simpleaddon.modules.NBTLabel;

public interface IInWorldRecipeLogic extends IExtraRecipeLogic {

    @NotNull
    static IInWorldRecipeLogic getInWorldRecipeLogic(AbstractRecipeLogic abstractRecipeLogic) {
        return (IInWorldRecipeLogic) abstractRecipeLogic;
    }

    boolean doesSpawnOutputItems();

    boolean doesRemoveBlock();

    @Nullable
    ItemStack getInputRecipeInWorldBlockStack();

    @Nullable
    ItemStack getOutputRecipeInWorldBlockStack();

    void setInputRecipeInWorldBlockStack(ItemStack itemStack);

    void setOutputRecipeInWorldBlockStack(ItemStack itemStack);

    @Nullable
    BlockPos getInputBlockPos();

    @Nullable
    BlockPos getOutputBlockPos();

    /**
     * Little hackery to the standard input consumption logic in used (see {@link Recipe#matches(boolean, IItemHandlerModifiable, IMultipleTankHandler)}).
     * @param inputInventory
     * @param simulate
     * @return
     */
    default boolean addInWorldInputToInventory(IItemHandler inputInventory, boolean simulate) {
        if (doesNeedInWorldBlock()) {
            ItemStack itemStack = GTTransferUtils.insertItem(inputInventory, getInputRecipeInWorldBlockStack(), simulate);
            return itemStack == null || itemStack.isEmpty();
        }
        return true;
    }

    default boolean isNotValid() {
        boolean isValid = true;
        if (doesNeedInWorldBlock()) isValid = getInputBlockPos() != null;
        if (doesPlaceOutputBlock()) isValid = getOutputBlockPos() != null;
        return !isValid;
    }

    @NotNull
    default World getWorld() {
        return getMetaTileEntity().getWorld();
    }

    default boolean doesPlaceOutputBlock() {
        return getOutputBlockPos() != null;
    }

    default boolean doesNeedInWorldBlock() {
        return getInputBlockPos() != null;
    }

    @Nullable
    default ItemStack getInWorldInputStack() {
        if (getInputBlockPos() != null) {
            return WorldInteractionsHelper.getInWorldInputStack(getWorld(), getInputBlockPos());
        } else return null;
    }

    default boolean canInWorldRecipeProgress() {
        return isInWorldInputValid();
    }

    @SuppressWarnings("ConstantConditions")
    default boolean isInWorldInputValid() {
        if (!doesNeedInWorldBlock()) return true;

        ItemStack inWorldStackRecipe = getInputRecipeInWorldBlockStack();
        ItemStack inWorldStack = getInWorldInputStack();

        if (BooleanHelper.doesAnyMatch(ItemStack::isEmpty, inWorldStack, inWorldStackRecipe)) return false;
        return inWorldStack.isItemEqual(inWorldStackRecipe);
    }

    @Nullable
    default ItemStack getInputRecipeInWorldBlockStack(@NotNull Recipe recipe) {
        if (getInputBlockPos() != null) {
            return InputBlockStateRecipeProperty.getInstance().getValueFromRecipe(recipe);
        } else return null;
    }

    @Nullable
    default ItemStack getOutputRecipeInWorldBlockStack(@NotNull Recipe recipe) {
        if (getOutputBlockPos() != null) {
            IBlockState blockState = OutputBlockStateRecipeProperty.getInstance().getValueFromRecipe(recipe);
            return BlockStateHelper.blockStateToItemStack(blockState);
        } else return null;
    }

    /**
     * if {@link #doesPlaceOutputBlock()} but the {@code outputBlockPos} is occupied by another block, it calls
     * {@link AbstractRecipeLogic#invalidateOutputs()}.
     */
    @SuppressWarnings("ConstantConditions")
    default void outputRecipeStacks(List<ItemStack> outputItemStacks) {
        if (isNotValid()) {
            TKCYSALog.logger.error("IInWorldRecipeLogic: outputRecipeStacks() failed because logic !isValid()!");
            getLogic().invalidateOutputs();
            TKCYSALog.logger.warn("IInWorldRecipeLogic: called invalidateOutputs()");
            return;
        }

        if (doesSpawnOutputItems()) {
            WorldInteractionsHelper.spawnStacks(getMetaTileEntity(), outputItemStacks);
        } else GTTransferUtils.addItemsToItemHandler(getMetaTileEntity().getExportItems(), false, outputItemStacks);

        if (doesRemoveBlock()) {
            WorldInteractionsHelper.removeBlockInWorld(getWorld(), getOutputBlockPos());
        }

        if (doesPlaceOutputBlock()) {
            if (!WorldInteractionsHelper.canPlaceBlockInWorld(getWorld(), getOutputBlockPos())) {
                getLogic().invalidateOutputs();
            }
            if (!WorldInteractionsHelper.placeBlockInWorld(getOutputRecipeInWorldBlockStack(), getWorld(),
                    getOutputBlockPos(),
                    false)) {
                getLogic().invalidateOutputs();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    default void serializeInWorldRecipeLogic(@NotNull NBTTagCompound compound) throws IllegalArgumentException {
        if (!getLogic().isWorking()) return;

        if (isNotValid()) {
            TKCYSALog.logger
                    .error("IInWorldRecipeLogic: serializeInWorldRecipeLogic() failed because logic !isValid()!");
            return;
        }

        if (doesNeedInWorldBlock()) {
            compound.setTag(NBTLabel.INPUT_IN_WORLD_STACK.toString(), getInputRecipeInWorldBlockStack().serializeNBT());
        }

        if (doesPlaceOutputBlock()) {
            compound.setTag(NBTLabel.OUTPUT_IN_WORLD_STACK.toString(),
                    getOutputRecipeInWorldBlockStack().serializeNBT());
        }
    }

    default void deserializeInWorldRecipeLogic(@NotNull NBTTagCompound compound) {
        if (doesNeedInWorldBlock()) {
            NBTTagCompound inputTag = compound.getCompoundTag(NBTLabel.INPUT_IN_WORLD_STACK.toString());
            setInputRecipeInWorldBlockStack(new ItemStack(inputTag));
        }

        if (doesPlaceOutputBlock()) {
            NBTTagCompound inputTag = compound.getCompoundTag(NBTLabel.OUTPUT_IN_WORLD_STACK.toString());
            setOutputRecipeInWorldBlockStack(new ItemStack(inputTag));
        }
    }

    default void updateRecipeInWorldParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        if (doesNeedInWorldBlock())
            recipeParameters.put(InputBlockStateRecipeProperty.getInstance(), getInWorldInputStack());
    }
}
