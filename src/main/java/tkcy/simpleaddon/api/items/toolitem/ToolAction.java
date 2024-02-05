package tkcy.simpleaddon.api.items.toolitem;

import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.entity.player.EntityPlayer;

import org.jetbrains.annotations.Nullable;

import gregtech.api.recipes.Recipe;

public interface ToolAction {

    Map<String, Consumer<EntityPlayer>> setToolClassToOnUsage();

    @Nullable
    Recipe getCurrentRecipe();
}
