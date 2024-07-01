package tkcy.simpleaddon.api.recipes.logic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;

import tkcy.simpleaddon.api.recipes.RecipeSearchHelpers;
import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.common.metatileentities.primitive.PartsWorkerMTE;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.ToolsModule;

public class ToolRecipeLogic extends PrimitiveLogic {

    protected ToolsModule.GtTool tool;
    protected List<ItemStack> itemStackListInventory;
    protected List<FluidStack> fluidStackListInventory;

    public ToolRecipeLogic(PartsWorkerMTE tileEntity, @NotNull RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
        this.itemStackListInventory = new ArrayList<>();
        this.fluidStackListInventory = new ArrayList<>();
    }

    public void startWorking(ToolsModule.GtTool gtTool) {
        TKCYSALog.logger.info("######################");
        TKCYSALog.logger.info("######################");
        TKCYSALog.logger.info("progressTime : " + progressTime);
        TKCYSALog.logger.info("maxProgressTime : " + maxProgressTime);

        this.setActive(true);
        this.tool = gtTool;
        // TODO
        // this.canRecipeProgress = canProgressRecipe();
        if (progressTime > 0) {
            progressTime++;
            if (progressTime == maxProgressTime) {
                TKCYSALog.logger.info("complete recipe");
                completeRecipe();
                return;
            }
        }

        // check everything that would make a recipe never start here.
        if (progressTime == 0) {
            TKCYSALog.logger.info("update inventories");

            updateFluidStackListInventory();
            updateItemStackListInventory();

            TKCYSALog.logger.info("trySearchNewRecipe");
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

    // Spawns items in world, fluids remain in the mte fluidOutput inventory
    @Override
    protected void outputRecipeOutputs() {
        BlockPos metatileEntityBlockPos = this.metaTileEntity.getPos();
        World world = this.metaTileEntity.getWorld();
        this.itemOutputs.forEach(itemStack -> Block.spawnAsEntity(world, metatileEntityBlockPos, itemStack));
        GTTransferUtils.addFluidsToFluidHandler(getOutputTank(), false, fluidOutputs);
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
        if (checkPreviousRecipe()) {
            currentRecipe = this.previousRecipe;
        } else {
            TKCYSALog.logger.info("search recipe");
            currentRecipe = researchRecipe();
            // currentRecipe = getRecipeMap().findRecipe(getMaxVoltage(), this.itemStackListInventory,
            // this.fluidStackListInventory);
        }

        // If a recipe was found, then inputs were valid. Cache found recipe.
        if (currentRecipe != null) this.previousRecipe = currentRecipe;

        this.invalidInputsForRecipes = (currentRecipe == null);

        if (currentRecipe == null) {
            TKCYSALog.logger.info("current recipe is null");
            return;
        }
        if (!checkCleanroomRequirement(currentRecipe)) return;

        TKCYSALog.logger.info("preparing recipe");
        prepareRecipe(currentRecipe);
    }

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
            TKCYSALog.logger.info("setupRecipe");
            setupRecipe(recipe);
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
            TKCYSALog.logger.info("matches");
            this.metaTileEntity.addNotifiedInput(getInputInventory());
            return true;
        }
        TKCYSALog.logger.info("did not work");
        return false;
    }

    // TODO
    // @Override
    // protected boolean setupAndConsumeRecipeInputs(@NotNull Recipe recipe,
    // @NotNull IItemHandlerModifiable importInventory,
    // @NotNull IMultipleTankHandler importFluids) {
    // TKCYSALog.logger.info("setupAndConsumeRecipeInputs");
    //
    // this.isOutputsFull = false;
    // if (recipe.matches(true, this.itemStackListInventory, this.fluidStackListInventory)) {
    // TKCYSALog.logger.info("matches");
    // this.metaTileEntity.addNotifiedInput(importInventory);
    // return true;
    // }
    // TKCYSALog.logger.info("did not work");
    // return false;
    // }

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
    @Override
    protected void setupRecipe(Recipe recipe) {
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
        if (progressTime > 0) {
            compound.setInteger(NBTLabel.CURRENT_TOOL_USES.name(), progressTime);
            compound.setInteger(NBTLabel.RECIPE_TOOL_USES.name(), maxProgressTime);
            NBTTagList itemOutputsList = new NBTTagList();
            for (ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            NBTTagList fluidOutputsList = new NBTTagList();
            for (FluidStack fluidOutput : fluidOutputs) {
                fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag(NBTLabel.ITEM_OUTPUTS.name(), itemOutputsList);
            compound.setTag(NBTLabel.FLUID_OUTPUTS.name(), fluidOutputsList);
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
            NBTTagList itemOutputsList = compound.getTagList(NBTLabel.ITEM_OUTPUTS.name(), Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for (int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
            NBTTagList fluidOutputsList = compound.getTagList(NBTLabel.FLUID_OUTPUTS.name(),
                    Constants.NBT.TAG_COMPOUND);
            this.fluidOutputs = new ArrayList<>();
            for (int i = 0; i < fluidOutputsList.tagCount(); i++) {
                this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
            }
        }
    }

    @Override
    protected void updateRecipeProgress() {}

    @Override
    public void update() {}
}
