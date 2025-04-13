package tkcy.simpleaddon.api.recipes.logic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;

import tkcy.simpleaddon.api.machines.ToolLogicMetaTileEntity;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.helpers.RecipeSearchHelpers;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.modules.NBTHelpers;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.toolmodule.ToolsModule;
import tkcy.simpleaddon.modules.toolmodule.WorkingTool;

@WorkingTool
public class ToolRecipeLogic extends PrimitiveLogic {

    protected ToolsModule.GtTool tool;
    protected List<ItemStack> itemStackListInventory;
    protected List<FluidStack> fluidStackListInventory;
    protected final boolean doOutputInWorld;

    public ToolRecipeLogic(ToolLogicMetaTileEntity tileEntity, @NotNull RecipeMap<?> recipeMap,
                           boolean doOutputInWorld) {
        super(tileEntity, recipeMap);
        this.doOutputInWorld = doOutputInWorld;
        this.itemStackListInventory = new ArrayList<>();
        this.fluidStackListInventory = new ArrayList<>();
    }

    public void startWorking(ToolsModule.GtTool gtTool) {
        this.setActive(true);
        this.tool = gtTool;

        if (progressTime > 0) {
            progressTime++;
            if (progressTime == maxProgressTime) {
                completeRecipe();
                return;
            }
        }

        if (progressTime == 0) {
            updateFluidStackListInventory();
            updateItemStackListInventory();
            trySearchNewRecipe();
        }
    }

    @Override
    protected void completeRecipe() {
        this.outputRecipeOutputs();
        this.progressTime = 0;
        resetRecipeToolUses();
        setActive(false);
        this.fluidOutputs.clear();
        this.itemOutputs.clear();
        this.wasActiveAndNeedsUpdate = true;
        this.tool = null;
    }

    @Override
    protected void outputRecipeOutputs() {
        if (doOutputInWorld) {
            spawnOutputStacks();
            GTTransferUtils.addFluidsToFluidHandler(getOutputTank(), false, this.fluidOutputs);
        } else super.outputRecipeOutputs();
    }

    /**
     * Spawns items in world, fluids remain in the mte fluidOutput inventory
     */
    protected void spawnOutputStacks() {
        BlockPos metaTileEntityBlockPos = this.metaTileEntity.getPos();
        World world = this.metaTileEntity.getWorld();
        this.itemOutputs.forEach(itemStack -> Block.spawnAsEntity(world, metaTileEntityBlockPos, itemStack));
    }

    // Removed energy stuff
    @Override
    protected boolean checkPreviousRecipe() {
        if (this.previousRecipe == null) return false;
        return this.previousRecipe.matches(false, this.itemStackListInventory, this.fluidStackListInventory);
    }

    // Removed canFitInputs()
    @Override
    protected boolean shouldSearchForRecipes() {
        return canFitNewOutputs();
    }

    @Override
    protected boolean canFitNewOutputs() {
        return true;
    }

    // Does not need energy
    @Override
    public boolean isHasNotEnoughEnergy() {
        return false;
    }

    @Override
    protected void trySearchNewRecipe() {
        Recipe currentRecipe;

        // see if the last recipe we used still works
        if (checkPreviousRecipe()) currentRecipe = this.previousRecipe;
        else currentRecipe = researchRecipe();

        // If a recipe was found, then inputs were valid. Cache found recipe.
        if (currentRecipe != null) this.previousRecipe = currentRecipe;

        this.invalidInputsForRecipes = (currentRecipe == null);
        if (currentRecipe == null) return;
        if (!checkCleanroomRequirement(currentRecipe)) return;
        prepareRecipe(currentRecipe);
    }

    @SuppressWarnings(value = "all")
    @Nullable
    protected Recipe researchRecipe() {
        return RecipeSearchHelpers.findRecipeWithTool(getRecipeMap(), this.tool, this.itemStackListInventory,
                this.fluidStackListInventory);
    }

    // Removed parallel logic
    @Override
    public boolean prepareRecipe(Recipe recipe) {
        recipe = Recipe.trimRecipeOutputs(recipe, getRecipeMap(), metaTileEntity.getItemOutputLimit(),
                metaTileEntity.getFluidOutputLimit());

        if (recipe != null && tryConsumeRecipeInputs(recipe)) {
            setupToolRecipe(recipe);
            return true;
        }
        return false;
    }

    protected boolean tryConsumeRecipeInputs(@NotNull Recipe recipe) {
        IMultipleTankHandler exportFluids = getOutputTank();

        // We have already trimmed fluid outputs at this time
        if (!metaTileEntity.canVoidRecipeFluidOutputs() &&
                !GTTransferUtils.addFluidsToFluidHandler(exportFluids, true, recipe.getAllFluidOutputs())) {
            this.isOutputsFull = true;
            return false;
        }

        this.isOutputsFull = false;

        if (recipe.matches(true, this.itemStackListInventory, this.fluidStackListInventory)) {
            this.metaTileEntity.addNotifiedInput(getInputInventory());
            return true;
        }

        return false;
    }

    /**
     * Sets {@code maxProgress} from the {@link ToolProperty} in the {@link ToolRecipeBuilder}. Just used for the label.
     */
    protected void setRecipeToolUses(Recipe recipe) {
        setMaxProgress(ToolUsesProperty.getInstance().getValueFromRecipe(recipe));
    }

    /**
     * Sets {@code maxProgress} to 0. Just used for the label.
     */
    protected void resetRecipeToolUses() {
        setMaxProgress(0);
    }

    /**
     * Set {@code progressTime} to 1. Just used for the label.
     */
    protected void initToolUses() {
        this.progressTime = 1;
    }

    protected void updateItemStackListInventory() {
        this.itemStackListInventory = RecipeSearchHelpers.getAppendedInputsTool(getInputInventory(), this.tool);
    }

    protected void updateFluidStackListInventory() {
        this.fluidStackListInventory = GTUtility.fluidHandlerToList(getInputTank());
    }

    // Removed parallel stuff
    protected void setupToolRecipe(@NotNull Recipe recipe) {
        initToolUses();
        setRecipeToolUses(recipe);
        this.tool = ToolProperty.getInstance().getValueFromRecipe(recipe);
        this.fluidOutputs = GTUtility.copyFluidList(recipe.getAllFluidOutputs());
        this.itemOutputs = GTUtility.copyStackList(recipe.getAllItemOutputs());

        if (this.wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
        } else {
            this.setActive(true);
        }
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("WorkEnabled", workingEnabled);
        compound.setBoolean("CanRecipeProgress", canRecipeProgress);
        if (this.progressTime > 0) {
            compound.setInteger(NBTLabel.CURRENT_TOOL_USES.name(), this.progressTime);
            compound.setInteger(NBTLabel.RECIPE_TOOL_USES.name(), this.maxProgressTime);

            NBTHelpers.itemStacksSerializer.accept(compound, this.itemOutputs, NBTLabel.ITEM_OUTPUTS);
            NBTHelpers.fluidStacksSerializer.accept(compound, this.fluidOutputs, NBTLabel.FLUID_OUTPUTS);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.workingEnabled = compound.getBoolean("WorkEnabled");
        this.canRecipeProgress = compound.getBoolean("CanRecipeProgress");
        this.progressTime = compound.getInteger(NBTLabel.CURRENT_TOOL_USES.name());
        this.isActive = false;
        if (progressTime > 0) {
            this.isActive = true;
            this.maxProgressTime = compound.getInteger(NBTLabel.RECIPE_TOOL_USES.name());
            this.itemOutputs = NBTHelpers.getDeserializedItemStacks(compound, NBTLabel.ITEM_OUTPUTS);
            this.fluidOutputs = NBTHelpers.getDeserializedFluidStacks(compound, NBTLabel.FLUID_OUTPUTS);
        }
    }

    @Override
    protected void updateRecipeProgress() {}

    @Override
    public void update() {}
}
