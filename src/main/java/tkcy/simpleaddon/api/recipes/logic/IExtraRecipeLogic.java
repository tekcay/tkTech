package tkcy.simpleaddon.api.recipes.logic;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;

import tkcy.simpleaddon.api.recipes.properties.IRecipePropertyHelper;

public interface IExtraRecipeLogic {

    @NotNull
    AbstractRecipeLogic getLogic();

    void updateRecipeParameters(@NotNull Map<IRecipePropertyHelper<?>, Object> recipeParameters);

    @NotNull
    default MetaTileEntity getMetaTileEntity() {
        return getLogic().getMetaTileEntity();
    }
}
