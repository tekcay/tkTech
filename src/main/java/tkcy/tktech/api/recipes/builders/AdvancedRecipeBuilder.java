package tkcy.tktech.api.recipes.builders;

import java.util.*;

import gregtech.api.capability.impl.AbstractRecipeLogic;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.properties.impl.PrimitiveProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.ValidationResult;
import gregtech.common.blocks.BlockWireCoil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import tkcy.tktech.api.recipes.properties.*;

@NoArgsConstructor
public class AdvancedRecipeBuilder extends RecipeBuilder<AdvancedRecipeBuilder> implements
                                   IChemicalStructureRecipeBuilder<AdvancedRecipeBuilder>,
                                   IToolRecipeBuilder<AdvancedRecipeBuilder>,
                                   IBlockInWorldRecipeBuilder<AdvancedRecipeBuilder>,
                                   IFailedStackRecipeBuilder<AdvancedRecipeBuilder> {

    protected boolean hideDuration = false;
    protected boolean useAndDisplayEnergy = true;
    @Getter
    protected final List<Material> inputMaterialsChemStructure = new ArrayList<>();
    @Getter
    protected final List<Material> outputMaterialsChemStructure = new ArrayList<>();

    @Override
    public void invalidateRecipe() {
        recipeStatus = EnumValidationResult.INVALID;
    }

    @SuppressWarnings("unused")
    public AdvancedRecipeBuilder(Recipe recipe, RecipeMap<AdvancedRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public AdvancedRecipeBuilder(AdvancedRecipeBuilder builder) {
        super(builder);
    }

    @Override
    public AdvancedRecipeBuilder copy() {
        return new AdvancedRecipeBuilder(this);
    }

    public AdvancedRecipeBuilder hideDuration() {
        this.hideDuration = true;
        return this;
    }

    public AdvancedRecipeBuilder noEUt() {
        this.useAndDisplayEnergy = false;
        return this;
    }

    public AdvancedRecipeBuilder hideEnergyAndDuration() {
        this.hideDuration();
        this.noEUt();
        return this;
    }

    public AdvancedRecipeBuilder coil(BlockWireCoil.CoilType coil) {
        CoilTypeRecipeProperty property = CoilTypeRecipeProperty.getInstance();
        return testAndApplyPropertyValue(CoilTypeRecipeProperty.getInstance(), coil);
    }

    public AdvancedRecipeBuilder requiresIgnition() {
        return testAndApplyPropertyValue(IsIgnitedRecipeProperty.getInstance(), true);
    }

    /**
     * List the recipe {@link #duration}.
     * 
     * @param duration
     * @param recipeDurationRate a value that can modify the recipe duration at
     *                           {@link AbstractRecipeLogic#setupRecipe(Recipe)}
     */
    public AdvancedRecipeBuilder duration(int duration, float recipeDurationRate) {
        duration(duration);
        return testAndApplyPropertyValue(DurationModifierRecipeProperty.getInstance(), recipeDurationRate);
    }

    @Override
    public ValidationResult<Recipe> build() {
        validateChemicalStructuresRecipeProperty(recipeStatus);

        if (hideDuration) {
            duration(10);
            applyProperty(HideDurationRecipeProperty.getInstance(), true);
        }

        if (!useAndDisplayEnergy) {
            applyProperty(PrimitiveProperty.getInstance(), true);
            EUt(1); // secretly force to 1 to allow recipe matching to work properly
        }

        return super.build();
    }

    @Override
    public AdvancedRecipeBuilder getRecipeBuilder() {
        return this;
    }
}
