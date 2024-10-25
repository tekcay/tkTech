package tkcy.simpleaddon.api.recipes.logic;

import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;

import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.recipes.properties.HeatInputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.RecipePropertyHelper;
import tkcy.simpleaddon.modules.capabilitiesmodule.Machines;

public class HeatLogic extends AbstractRecipeLogic implements Machines.HeatMachine {

    private int heatRecipeValue;
    private final boolean consumesHeat;
    private final RecipePropertyHelper<Integer> recipeProperty;

    public HeatLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean consumesHeat) {
        super(tileEntity, recipeMap);
        this.consumesHeat = consumesHeat;
        this.recipeProperty = consumesHeat ? HeatInputRecipeProperty.getInstance() :
                HeatOutputRecipeProperty.getInstance();
    }

    public void setHeatRecipeValue(Recipe recipe) {
        if (recipe.getRecipePropertyStorage() != null) {
            this.heatRecipeValue = recipeProperty.getValueFromRecipe(recipe);
        }
    }

    @Override
    protected void trySearchNewRecipe() {
        if (getHeatContainer() == null) return;
        if (consumesHeat && getHeatContainer().isEmpty()) return;
        super.trySearchNewRecipe();
    }

    @Override
    @SuppressWarnings(value = "all")
    public void setupRecipe(Recipe recipe) {
        this.setHeatRecipeValue(recipe);
        if (consumesHeat && getHeatContainer().getValue() < this.heatRecipeValue) return;
        super.setupRecipe(recipe);
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        if (getHeatContainer() != null) {
            getHeatContainer().increaseValue(consumesHeat ? -heatRecipeValue : heatRecipeValue);
        }
    }

    @Override
    protected long getEnergyInputPerSecond() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyStored() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long getEnergyCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected boolean drawEnergy(int recipeEUt, boolean simulate) {
        return true;
    }

    @Override
    public long getMaxVoltage() {
        return Integer.MAX_VALUE;
    }

    @Nullable
    @Override
    public HeatContainer getHeatContainer() {
        return ((Machines.HeatMachine) metaTileEntity).getHeatContainer();
    }
}
