package tkcy.simpleaddon.integration.jei;

import java.util.Arrays;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.metatileentities.MetaTileEntities;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;

@JEIPlugin
public class Removals implements IModPlugin {

    @Override
    public void register(@NotNull IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IIngredientBlacklist ingredientBlacklist = jeiHelpers.getIngredientBlacklist();

        Arrays.stream(MetaTileEntities.ELECTROLYZER)
                .filter(Objects::nonNull)
                .map(MetaTileEntity::getStackForm)
                .forEach(ingredientBlacklist::addIngredientToBlacklist);
    }
}
