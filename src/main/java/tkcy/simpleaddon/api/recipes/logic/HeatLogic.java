package tkcy.simpleaddon.api.recipes.logic;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.recipeproperties.RecipeProperty;
import org.jetbrains.annotations.Nullable;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.machines.HeatMachine;
import tkcy.simpleaddon.api.recipes.properties.HeatInputRecipeProperty;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;

public class HeatLogic extends AbstractRecipeLogic implements HeatMachine {

    private int heatRecipeValue;
    private final boolean consumesHeat;
    private final RecipeProperty<Integer> recipeProperty;

    public HeatLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap, boolean consumesHeat) {
        super(tileEntity, recipeMap);
        this.consumesHeat = consumesHeat;
        this.recipeProperty = consumesHeat ? HeatInputRecipeProperty.getInstance() : HeatOutputRecipeProperty.getInstance();
    }

    public void setHeatRecipeValue(Recipe recipe) {
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(recipeProperty)) {
            this.heatRecipeValue = recipe.getProperty(recipeProperty, 0);
        } else this.heatRecipeValue = 0;
    }

    @Override
    public void setupRecipe(Recipe recipe) {
        super.setupRecipe(recipe);
        this.setHeatRecipeValue(recipe);
    }

    @Override
    protected void completeRecipe() {
        super.completeRecipe();
        if (getHeatContainer() != null) {
            getHeatContainer().increaseValue(consumesHeat ? - heatRecipeValue : heatRecipeValue);
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
        return ((HeatMachine) metaTileEntity).getHeatContainer();
    }
}
