package tkcy.tktech.api.recipes.properties;

import java.util.Arrays;
import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.unification.material.Material;
import gregtech.api.util.EnumValidationResult;
import gregtech.common.blocks.BlockWireCoil;

import tkcy.tktech.api.utils.TkTechLog;
import tkcy.tktech.modules.RecipePropertiesKey;

public class CoilTypeRecipeProperty extends RecipeProperty<BlockWireCoil.CoilType>
                                    implements IRecipePropertyHelper<BlockWireCoil.CoilType> {

    public static final String KEY = RecipePropertiesKey.COIL_KEY;
    private static CoilTypeRecipeProperty INSTANCE;

    private CoilTypeRecipeProperty() {
        super(KEY, BlockWireCoil.CoilType.class);
    }

    @NotNull
    public static CoilTypeRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoilTypeRecipeProperty();
        }
        return INSTANCE;
    }

    private static String getInfo(Object value) {
        BlockWireCoil.CoilType coilType = (BlockWireCoil.CoilType) value;
        Material coilMaterial = coilType.getMaterial();
        return coilMaterial == null ? "invalid" : coilMaterial.getLocalizedName();
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagString(castValue(value).getName());
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return BlockWireCoil.CoilType.valueOf(((NBTTagString) nbt).getString());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        minecraft.fontRenderer.drawString(I18n.format("tktech.recipe.coil",
                getInfo(value)), x, y, color);
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(BlockWireCoil.CoilType valueToTest,
                                                      EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (!this.testSuppliedValue().test(valueToTest)) {
            TkTechLog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, valueToTest);
        return recipeBuilder;
    }

    @Override
    public Predicate<BlockWireCoil.CoilType> testSuppliedValue() {
        return coilType -> Arrays.asList(BlockWireCoil.CoilType.values()).contains(coilType);
    }

    @Override
    public BlockWireCoil.CoilType getDefaultValue() {
        return BlockWireCoil.CoilType.CUPRONICKEL;
    }

    @Override
    public String getErrorMessage() {
        return "Not valid CoilType!";
    }

    @Override
    public RecipeProperty<BlockWireCoil.CoilType> getProperty() {
        return this;
    }
}
