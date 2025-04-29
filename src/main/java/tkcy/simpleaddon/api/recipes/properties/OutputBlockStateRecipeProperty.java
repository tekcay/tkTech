package tkcy.simpleaddon.api.recipes.properties;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import tkcy.simpleaddon.modules.RecipePropertiesKey;

public class OutputBlockStateRecipeProperty extends BlockStateStackRecipeProperty
                                            implements IRecipePropertyHelper<ItemStack> {

    public static final String KEY = RecipePropertiesKey.OUTPUT_BLOCK_STATE_KEY;
    private static OutputBlockStateRecipeProperty INSTANCE;

    private OutputBlockStateRecipeProperty() {
        super(KEY, ItemStack.class);
    }

    @NotNull
    public static OutputBlockStateRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OutputBlockStateRecipeProperty();
        }
        return INSTANCE;
    }
}
