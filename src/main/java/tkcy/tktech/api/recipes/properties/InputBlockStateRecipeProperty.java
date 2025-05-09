package tkcy.tktech.api.recipes.properties;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.modules.RecipePropertiesKey;

public class InputBlockStateRecipeProperty extends BlockStateStackRecipeProperty
                                           implements IRecipePropertyHelper<ItemStack> {

    public static final String KEY = RecipePropertiesKey.INPUT_BLOCK_STATE_KEY;
    private static InputBlockStateRecipeProperty INSTANCE;

    private InputBlockStateRecipeProperty() {
        super(KEY, ItemStack.class);
    }

    @NotNull
    public static InputBlockStateRecipeProperty getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InputBlockStateRecipeProperty();
        }
        return INSTANCE;
    }
}
