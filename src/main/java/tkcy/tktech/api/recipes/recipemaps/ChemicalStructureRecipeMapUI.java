package tkcy.tktech.api.recipes.recipemaps;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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

    // @Override
    // @NotNull
    // public ModularUI.Builder createJeiUITemplate(IItemHandlerModifiable importItems, IItemHandlerModifiable
    // exportItems,
    // FluidTankList importFluids, FluidTankList exportFluids, int yOffset) {
    // ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, backgroundHeight);
    // builder.widget(new RecipeProgressWidget(200, 78, 23 + yOffset, 20, 20, progressBarTexture(),
    // progressBarMoveType(), recipeMap()));
    // this.addInventorySlotGroup(builder, importItems, importFluids, false, yOffset);
    // this.addInventorySlotGroup(builder, exportItems, exportFluids, true, yOffset);
    // return builder;
    // }
}
