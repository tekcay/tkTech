package tkcy.tktech.api.recipes.builders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import gregtech.common.blocks.BlockWireCoil;

import lombok.NoArgsConstructor;
import tkcy.tktech.api.recipes.properties.CoilTypeRecipeProperty;

@NoArgsConstructor
public class CoilTypeRecipeBuilder extends RecipeBuilder<CoilTypeRecipeBuilder> {

    @SuppressWarnings("unused")
    public CoilTypeRecipeBuilder(Recipe recipe, RecipeMap<CoilTypeRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CoilTypeRecipeBuilder(RecipeBuilder<CoilTypeRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    @Override
    public CoilTypeRecipeBuilder copy() {
        return new CoilTypeRecipeBuilder(this);
    }

    public CoilTypeRecipeBuilder coil(BlockWireCoil.CoilType coil) {
        CoilTypeRecipeProperty property = CoilTypeRecipeProperty.getInstance();
        return (CoilTypeRecipeBuilder) property.testAndApplyPropertyValue(coil, this.recipeStatus, this);
    }

    public static BlockWireCoil.CoilType getDefaultValue() {
        return BlockWireCoil.CoilType.CUPRONICKEL;
    }

    private static String getDisplayedValue(@NotNull BlockWireCoil.CoilType coil) {
        Material coilMaterial = coil.getMaterial();
        return coilMaterial == null ? "invalid" : "right : " + coilMaterial.getLocalizedName();
    }

    @NotNull
    public BlockWireCoil.CoilType getCoilType() {
        return this.recipePropertyStorage == null ? getDefaultValue() :
                this.recipePropertyStorage.get(CoilTypeRecipeProperty.getInstance(),
                        getDefaultValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(CoilTypeRecipeProperty.getInstance().getKey(), getDisplayedValue(this.getCoilType()))
                .toString();
    }
}
