package tkcy.tktech.api.recipes.builders;

import java.util.Arrays;
import java.util.List;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.EnumValidationResult;

import tkcy.tktech.api.recipes.properties.ChemicalStructuresRecipeProperty;
import tkcy.tktech.api.utils.BooleanHelper;

public interface IChemicalStructureRecipeBuilder<T extends RecipeBuilder<T>> extends IAdvancedRecipeBuilder<T> {

    List<Material> getInputMaterialsChemStructure();

    List<Material> getOutputMaterialsChemStructure();

    default void validateChemicalStructuresRecipeProperty(EnumValidationResult recipeStatus) {
        if (BooleanHelper.doesAnyNotMatch(List::isEmpty, getInputMaterialsChemStructure(),
                getOutputMaterialsChemStructure())) {

            ChemicalStructuresRecipeProperty recipeProperty = ChemicalStructuresRecipeProperty.getInstance();
            recipeProperty.testAndApplyPropertyValue(
                    new ChemicalStructuresRecipeProperty.Container(
                            getInputMaterialsChemStructure(),
                            getOutputMaterialsChemStructure()),
                    recipeStatus,
                    getRecipeBuilder());
        }
    }

    default T chemicalStructures(boolean isInput, Material... materials) {
        if (isInput) {
            getInputMaterialsChemStructure().addAll(Arrays.asList(materials));
        } else {
            getOutputMaterialsChemStructure().addAll(Arrays.asList(materials));
        }
        return getRecipeBuilder();
    }

    default T inputChemical(OrePrefix orePrefix, Material material, int count) {
        chemicalStructures(true, material);
        return getRecipeBuilder().input(orePrefix, material, count);
    }

    default T inputChemical(OrePrefix orePrefix, Material material) {
        return inputChemical(orePrefix, material, 1);
    }

    default T outputChemical(OrePrefix orePrefix, Material material, int count) {
        chemicalStructures(false, material);
        return getRecipeBuilder().output(orePrefix, material, count);
    }

    default T outputChemical(OrePrefix orePrefix, Material material) {
        return outputChemical(orePrefix, material, 1);
    }

    default T inputFluidChemical(Material material, int amount) {
        chemicalStructures(true, material);
        return getRecipeBuilder().fluidInputs(material.getFluid(amount));
    }

    default T inputFluidChemical(Material material) {
        return inputFluidChemical(material, 1000);
    }

    default T outputFluidChemical(Material material, int amount) {
        chemicalStructures(false, material);
        return getRecipeBuilder().fluidOutputs(material.getFluid(amount));
    }

    default T outputFluidChemical(Material material) {
        return outputFluidChemical(material, 1000);
    }
}
