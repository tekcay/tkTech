package tkcy.simpleaddon.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.minecraft.item.ItemStack;

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

        List<ItemStack> toHide = new ArrayList<>();

        Arrays.stream(MetaTileEntities.ELECTROLYZER)
                .filter(Objects::nonNull)
                .map(MetaTileEntity::getStackForm)
                .forEach(toHide::add);

        toHide.add(MetaTileEntities.CRACKER.getStackForm());

        toHide.forEach(ingredientBlacklist::addIngredientToBlacklist);
    }
}
