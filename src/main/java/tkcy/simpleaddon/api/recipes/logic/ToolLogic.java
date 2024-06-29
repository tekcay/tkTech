package tkcy.simpleaddon.api.recipes.logic;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MTETrait;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.GTTransferUtils;

import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;
import tkcy.simpleaddon.api.recipes.properties.ToolProperty;
import tkcy.simpleaddon.api.recipes.properties.ToolUsesProperty;
import tkcy.simpleaddon.common.metatileentities.primitive.PartsWorkerMTE;
import tkcy.simpleaddon.modules.NBTHelpers;
import tkcy.simpleaddon.modules.NBTLabel;
import tkcy.simpleaddon.modules.ToolsModule;

public class ToolLogic extends MTETrait {

    private int currentToolUses;
    private int recipeToolUses;
    protected boolean isWorking;
    protected List<FluidStack> fluidOutputs;
    protected NonNullList<ItemStack> itemOutputs;
    protected ToolsModule.GtTool tool;
    protected final RecipeMap<ToolRecipeBuilder> recipeMap;
    protected final PartsWorkerMTE metatileEntity;

    public ToolLogic(PartsWorkerMTE partsWorkerMTE, RecipeMap<ToolRecipeBuilder> recipeMap) {
        super(partsWorkerMTE);
        this.metatileEntity = partsWorkerMTE;
        this.recipeMap = recipeMap;
    }

    /**
     * @return the inventory to input items from
     */
    protected IItemHandlerModifiable getInputInventory() {
        return this.metatileEntity.getImportItems();
    }

    /**
     * @return the inventory to output items to
     */
    protected IItemHandlerModifiable getOutputInventory() {
        return this.metatileEntity.getExportItems();
    }

    /**
     * @return the fluid inventory to input fluids from
     */
    protected IMultipleTankHandler getInputTank() {
        return this.metatileEntity.getImportFluids();
    }

    /**
     * @return the fluid inventory to output fluids to
     */
    protected IMultipleTankHandler getOutputTank() {
        return this.metatileEntity.getExportFluids();
    }

    public void reset() {
        this.currentToolUses = 0;
        this.recipeToolUses = 0;
        this.tool = null;
        this.isWorking = false;
        this.fluidOutputs.clear();
        this.itemOutputs.clear();
    }

    public boolean checkRecipeProperties(@NotNull Recipe recipe) {
        return recipe.hasProperty(ToolProperty.getInstance()) && recipe.hasProperty(ToolUsesProperty.getInstance());
    }

    @Nullable
    protected Recipe findRecipe() {
        RecipeMap<?> map = this.recipeMap;
        if (map == null) {
            return null;
        }
        IItemHandlerModifiable importInventory = getInputInventory();
        IMultipleTankHandler importFluids = getInputTank();
        return map.findRecipe(1L, importInventory, importFluids);
    }

    public void work(@NotNull ToolsModule.GtTool gtTool) {
        this.tool = gtTool;

        Recipe currentRecipe;
        currentRecipe = findRecipe();
        if (currentRecipe != null && checkRecipeProperties(currentRecipe)) {

            this.currentToolUses++;
            if (this.currentToolUses == this.recipeToolUses) {
                completeRecipe();
            }
        }
    }

    protected void completeRecipe() {
        outputRecipeOutputs();
        reset();
    }

    /**
     * Spawn {@code itemOutputs} in world and add {@code fluidOutputs} to {@link #fluidHandler}
     */
    protected void outputRecipeOutputs() {
        BlockPos metatileEntityBlockPos = this.metatileEntity.getPos();
        World world = this.metatileEntity.getWorld();
        this.itemOutputs.forEach(itemStack -> Block.spawnAsEntity(world, metatileEntityBlockPos, itemStack));
        GTTransferUtils.addFluidsToFluidHandler(getOutputTank(), false, fluidOutputs);
    }

    @Override
    @NotNull
    public String getName() {
        return GregtechDataCodes.ABSTRACT_WORKABLE_TRAIT;
    }

    @Override
    public <T> T getCapability(Capability<T> capability) {
        return null;
    }

    @Override
    @NotNull
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        if (this.currentToolUses > 0) {
            compound.setInteger(NBTLabel.CURRENT_TOOL_USES.name(), this.currentToolUses);
            compound.setInteger(NBTLabel.RECIPE_TOOL_USES.name(), this.recipeToolUses);
            compound.setTag(NBTLabel.ITEM_OUTPUTS.name(), NBTHelpers.serializeItemOutputs(this.itemOutputs));
            compound.setTag(NBTLabel.FLUID_OUTPUTS.name(), NBTHelpers.serializeFluidOutputs(this.fluidOutputs));
        }
        return compound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        this.currentToolUses = compound.getInteger(NBTLabel.CURRENT_TOOL_USES.name());
        this.isWorking = false;
        if (this.currentToolUses > 0) {
            this.isWorking = true;
            this.recipeToolUses = compound.getInteger(NBTLabel.RECIPE_TOOL_USES.name());
            this.itemOutputs = NBTHelpers.deserializeItemOutputs(compound, NBTLabel.ITEM_OUTPUTS);
            this.fluidOutputs = NBTHelpers.deserializeFluidOutputs(compound, NBTLabel.FLUID_OUTPUTS);
        }
    }
}
