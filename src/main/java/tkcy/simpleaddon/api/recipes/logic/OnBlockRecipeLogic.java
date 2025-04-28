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
import gregtech.api.util.GTUtility;

import lombok.Getter;
import tkcy.simpleaddon.api.recipes.helpers.RecipeSearchHelpers;
import tkcy.simpleaddon.api.recipes.logic.newway.*;
import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;
import tkcy.simpleaddon.api.utils.item.ItemHandlerHelpers;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public abstract class OnBlockRecipeLogic extends AbstractRecipeLogic
                                         implements IExtraRecipeLogic, IRecipePropertiesValueMap, IToolRecipeLogic {

    protected Supplier<IEnergyContainer> energyContainer;
    private final Map<IRecipePropertyHelper<?>, Object> recipeParameters = new HashMap<>();
    @Getter
    private final IRecipeLogicContainer recipeLogicContainer;

    public OnBlockRecipeLogic(MetaTileEntity tileEntity, Supplier<IEnergyContainer> energyContainer,
                              RecipeMap<?>... recipeMaps) {
        super(tileEntity, recipeMaps[0]);
        this.recipeLogicContainer = setRecipeLogicContainer();
        if (consumesEnergy()) {
            setMaximumOverclockVoltage(getMaxVoltage());
            this.energyContainer = energyContainer;
        }
    }

    @Override
    public void update() {
        if (!recipeLogicContainer.hasRecipeLogicType(RecipeLogicType.TOOL)) super.update();
    }

    @Override
    public void runToolRecipeLogic(ToolsModule.GtTool gtTool) {
        ToolLogic toolLogic = getToolLogic();
        if (toolLogic == null) return;
        toolLogic.setCurrentTool(gtTool);

        World world = getMetaTileEntity().getWorld();
        if (world != null && !world.isRemote) {
            if (isWorkingEnabled()) {

                if (getProgress() > 0) {
                    if (!canProgressRecipe()) {
                        invalidate();
                        return;
                    }
                    updateRecipeProgress();
                    if (getProgress() == getMaxProgress()) {
                        completeRecipe();
                    }
                } else if (getProgress() == 0) {
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
        recipeLogicContainer.updateRecipeParameters(recipeParameters);
    }

    @Override
    protected boolean canProgressRecipe() {
        boolean canProgress = super.canProgressRecipe();
        if (!canProgress) return false;
        return recipeLogicContainer.canRecipeLogicProgress();
    }

    @Override
    @Nullable
    protected Recipe findRecipe(long maxVoltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        RecipeMap<?> map = getRecipeMap();
        if (map == null || !isRecipeMapValid(map)) {
            return null;
        }

        List<FluidStack> fluidStacks = GTUtility.fluidHandlerToList(getInputTank());
        List<ItemStack> itemStacks = ItemHandlerHelpers.itemHandlerToList(getInputInventory());

        recipeLogicContainer.appendToInputsForRecipeSearch(itemStacks, fluidStacks);
        updateRecipeParameters(this.recipeParameters);
        return RecipeSearchHelpers.findFirstRecipeWithProperties(
                getRecipeMap(),
                this.recipeParameters,
                itemStacks,
                fluidStacks);
    }

    @Override
    protected void outputRecipeOutputs() {
        IRecipeLogicContainer inWorldLogic = getRecipeLogicContainer().getInstance(RecipeLogicType.IN_WORLD);
        if (inWorldLogic != null) {
            inWorldLogic.outputRecipeOutputs(itemOutputs, fluidOutputs, getOutputInventory(), getOutputTank());
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

        recipeLogicContainer.prepareRecipe(recipe, getInputInventory());

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
        recipeLogicContainer.resetLogic();
        this.recipeParameters.clear();
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        recipeLogicContainer.resetLogic();
        this.recipeParameters.clear();
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = super.serializeNBT();
        if (!isWorking()) return tagCompound;
        recipeLogicContainer.serializeRecipeLogic(tagCompound);
        return tagCompound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        recipeLogicContainer.deserializeRecipeLogic(compound);
    }

    // Energy stuff

    @Override
    protected long getEnergyInputPerSecond() {
        return consumesEnergy() ? energyContainer.get().getInputPerSec() : Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return consumesEnergy() ? energyContainer.get().getEnergyStored() : Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return consumesEnergy() ? energyContainer.get().getEnergyCapacity() : Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(long recipeEUt, boolean simulate) {
        if (!consumesEnergy()) return true;
        long resultEnergy = getEnergyStored() - recipeEUt;
        if (resultEnergy >= 0L && resultEnergy <= getEnergyCapacity()) {
            if (!simulate) energyContainer.get().changeEnergy(-recipeEUt);
            return true;
        }
        return false;
    }

    @Override
    public long getMaximumOverclockVoltage() {
        return consumesEnergy() ? super.getMaximumOverclockVoltage() : GTValues.V[GTValues.LV];
    }

    @Override
    public long getMaxVoltage() {
        return consumesEnergy() ?
                Math.max(energyContainer.get().getInputVoltage(), energyContainer.get().getOutputVoltage()) :
                GTValues.LV;
    }

    @Override
    protected void runOverclockingLogic(@NotNull OCParams ocParams, @NotNull OCResult ocResult,
                                        @NotNull RecipePropertyStorage propertyStorage, long maxVoltage) {
        subTickNonParallelOC(ocParams, ocResult, maxVoltage, getOverclockingDurationFactor(),
                getOverclockingVoltageFactor());
    }
}
