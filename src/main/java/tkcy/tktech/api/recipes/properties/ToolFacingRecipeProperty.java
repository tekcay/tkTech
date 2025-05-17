package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.properties.RecipeProperty;
import gregtech.api.util.EnumValidationResult;

import tkcy.tktech.api.utils.TkTechLog;
import tkcy.tktech.modules.RecipePropertiesKey;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@WorkingTool
public class ToolFacingRecipeProperty extends RecipeProperty<EnumFacing>
                                      implements IRecipePropertyHelper<EnumFacing> {

    public static final String KEY = RecipePropertiesKey.TOOL_CLICK_FACING_KEY;
    private static ToolFacingRecipeProperty INSTANCE;

    private ToolFacingRecipeProperty() {
        super(KEY, EnumFacing.class);
    }

    @NotNull
    public static ToolFacingRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolFacingRecipeProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagString(castValue(value).getName());
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        String enumFacingName = ((NBTTagString) nbt).getString();
        EnumFacing enumFacing = EnumFacing.byName(enumFacingName);
        return enumFacingName == null ? "" : enumFacing;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        EnumFacing facing = castValue(value);
        minecraft.fontRenderer.drawString(
                I18n.format("tktech.recipe.tool.click_facing", facing.getName().toUpperCase()), x, y, color);
    }

    @Override
    public RecipeBuilder<?> testAndApplyPropertyValue(EnumFacing valueToTest, EnumValidationResult recipeStatus,
                                                      RecipeBuilder<?> recipeBuilder) {
        if (!this.testSuppliedValue().test(valueToTest)) {
            TkTechLog.logger.error(this::getErrorMessage, new IllegalArgumentException());
            recipeStatus = EnumValidationResult.INVALID;
        }
        recipeBuilder.applyProperty(this, valueToTest);
        return recipeBuilder;
    }

    @Override
    public Predicate<EnumFacing> testSuppliedValue() {
        return enumFacing -> true;
    }

    @Override
    public EnumFacing getDefaultValue() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return "Error with EnumFacing property";
    }

    @Override
    public RecipeProperty<EnumFacing> getProperty() {
        return this;
    }
}
