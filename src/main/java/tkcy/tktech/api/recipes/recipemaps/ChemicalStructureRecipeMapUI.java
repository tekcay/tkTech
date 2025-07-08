package tkcy.tktech.api.recipes.recipemaps;

import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUI;

import lombok.Getter;

@Getter
@ApiStatus.Internal
public class ChemicalStructureRecipeMapUI<R extends RecipeMap<?>> extends RecipeMapUI<R> {

    private final int backgroundHeight = 512;

    public ChemicalStructureRecipeMapUI(@NotNull R recipeMap) {
        super(recipeMap, false, false, false, false, false);
    }

    @Override
    @NotNull
    public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable exportItems,
                                                 FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 500, backgroundHeight)
                .widget(new ProgressWidget(200, 70, 19, 36, 18, GuiTextures.PROGRESS_BAR_ARROW,
                        ProgressWidget.MoveType.HORIZONTAL));
        this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
        this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
        return builder;
    }
}
