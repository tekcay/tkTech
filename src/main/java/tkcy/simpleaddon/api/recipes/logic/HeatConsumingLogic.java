package tkcy.simpleaddon.api.recipes.logic;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import org.jetbrains.annotations.Nullable;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.HeatMachine;
import tkcy.simpleaddon.api.recipes.properties.HeatOutputRecipeProperty;

public class HeatConsumingLogic extends AbstractRecipeLogic implements HeatMachine {

    private int heatRecipeValue;

    public HeatConsumingLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
    }

    public void setHeatRecipeValue(Recipe recipe) {
        if (recipe.getRecipePropertyStorage() != null && recipe.hasProperty(HeatOutputRecipeProperty.getInstance())) {
            this.heatRecipeValue = recipe.getProperty(HeatOutputRecipeProperty.getInstance(), 0);
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
            getHeatContainer().increaseValue(heatRecipeValue);
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
