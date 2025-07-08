package tkcy.tktech.api.recipes.recipemaps;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.ui.RecipeMapUIFunction;
import gregtech.core.sound.GTSoundEvents;

@ApiStatus.Internal
public class ChemicalStructureDisplayRecipeMap<R extends RecipeBuilder<R>> extends RecipeMap<R>
                                              implements
                                              ICustomCategoryRecipeMap<ChemicalStructureDisplayRecipeMap<R>> {

    public ChemicalStructureDisplayRecipeMap(@NotNull String unlocalizedName,
                                             @NotNull R defaultRecipeBuilder,
                                             @NotNull RecipeMapUIFunction recipeMapUI, int maxInputs, int maxOutputs,
                                             int maxFluidInputs, int maxFluidOutputs) {
        super(unlocalizedName, defaultRecipeBuilder, recipeMapUI, maxInputs, maxOutputs, maxFluidInputs,
                maxFluidOutputs);
        setSound(GTSoundEvents.ASSEMBLER);
    }
}
