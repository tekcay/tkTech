package tkcy.simpleaddon.api.recipes.logic;

import gregtech.api.GTValues;
import gregtech.api.capability.IWorkable;
import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.logic.IParallelableRecipeLogic;

public class PrimitiveLogic extends AbstractRecipeLogic implements IWorkable, IParallelableRecipeLogic {

    public PrimitiveLogic(MetaTileEntity tileEntity, RecipeMap<?> recipeMap) {
        super(tileEntity, recipeMap);
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
        return GTValues.LV;
    }

    @Override
    public boolean isAllowOverclocking() {
        return false;
    }
}
