package tkcy.simpleaddon.api.recipes.builders;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Material;
import gregtech.api.util.EnumValidationResult;
import gregtech.common.blocks.BlockWireCoil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import tkcy.simpleaddon.api.recipes.properties.CoilTypeProperty;
import tkcy.simpleaddon.api.utils.TKCYSALog;

import java.util.Arrays;

public class CoilTypeRecipeBuilder extends RecipeBuilder<CoilTypeRecipeBuilder> {

    public CoilTypeRecipeBuilder() {}

    @SuppressWarnings("unused")
    public CoilTypeRecipeBuilder(Recipe recipe, RecipeMap<CoilTypeRecipeBuilder> recipeMap) {
        super(recipe, recipeMap);
    }

    public CoilTypeRecipeBuilder(RecipeBuilder<CoilTypeRecipeBuilder> recipeBuilder) {
        super(recipeBuilder);
    }

    public CoilTypeRecipeBuilder copy() {
        return new CoilTypeRecipeBuilder(this);
    }

    @Override
    public boolean applyProperty(@NotNull String key, Object value) {
        if (key.equals(CoilTypeProperty.KEY)) {
            this.coil(((BlockWireCoil.CoilType) value));
            return true;
        }
        return super.applyProperty(key, value);
    }

    public CoilTypeRecipeBuilder coil(BlockWireCoil.CoilType coil) {
        if (!Arrays.asList(BlockWireCoil.CoilType.values()).contains(coil)) {
            TKCYSALog.logger.error("Coil type must be declared in the BlockWireCoil.CoilType enum!",
                    new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        this.applyProperty(CoilTypeProperty.getInstance(), coil);
        return this;
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
                this.recipePropertyStorage.getRecipePropertyValue(CoilTypeProperty.getInstance(),
                        getDefaultValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(CoilTypeProperty.getInstance().getKey(), getDisplayedValue(this.getCoilType()))
                .toString();
    }
}
