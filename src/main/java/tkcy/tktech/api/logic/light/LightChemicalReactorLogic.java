package tkcy.tktech.api.logic.light;

import java.util.function.Supplier;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.RecipeLogicEnergy;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;

import tkcy.tktech.api.recipes.properties.RequiresLightRecipeProperty;
import tkcy.tktech.api.recipes.properties.RequiresNoLightRecipeProperty;

public class LightChemicalReactorLogic<T extends MetaTileEntity & ILightRecipeLogicMachine> extends RecipeLogicEnergy {

    private boolean mustCheckIfInDark;
    private EnumDyeColor lightColor;
    private final T metaTileEntity;

    public LightChemicalReactorLogic(T metaTileEntity, RecipeMap<?> recipeMap,
                                     Supplier<IEnergyContainer> energyContainer) {
        super(metaTileEntity, recipeMap, energyContainer);
        this.metaTileEntity = metaTileEntity;
    }

    private void toggle(@Nullable EnumDyeColor enumDyeColor) {
        this.mustCheckIfInDark = !mustCheckIfInDark;
        this.lightColor = enumDyeColor;
    }

    @Override
    protected void decreaseProgress() {}

    @Override
    protected boolean canProgressRecipe() {
        if (mustCheckIfInDark &&
                this.metaTileEntity instanceof IRequiresNoLightRecipeLogicMachine machine &&
                !machine.hasNoLight(metaTileEntity.getWorld())) {
            return false;
        }
        if (lightColor != null &&
                this.metaTileEntity instanceof IRequiresLightRecipeLogicMachine machine &&
                !machine.hasLamp(metaTileEntity.getWorld(), lightColor)) {
            return false;
        }
        return super.canProgressRecipe();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe) {
        if (recipe.hasProperty(RequiresNoLightRecipeProperty.getInstance())) {
            this.lightColor = null;
            this.mustCheckIfInDark = true;
            return true;
        } else if (recipe.hasProperty(RequiresLightRecipeProperty.getInstance())) {
            this.lightColor = RequiresLightRecipeProperty.getInstance().getValueFromRecipe(recipe);
            this.mustCheckIfInDark = false;
            return true;
        }
        return false;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.lightColor = null;
        this.mustCheckIfInDark = false;
    }

    @Override
    @NotNull
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbtTagCompound = super.serializeNBT();
        if (this.lightColor != null) {
            RequiresLightRecipeProperty.getInstance().serialize(nbtTagCompound, this.lightColor);
        }
        if (this.mustCheckIfInDark) {
            RequiresNoLightRecipeProperty.getInstance().serialize(nbtTagCompound);
        }
        return nbtTagCompound;
    }

    @Override
    public void deserializeNBT(@NotNull NBTTagCompound compound) {
        super.deserializeNBT(compound);
        this.mustCheckIfInDark = RequiresNoLightRecipeProperty.getInstance().deserialize(compound);
        this.lightColor = RequiresLightRecipeProperty.getInstance().deserialize(compound);
    }
}
