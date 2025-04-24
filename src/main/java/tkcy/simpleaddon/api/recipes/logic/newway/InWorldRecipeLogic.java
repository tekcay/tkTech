package tkcy.simpleaddon.api.recipes.logic.newway;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;

import lombok.Getter;
import lombok.Setter;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.recipes.properties.InputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.OutputBlockStateRecipeProperty;
import tkcy.simpleaddon.api.utils.BooleanHelper;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.api.utils.WorldInteractionsHelper;
import tkcy.simpleaddon.modules.NBTLabel;

@Getter
@Setter
public class InWorldRecipeLogic implements IRecipeLogic, IRecipePropertiesValueMap {

    private ItemStack inputRecipeInWorldBlockStack;
    private ItemStack outputRecipeInWorldBlockStack;
    private final BlockPos inputBlockPos;
    private final BlockPos outputBlockPos;
    private final AbstractRecipeLogic abstractRecipeLogic;
    private final boolean doesNeedInWorldBlock;
    private final boolean doesPlaceOutputBlock;
    private final boolean doesRemoveBlock;
    private final boolean doesSpawnOutputItems;

    private InWorldRecipeLogic(AbstractRecipeLogic abstractRecipeLogic, boolean doesNeedInWorldBlock,
                               boolean doesPlaceOutputBlock, boolean doesRemoveBlock, boolean doesSpawnOutputItems,
                               BlockPos inputBlockPos, BlockPos outputBlockPos) {
        this.abstractRecipeLogic = abstractRecipeLogic;
        this.doesNeedInWorldBlock = doesNeedInWorldBlock;
        this.doesPlaceOutputBlock = doesPlaceOutputBlock;
        this.doesRemoveBlock = doesRemoveBlock;
        this.doesSpawnOutputItems = doesSpawnOutputItems;
        this.inputBlockPos = inputBlockPos;
        this.outputBlockPos = outputBlockPos;
    }

    public boolean isNotValid() {
        boolean isValid = true;
        if (doesNeedInWorldBlock) isValid = getInputBlockPos() != null;
        if (doesPlaceOutputBlock) isValid = getOutputBlockPos() != null;
        return !isValid;
    }

    @Nullable
    protected ItemStack getInWorldInputStack() {
        if (getInputBlockPos() != null) {
            return WorldInteractionsHelper.getInWorldInputStack(getWorld(), getInputBlockPos());
        } else return null;
    }

    @SuppressWarnings("ConstantConditions")
    protected boolean isInWorldInputValid() {
        if (!doesNeedInWorldBlock) return true;

        ItemStack inWorldStackRecipe = getInputRecipeInWorldBlockStack();
        ItemStack inWorldStack = getInWorldInputStack();

        if (BooleanHelper.doesAnyMatch(ItemStack::isEmpty, inWorldStack, inWorldStackRecipe)) return false;
        return inWorldStack.isItemEqual(inWorldStackRecipe);
    }

    @Nullable
    protected ItemStack getInputRecipeInWorldBlockStack(@NotNull Recipe recipe) {
        if (getInputBlockPos() != null) {
            return InputBlockStateRecipeProperty.getInstance().getValueFromRecipe(recipe);
        } else return null;
    }

    @Nullable
    protected ItemStack getOutputRecipeInWorldBlockStack(@NotNull Recipe recipe) {
        if (getOutputBlockPos() != null) {
            return OutputBlockStateRecipeProperty.getInstance().getValueFromRecipe(recipe);
        } else return null;
    }

    public static class Builder {

        private BlockPos inputBlockPos = null;
        private BlockPos outputBlockPos = null;
        private AbstractRecipeLogic abstractRecipeLogic;
        private boolean doesNeedInWorldBlock = false;
        private boolean doesPlaceOutputBlock = false;
        private boolean doesRemoveBlock = false;
        private boolean doesSpawnOutputItems = false;

        public static Builder init() {
            return new Builder();
        }

        public Builder baseLogic(@NotNull AbstractRecipeLogic abstractRecipeLogic) {
            this.abstractRecipeLogic = abstractRecipeLogic;
            return this;
        }

        public Builder doesNeedInWorldBlock(@NotNull BlockPos inputBlockPos) {
            this.doesNeedInWorldBlock = true;
            this.inputBlockPos = inputBlockPos;
            return this;
        }

        public Builder doesNeedInWorldBlock(@NotNull BlockPos inputBlockPos, boolean doesRemoveBlock) {
            this.doesNeedInWorldBlock = true;
            this.inputBlockPos = inputBlockPos;
            this.doesRemoveBlock = doesRemoveBlock;
            return this;
        }

        public Builder doesPlaceOutputBlock(@NotNull BlockPos outputBlockPos) {
            this.doesPlaceOutputBlock = true;
            this.outputBlockPos = outputBlockPos;
            return this;
        }

        public Builder doesSpawnOutputItems() {
            this.doesSpawnOutputItems = true;
            return this;
        }

        public InWorldRecipeLogic build() {
            return new InWorldRecipeLogic(abstractRecipeLogic, doesNeedInWorldBlock, doesPlaceOutputBlock,
                    doesRemoveBlock, doesSpawnOutputItems, inputBlockPos, outputBlockPos);
        }
    }

    /**
     * Used if {@link #doesPlaceOutputBlock}. As the block to place in world is stored both as itemStack in
     * {@link AbstractRecipeLogic#itemOutputs} and in {@link #getOutputRecipeInWorldBlockStack()}, it must be removed
     * from {@code itemOutputs}.
     */
    protected boolean removeInWorldOutputFromNormalOutput(@NotNull List<ItemStack> itemOutputs) {
        if (getOutputRecipeInWorldBlockStack() == null) return false;
        for (ItemStack itemStack : itemOutputs) {
            if (itemStack.isItemEqual(getOutputRecipeInWorldBlockStack())) {
                itemOutputs.remove(itemStack);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canRecipeLogicProgress() {
        return isInWorldInputValid();
    }

    @Override
    public boolean prepareRecipe(@NotNull Recipe recipe, IItemHandler inputInventory) {
        return false;
    }

    @Override
    public void resetLogic() {
        setOutputRecipeInWorldBlockStack(null);
        setInputRecipeInWorldBlockStack(null);
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (!abstractRecipeLogic.isWorking()) return;

        if (isNotValid()) {
            TKCYSALog.logger
                    .error("IInWorldRecipeLogic: serializeInWorldRecipeLogic() failed because logic !isValid()!");
            return;
        }

        if (doesNeedInWorldBlock) {
            compound.setTag(NBTLabel.INPUT_IN_WORLD_STACK.toString(), getInputRecipeInWorldBlockStack().serializeNBT());
        }

        if (doesPlaceOutputBlock) {
            compound.setTag(NBTLabel.OUTPUT_IN_WORLD_STACK.toString(),
                    getOutputRecipeInWorldBlockStack().serializeNBT());
        }
    }

    @Override
    public void deserializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (doesNeedInWorldBlock) {
            NBTTagCompound inputTag = compound.getCompoundTag(NBTLabel.INPUT_IN_WORLD_STACK.toString());
            setInputRecipeInWorldBlockStack(new ItemStack(inputTag));
        }

        if (doesPlaceOutputBlock) {
            NBTTagCompound outputTag = compound.getCompoundTag(NBTLabel.OUTPUT_IN_WORLD_STACK.toString());
            setOutputRecipeInWorldBlockStack(new ItemStack(outputTag));
        }
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        if (doesNeedInWorldBlock)
            recipeParameters.put(InputBlockStateRecipeProperty.getInstance(), getInWorldInputStack());
    }

    @Override
    public void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks) {}

    /**
     * if {@link #doesPlaceOutputBlock} but the {@code outputBlockPos} is occupied by another block, it calls
     * {@link AbstractRecipeLogic#invalidateOutputs()}.
     */
    @Override
    public void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                                    IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        if (isNotValid()) {
            TKCYSALog.logger.error("IInWorldRecipeLogic: outputRecipeStacks() failed because logic !isValid()!");
            abstractRecipeLogic.invalidateOutputs();
            TKCYSALog.logger.warn("IInWorldRecipeLogic: called invalidateOutputs()");
            return;
        }

        if (doesRemoveBlock) {
            WorldInteractionsHelper.removeBlockInWorld(getWorld(), getOutputBlockPos());
        }

        if (doesPlaceOutputBlock) {
            if (!removeInWorldOutputFromNormalOutput(outputStacks)) return;

            if (!WorldInteractionsHelper.canPlaceBlockInWorld(getWorld(), getOutputBlockPos())) {
                abstractRecipeLogic.invalidateOutputs();
            }
            if (!WorldInteractionsHelper.placeBlockInWorld(getOutputRecipeInWorldBlockStack(), getWorld(),
                    getOutputBlockPos(),
                    false)) {
                abstractRecipeLogic.invalidateOutputs();
            }
        }

        if (doesSpawnOutputItems) {
            WorldInteractionsHelper.spawnStacks(getMetaTileEntity(), outputStacks);
        } else GTTransferUtils.addItemsToItemHandler(getMetaTileEntity().getExportItems(), false, outputStacks);

        GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, false, outputFluidStacks);
    }

    @Override
    public @Nullable InWorldRecipeLogic getInstance(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.IN_WORLD ? this : null;
    }

    @Override
    public boolean hasRecipeLogicType(RecipeLogicType recipeLogicType) {
        return recipeLogicType == RecipeLogicType.IN_WORLD;
    }
}
