package tkcy.simpleaddon.api.recipes.logic;

import static gregtech.api.recipes.logic.OverclockingLogic.subTickNonParallelOC;

import java.util.*;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.OCParams;
import gregtech.api.recipes.logic.OCResult;
import gregtech.api.recipes.properties.RecipePropertyStorage;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;

import tkcy.simpleaddon.api.recipes.helpers.RecipeSearchHelpers;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public abstract class OnBlockRecipeLogic extends AbstractRecipeLogic implements IExtraRecipeLogic {

    private boolean useEnergy;
    protected Supplier<IEnergyContainer> energyContainer;
    private final Map<IRecipePropertyHelper<?>, Object> recipeParameters = new HashMap<>();

    public OnBlockRecipeLogic(MetaTileEntity tileEntity, Supplier<IEnergyContainer> energyContainer,
                              RecipeMap<?>... recipeMaps) {
        super(tileEntity, recipeMaps[0]);
        if (useEnergy) {
            setMaximumOverclockVoltage(getMaxVoltage());
            this.energyContainer = energyContainer;
        }
    }

    @Override
    public boolean consumesEnergy() {
        return useEnergy;
    }

    private boolean useToolLogic() {
        return this instanceof IToolRecipeLogic;
    }

    private boolean useInWorldLogic() {
        return this instanceof IInWorldRecipeLogic;
    }

    @Override
    public void update() {
        if (!useToolLogic()) super.update();
    }

    public void runToolRecipeLogic(ToolsModule.GtTool gtTool) {
        if (!useToolLogic()) return;
        World world = getMetaTileEntity().getWorld();
        if (world != null && !world.isRemote) {
            if (isWorkingEnabled()) {
                IToolRecipeLogic toolLogic = IToolRecipeLogic.getToolRecipeLogic(this);
                toolLogic.setCurrentTool(gtTool);
                setActive(true);

                if (getProgress() > 0) {
                    canRecipeProgress = toolLogic.canToolRecipeLogicProgress(gtTool) &&
                            canProgressRecipe();
                    updateRecipeProgress();
                    if (getProgress() == getMaxProgress()) {
                        completeRecipe();
                        return;
                    }
                }

                if (getProgress() == 0 && shouldSearchForRecipes()) {
                    trySearchNewRecipe();
                }
            }

            if (wasActiveAndNeedsUpdate) {
                wasActiveAndNeedsUpdate = false;
                setActive(false);
            }
        }
    }

    @Override
    protected void trySearchNewRecipe() {
        updateRecipeParameters(this.recipeParameters);
        super.trySearchNewRecipe();
    }

    @Override
    public void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters) {
        if (useToolLogic()) {
            IToolRecipeLogic toolLogic = IToolRecipeLogic.getToolRecipeLogic(this);
            toolLogic.updateRecipeToolParameters(recipeParameters);
        }
        if (useInWorldLogic()) {
            IInWorldRecipeLogic inWorldLogic = IInWorldRecipeLogic.getInWorldRecipeLogic(this);
            inWorldLogic.updateRecipeInWorldParameters(recipeParameters);
        }
    }

    @Override
    public @NotNull AbstractRecipeLogic getLogic() {
        return this;
    }

    @Override
    protected boolean canProgressRecipe() {
        boolean canProgress = super.canProgressRecipe();
        if (canProgress && useInWorldLogic()) {
            return IInWorldRecipeLogic.getInWorldRecipeLogic(this).canInWorldRecipeProgress();
        }
        return canProgress;
    }

    @Override
    @Nullable
    protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        RecipeMap<?> map = getRecipeMap();
        if (map == null || !isRecipeMapValid(map)) {
            return null;
        }

        List<FluidStack> fluidStacks = getFluidStackListInventory();
        updateRecipeParameters(this.recipeParameters);
        return RecipeSearchHelpers.findFirstRecipeWithProperties(getRecipeMap(), this.recipeParameters);
    }

    private List<FluidStack> getFluidStackListInventory() {
        return GTUtility.fluidHandlerToList(getInputTank());
    }

    @Override
    protected boolean checkPreviousRecipe() {
        return super.checkPreviousRecipe();
    }

    @Override
    protected void outputRecipeOutputs() {
        if (useInWorldLogic()) {
            IInWorldRecipeLogic.getInWorldRecipeLogic(this).outputRecipeStacks(itemOutputs);
            GTTransferUtils.addFluidsToFluidHandler(getOutputTank(), false, fluidOutputs);
        } else super.outputRecipeOutputs();
    }

    /**
     * Skips {@link #prepareRecipe(Recipe recipe, IItemHandlerModifiable inputInventory,
     * IMultipleTankHandler inputFluidInventory)} because parallel logic is not used
     * and some recipe inputs are added to the input inventory right before
     * {@link Recipe#matches(boolean, IItemHandlerModifiable, IMultipleTankHandler)} is called.
     */
    @Override
    public boolean prepareRecipe(Recipe recipe) {
        recipe = Recipe.trimRecipeOutputs(recipe, getRecipeMap(), metaTileEntity.getItemOutputLimit(),
                metaTileEntity.getFluidOutputLimit());

        if (useInWorldLogic()) {
            IInWorldRecipeLogic logic = IInWorldRecipeLogic.getInWorldRecipeLogic(this);

            if (logic.doesNeedInWorldBlock()) {

                ItemStack input = logic.getInputRecipeInWorldBlockStack(recipe);
                if (input == null) return false;
                input.setCount(1);
                logic.setInputRecipeInWorldBlockStack(input);

                if (logic.addInWorldInputToInventory(getInputInventory(), true)) {
                    logic.addInWorldInputToInventory(getInputInventory(), false);
                } else return false;
            }
            if (logic.doesPlaceOutputBlock()) {
                ItemStack stackToOutput = logic.getOutputRecipeInWorldBlockStack(recipe);
                logic.setOutputRecipeInWorldBlockStack(stackToOutput);
            }
        }

        if (useToolLogic()) {
            IToolRecipeLogic logic = IToolRecipeLogic.getToolRecipeLogic(this);
            logic.setRecipeTool(recipe);
            logic.setRecipeToolUses(recipe);
            if (logic.addToolStackToInventory(getInputInventory(), true)) {
                logic.addToolStackToInventory(getInputInventory(), false);
            } else return false;
        }

        if (recipe != null) {
            recipe = setupAndConsumeRecipeInputs(recipe, getInputInventory(), getInputTank());
            if (recipe != null) {
                setupRecipe(recipe);
                return true;
            }
        }
        return false;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (useInWorldLogic()) IInWorldRecipeLogic.resetInWorldLogic(this);
        if (useToolLogic()) IToolRecipeLogic.resetToolLogic(this);
        this.recipeParameters.clear();
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        if (useInWorldLogic()) IInWorldRecipeLogic.resetInWorldLogic(this);
        if (useToolLogic()) IToolRecipeLogic.resetToolLogic(this);
        this.recipeParameters.clear();
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = super.serializeNBT();
        if (!isWorking()) return tagCompound;
        if (useInWorldLogic()) {
            IInWorldRecipeLogic.getInWorldRecipeLogic(this).serializeInWorldRecipeLogic(tagCompound);
        }
        if (useToolLogic()) {
            IToolRecipeLogic.getToolRecipeLogic(this).serializeToolRecipeLogic(tagCompound);
        }
        return tagCompound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        if (useInWorldLogic()) {
            IInWorldRecipeLogic.getInWorldRecipeLogic(this).deserializeInWorldRecipeLogic(compound);
        }
        if (useToolLogic()) {
            IToolRecipeLogic.getToolRecipeLogic(this).deserializeToolRecipeLogic(compound);
        }
    }

    // Energy stuff

    @Override
    protected long getEnergyInputPerSecond() {
        return useEnergy ? energyContainer.get().getInputPerSec() : Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return useEnergy ? energyContainer.get().getEnergyStored() : Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return useEnergy ? energyContainer.get().getEnergyCapacity() : Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(long recipeEUt, boolean simulate) {
        if (!useEnergy) return true;
        long resultEnergy = getEnergyStored() - recipeEUt;
        if (resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            if (!simulate) energyContainer.get().changeEnergy(-recipeEUt);
            return true;
        }
        return false;
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return useEnergy ? super.getMaximumOverclockVoltage() : GTValues.V[GTValues.LV];
    }

    @Override
    public long getMaxVoltage() {
        return useEnergy ? Math.max(energyContainer.get().getInputVoltage(), energyContainer.get().getOutputVoltage()) :
                GTValues.LV;
    }

    @Override
    protected void runOverclockingLogic(@NotNull OCParams ocParams, @NotNull OCResult ocResult,
                                        @NotNull RecipePropertyStorage propertyStorage, long maxVoltage) {
        subTickNonParallelOC(ocParams, ocResult, maxVoltage, getOverclockingDurationFactor(),
                getOverclockingVoltageFactor());
    }
}
