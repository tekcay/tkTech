package tkcy.tktech.api.recipes.properties;

import java.util.function.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

import org.jetbrains.annotations.NotNull;

import gregtech.api.recipes.properties.RecipeProperty;

import tkcy.tktech.modules.RecipePropertiesKey;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@WorkingTool
public class ToolUsesProperty extends RecipeProperty<Integer> implements IRecipePropertyHelper<Integer> {

    public static final String KEY = RecipePropertiesKey.TOOL_USAGE_KEY;
    private static ToolUsesProperty INSTANCE;

    private ToolUsesProperty() {
        super(KEY, Integer.class);
    }

    @NotNull
    public static ToolUsesProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ToolUsesProperty();
        }
        return INSTANCE;
    }

    @Override
    public @NotNull NBTBase serialize(@NotNull Object value) {
        return new NBTTagInt(castValue(value));
    }

    @Override
    public @NotNull Object deserialize(@NotNull NBTBase nbt) {
        return ((NBTTagInt) nbt).getInt();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int x, int y, int color, Object value) {
        Integer uses = castValue(value);
        minecraft.fontRenderer.drawString(I18n.format("tktech.recipe.tool.uses", uses), x, y, color);
    }

    @Override
    public Predicate<Integer> testSuppliedValue() {
        return value -> value >= this.getDefaultValue();
    }

    @Override
    public Integer getDefaultValue() {
        return 1;
    }

    @Override
    public String getErrorMessage() {
        return "Uses must be 1 or more!";
    }

    @Override
    public RecipeProperty<Integer> getProperty() {
        return this;
    }
}
