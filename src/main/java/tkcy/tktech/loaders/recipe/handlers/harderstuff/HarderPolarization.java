package tkcy.tktech.loaders.recipe.handlers.harderstuff;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.ingredients.GTRecipeItemInput;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HarderPolarization {

    public static void init() {
        GTRecipeItemInput gtRecipeItemInput = new GTRecipeItemInput(
                OreDictUnifier.get(OrePrefix.dust, Materials.Magnetite));

        RecipeMaps.POLARIZER_RECIPES.getRecipeList()
                .stream()
                .map(Recipe::getInputs)
                .forEach(gtRecipeInputs -> gtRecipeInputs.add(gtRecipeItemInput));
    }
}
