package tkcy.simpleaddon.api.recipes;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.gui.widgets.RecipeProgressWidget;
import gregtech.api.recipes.RecipeMap;

import tkcy.simpleaddon.api.recipes.builders.ToolRecipeBuilder;

public class ToolRecipeMap<T extends ToolRecipeBuilder> extends RecipeMap<ToolRecipeBuilder> {

    public ToolRecipeMap(@NotNull String unlocalizedName, int maxInputs, int maxOutputs, int maxFluidInputs,
                         int maxFluidOutputs, @NotNull T defaultRecipeBuilder, boolean isHidden) {
        super(unlocalizedName, maxInputs, maxOutputs, maxFluidInputs, maxFluidOutputs, defaultRecipeBuilder, isHidden);
        setProgressBar(GuiTextures.PROGRESS_BAR_BENDING, ProgressWidget.MoveType.HORIZONTAL);
    }

    @Override
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yoffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 80);
        // ModularUI.Builder builder = ModularUI.defaultBuilder(yOffset);
        builder.widget(new RecipeProgressWidget(200, 78, 7, 20, 20, progressBarTexture, moveType, this));
        addInventorySlotGroup(builder, importItems, importFluids, false, -16);
        addInventorySlotGroup(builder, exportItems, exportFluids, true, -16);

        if (this.specialTexture != null && this.specialTexturePosition != null) addSpecialTexture(builder);
        return builder;
    }
}
