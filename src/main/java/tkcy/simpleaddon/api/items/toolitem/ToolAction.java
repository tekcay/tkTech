package tkcy.simpleaddon.api.items.toolitem;

import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface ToolAction {

    Map<String, Consumer<EntityPlayer>> setToolClassToOnUsage();

    @Nullable
    Recipe getCurrentRecipe();
}
