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
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;

import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.common.metatileentities.primitive.PartsWorkerMTE;
import tkcy.simpleaddon.modules.ToolsModule;

public class ToolRecipeLogic extends PrimitiveLogic {

    protected ToolsModule.GtTool tool;

    public ToolRecipeLogic(PartsWorkerMTE tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
    }

    @Override
    protected boolean canWorkWithInputs() {
        boolean lb = super.canWorkWithInputs();
        TKCYSALog.logger.info("CAN WORK ? : " + lb);
        return lb;
    }

    public void startWorking(ToolsModule.GtTool gtTool) {
        this.tool = gtTool;
        this.canRecipeProgress = canProgressRecipe();
        if (progressTime > 0) {
            TKCYSALog.logger.info("updateRecipeProgress()");
            updateRecipeProgress();
        }

        // check everything that would make a recipe never start here.
        if (progressTime == 0 && shouldSearchForRecipes()) {
            trySearchNewRecipe();
        }
        if (wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
            setActive(false);
        }

        TKCYSALog.logger.info("###################");
        TKCYSALog.logger.info("###################");
        TKCYSALog.logger.info("###################");
    }

    @Override
    public void update() {
        // TODO might need longer time
        /*
         * World world = getMetaTileEntity().getWorld();
         * if (world != null && !world.isRemote) {
         * if (workingEnabled) {
         * if (getMetaTileEntity().getOffsetTimer() % 20 == 0) {
         * this.canRecipeProgress = canProgressRecipe();
         * TKCYSALog.logger.info("canRecipeProgress : " + canRecipeProgress);
         * TKCYSALog.logger.info("progressTime : " + progressTime);
         * TKCYSALog.logger.info("maxProgressTime : " + maxProgressTime);
         * TKCYSALog.logger.info("hasNotifiedInputs() : " + hasNotifiedInputs());
         * 
         * if (progressTime > 0) {
         * updateRecipeProgress();
         * }
         * 
         * // check everything that would make a recipe never start here.
         * if (progressTime == 0 && shouldSearchForRecipes()) {
         * trySearchNewRecipe();
         * }
         * if (wasActiveAndNeedsUpdate) {
         * this.wasActiveAndNeedsUpdate = false;
         * setActive(false);
         * }
         * TKCYSALog.logger.info("########################");
         * TKCYSALog.logger.info("########################");
         * }
         * }
         * }
         * 
         */
    }

    @Override
    protected boolean canFitNewOutputs() {
        return true;
    }

    @Override
    protected boolean checkPreviousRecipe() {
        if (this.previousRecipe == null) return false;
        return this.previousRecipe.matches(false, getInputInventory(), getInputTank());
    }

    // Removed canFitInputs()
    @Override
    protected boolean shouldSearchForRecipes() {
        return canFitNewOutputs();
    }

    @Nullable
    protected Recipe findRecipe() {
        List<ItemStack> inventoryStacks = GTUtility.itemHandlerToList(getInputInventory());
        List<FluidStack> inventoryFluids = GTUtility.fluidHandlerToList(getInputTank());
        /*
         * getRecipeMap().getRecipeList().stream()
         * .filter(this::checkRecipe)
         * .filter(recipe -> recipe.getInputs().)
         * 
         * 
         */
        //return getRecipeMap().find(inventoryStacks, inventoryFluids, recipe -> true);
        return getRecipeMap().findRecipe(getMaxVoltage(), inventoryStacks, inventoryFluids, false);
    }

    // TODO SHOULD BE AROUND HERE
    @Override
    protected void trySearchNewRecipe() {
        Recipe currentRecipe;
        IItemHandlerModifiable importInventory = getInputInventory();
        IMultipleTankHandler importFluids = getInputTank();

        // see if the last recipe we used still works
        if (checkPreviousRecipe()) {
            currentRecipe = this.previousRecipe;
            // If there is no active recipe, then we need to find one.
        } else {

            currentRecipe = findRecipe(this.getMaxVoltage(), importInventory, importFluids);

        }
        // If a recipe was found, then inputs were valid. Cache found recipe.
        if (currentRecipe != null) {
            this.previousRecipe = currentRecipe;
            currentRecipe.getOutputs().forEach(
                    itemStack -> TKCYSALog.logger.info("recipe output : " + TKCYSALog.itemStackToString(itemStack)));
        }
        this.invalidInputsForRecipes = (currentRecipe == null);

        // proceed if we have a usable recipe.
        if (currentRecipe != null && checkRecipe(currentRecipe)) {
            TKCYSALog.logger.info("checkRecipe() must be true");
            prepareRecipe(currentRecipe);
        }
        TKCYSALog.logger.info("did try to search for recipe");
    }

    // Removed energy stuff related
    // Increments progressTime which is toolUses here
    @Override
    protected void updateRecipeProgress() {
        // as recipe starts with progress on 1 this has to be > only not => to compensate for it
        if (++progressTime > maxProgressTime) {
            completeRecipe();
        }
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe) {
        return recipe.hasProperty(ToolProperty.getInstance()) && recipe.hasProperty(ToolUsesProperty.getInstance()) &&
                super.checkRecipe(recipe);
    }

    // Removed parallel logic
    @Override
    public boolean prepareRecipe(Recipe recipe, IItemHandlerModifiable inputInventory,
                                 IMultipleTankHandler inputFluidInventory) {
        recipe = Recipe.trimRecipeOutputs(recipe, getRecipeMap(), metaTileEntity.getItemOutputLimit(),
                metaTileEntity.getFluidOutputLimit());

        if (recipe != null && setupAndConsumeRecipeInputs(recipe, inputInventory, inputFluidInventory)) {
            setupRecipe(recipe);
            return true;
        }
        return false;
    }

    // TODO
    @Override
    protected boolean setupAndConsumeRecipeInputs(@NotNull Recipe recipe,
                                                  @NotNull IItemHandlerModifiable importInventory,
                                                  @NotNull IMultipleTankHandler importFluids) {
        IItemHandlerModifiable exportInventory = getOutputInventory();
        IMultipleTankHandler exportFluids = getOutputTank();

        // We have already trimmed outputs and chanced outputs at this time
        // Attempt to merge all outputs + chanced outputs into the output bus, to prevent voiding chanced outputs
        if (!metaTileEntity.canVoidRecipeItemOutputs() &&
                !GTTransferUtils.addItemsToItemHandler(exportInventory, true, recipe.getAllItemOutputs())) {
            this.isOutputsFull = true;
            return false;
        }

        // We have already trimmed fluid outputs at this time
        if (!metaTileEntity.canVoidRecipeFluidOutputs() &&
                !GTTransferUtils.addFluidsToFluidHandler(exportFluids, true, recipe.getAllFluidOutputs())) {
            this.isOutputsFull = true;
            return false;
        }

        this.isOutputsFull = false;
        if (recipe.matches(true, importInventory, importFluids)) {
            this.metaTileEntity.addNotifiedInput(importInventory);
            return true;
        }
        return false;
    }

    /**
     * Represents recipe {@code maxProgress}
     */
    protected int getToolUses(Recipe recipe) {
        return ToolUsesProperty.getInstance().getValueFromRecipe(recipe);
    }

    protected ToolsModule.GtTool getTool(Recipe recipe) {
        return ToolProperty.getInstance().getValueFromRecipe(recipe);
    }


    // Removed parallel stuff
    @Override
    protected void setupRecipe(Recipe recipe) {
        this.progressTime = 1;
        setMaxProgress(this.getToolUses(recipe));
        this.tool = getTool(recipe);
        this.fluidOutputs = GTUtility
                .copyFluidList(recipe.getAllFluidOutputs());
        this.itemOutputs = GTUtility
                .copyStackList(recipe.getAllItemOutputs());
        if (this.wasActiveAndNeedsUpdate) {
            this.wasActiveAndNeedsUpdate = false;
        } else {
            this.setActive(true);
        }
    }

    @Override
    protected void completeRecipe() {
        this.outputRecipeOutputs();
        this.progressTime = 0;
        setMaxProgress(0);
        this.fluidOutputs = null;
        this.itemOutputs = null;
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

    // Does not need energy
    @Override
    public boolean isHasNotEnoughEnergy() {
        return false;
    }

    @NotNull
    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("WorkEnabled", workingEnabled);
        compound.setBoolean("CanRecipeProgress", canRecipeProgress);
        if (progressTime > 0) {
            compound.setInteger("Progress", progressTime);
            compound.setInteger("MaxProgress", maxProgressTime);
            NBTTagList itemOutputsList = new NBTTagList();
            for (ItemStack itemOutput : itemOutputs) {
                itemOutputsList.appendTag(itemOutput.writeToNBT(new NBTTagCompound()));
            }
            NBTTagList fluidOutputsList = new NBTTagList();
            for (FluidStack fluidOutput : fluidOutputs) {
                fluidOutputsList.appendTag(fluidOutput.writeToNBT(new NBTTagCompound()));
            }
            compound.setTag("ItemOutputs", itemOutputsList);
            compound.setTag("FluidOutputs", fluidOutputsList);
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.workingEnabled = compound.getBoolean("WorkEnabled");
        this.canRecipeProgress = compound.getBoolean("CanRecipeProgress");
        this.progressTime = compound.getInteger("Progress");
        this.isActive = false;
        if (progressTime > 0) {
            this.isActive = true;
            this.maxProgressTime = compound.getInteger("MaxProgress");
            NBTTagList itemOutputsList = compound.getTagList("ItemOutputs", Constants.NBT.TAG_COMPOUND);
            this.itemOutputs = NonNullList.create();
            for (int i = 0; i < itemOutputsList.tagCount(); i++) {
                this.itemOutputs.add(new ItemStack(itemOutputsList.getCompoundTagAt(i)));
            }
            NBTTagList fluidOutputsList = compound.getTagList("FluidOutputs", Constants.NBT.TAG_COMPOUND);
            this.fluidOutputs = new ArrayList<>();
            for (int i = 0; i < fluidOutputsList.tagCount(); i++) {
                this.fluidOutputs.add(FluidStack.loadFluidStackFromNBT(fluidOutputsList.getCompoundTagAt(i)));
            }
        }
    }
}
