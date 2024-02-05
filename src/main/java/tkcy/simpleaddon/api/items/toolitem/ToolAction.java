package tkcy.simpleaddon.api.items.toolitem;

import java.util.List;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.Recipe;

public interface ToolAction {

    @Nullable
    Recipe getCurrentRecipe(List<ItemStack> inputStacks);
}
