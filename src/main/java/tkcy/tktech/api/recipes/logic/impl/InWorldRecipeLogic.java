package tkcy.tktech.api.recipes.logic.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import tkcy.tktech.api.recipes.logic.IRecipeLogicContainer;
import tkcy.tktech.api.recipes.logic.IRecipePropertiesValueMap;
import tkcy.tktech.api.recipes.logic.RecipeLogicType;
import tkcy.tktech.api.recipes.properties.IRecipePropertyHelper;
import tkcy.tktech.api.recipes.properties.InputBlockStateRecipeProperty;
import tkcy.tktech.api.recipes.properties.OutputBlockStateRecipeProperty;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.api.utils.TkTechLog;
import tkcy.tktech.api.utils.WorldInteractionsHelper;
import tkcy.tktech.modules.NBTLabel;

@Getter
@Setter
public class InWorldRecipeLogic implements IRecipeLogicContainer, IRecipePropertiesValueMap {

    private ItemStack inputRecipeInWorldBlockStack;
    private ItemStack outputRecipeInWorldBlockStack;
    private final AbstractRecipeLogic abstractRecipeLogic;
    private final boolean doesNeedInWorldBlock;
    private final boolean doesPlaceOutputBlock;
    private final boolean doesRemoveBlock;
    private final boolean doesSpawnOutputItems;

    @Getter(AccessLevel.NONE)
    private final Function<MetaTileEntity, BlockPos> inputBlockPos;
    @Getter(AccessLevel.NONE)
    private final Function<MetaTileEntity, BlockPos> outputBlockPos;

    private InWorldRecipeLogic(AbstractRecipeLogic abstractRecipeLogic, boolean doesNeedInWorldBlock,
                               boolean doesPlaceOutputBlock, boolean doesRemoveBlock, boolean doesSpawnOutputItems,
                               Function<MetaTileEntity, BlockPos> inputBlockPos,
                               Function<MetaTileEntity, BlockPos> outputBlockPos) {
        this.abstractRecipeLogic = abstractRecipeLogic;
        this.doesNeedInWorldBlock = doesNeedInWorldBlock;
        this.doesPlaceOutputBlock = doesPlaceOutputBlock;
        this.doesRemoveBlock = doesRemoveBlock;
        this.doesSpawnOutputItems = doesSpawnOutputItems;
        this.inputBlockPos = inputBlockPos;
        this.outputBlockPos = outputBlockPos;
    }

    public BlockPos getOutputBlockPos() {
        return this.inputBlockPos.apply(getMetaTileEntity());
    }

    public BlockPos getInputBlockPos() {
        return this.outputBlockPos.apply(getMetaTileEntity());
    }

    public MetaTileEntity getMetaTileEntity() {
        return abstractRecipeLogic.getMetaTileEntity();
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

        if (BooleanHelper.doesAnyMatch(itemStack -> itemStack == null || itemStack.isEmpty(), inWorldStack,
                inWorldStackRecipe))
            return false;
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
        if (doesNeedInWorldBlock) {
            ItemStack toAdd = getInputRecipeInWorldBlockStack(recipe);
            ItemStack remainder = GTTransferUtils.insertItem(inputInventory, toAdd, true);
            if (remainder != null && !remainder.isEmpty()) return false;

            GTTransferUtils.insertItem(inputInventory, toAdd, false);
            setInputRecipeInWorldBlockStack(toAdd);
        }

        if (doesPlaceOutputBlock) {
            @Nullable
            ItemStack toOutput = getOutputRecipeInWorldBlockStack(recipe);
            setOutputRecipeInWorldBlockStack(toOutput);
        }
        return true;
    }

    @Override
    public void resetLogic() {
        setOutputRecipeInWorldBlockStack(null);
        setInputRecipeInWorldBlockStack(null);
    }

    @Override
    public void invalidate(IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        resetLogic();
    }

    @Override
    public void serializeRecipeLogic(@NotNull NBTTagCompound compound) {
        if (!abstractRecipeLogic.isWorking()) return;

        if (isNotValid()) {
            TkTechLog.logger
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
    public void appendToInputsForRecipeSearch(List<ItemStack> handlerStacks, List<FluidStack> handlerFluidStacks) {
        if (doesNeedInWorldBlock) handlerStacks.add(getInWorldInputStack());
    }

    /**
     * if {@link #doesPlaceOutputBlock} but the {@code outputBlockPos} is occupied by another block, it calls
     * {@link AbstractRecipeLogic#invalidateOutputs()}.
     */
    @Override
    public void outputRecipeOutputs(List<ItemStack> outputStacks, List<FluidStack> outputFluidStacks,
                                    IItemHandler outputInventory, IMultipleTankHandler outputFluidInventory) {
        if (isNotValid()) {
            TkTechLog.logger.error("IInWorldRecipeLogic: outputRecipeStacks() failed because logic !isValid()!");
            abstractRecipeLogic.invalidateOutputs();
            TkTechLog.logger.warn("IInWorldRecipeLogic: called invalidateOutputs()");
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

        if (outputFluidStacks == null) return;
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

    public static class Builder {

        private Function<MetaTileEntity, BlockPos> inputBlockPos = null;
        private Function<MetaTileEntity, BlockPos> outputBlockPos = null;
        private final AbstractRecipeLogic abstractRecipeLogic;
        private boolean doesNeedInWorldBlock = false;
        private boolean doesPlaceOutputBlock = false;
        private boolean doesRemoveInputBlock = false;
        private boolean doesSpawnOutputItems = false;

        public Builder(@NotNull AbstractRecipeLogic abstractRecipeLogic) {
            this.abstractRecipeLogic = abstractRecipeLogic;
        }

        public Builder doesNeedInWorldBlock(@NotNull Function<MetaTileEntity, BlockPos> inputBlockPos) {
            this.doesNeedInWorldBlock = true;
            this.inputBlockPos = inputBlockPos;
            return this;
        }

        public Builder doesPlaceOutputBlock(@NotNull Function<MetaTileEntity, BlockPos> outputBlockPos) {
            this.doesPlaceOutputBlock = true;
            this.outputBlockPos = outputBlockPos;
            return this;
        }

        public Builder doesSpawnOutputItems() {
            this.doesSpawnOutputItems = true;
            return this;
        }

        public Builder doesRemoveInputBlock() {
            this.doesRemoveInputBlock = true;
            return this;
        }

        public InWorldRecipeLogic build() {
            return new InWorldRecipeLogic(abstractRecipeLogic, doesNeedInWorldBlock, doesPlaceOutputBlock,
                    doesRemoveInputBlock, doesSpawnOutputItems, inputBlockPos, outputBlockPos);
        }
    }
}
