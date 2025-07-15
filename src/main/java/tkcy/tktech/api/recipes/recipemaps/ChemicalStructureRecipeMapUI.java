package tkcy.tktech.api.recipes.recipemaps;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;

import lombok.Getter;
import mezz.jei.config.Config;
import tkcy.tktech.api.utils.GuiJeiUtils;

@Getter
@ApiStatus.Internal
public class ChemicalStructureRecipeMapUI<R extends RecipeMap<?>> extends RecipeMapUI<R>
                                         implements IJeiConfigBoundRecipeUI {

    private final int backgroundHeight = GuiJeiUtils.STANDARD_JEI_UI_HEIGHT;
    private final int backgroundWidth = GuiJeiUtils.STANDARD_JEI_UI_WIDTH;

    public ChemicalStructureRecipeMapUI(@NotNull R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }

    @Override
    @NotNull
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, getBackgroundWidth(),
                Config.getMaxRecipeGuiHeight());
        builder.widget(new RecipeProgressWidget(200, 78, 23 + yOffset, 20, 20, progressBarTexture(),
                progressBarMoveType(), recipeMap()));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }
}
