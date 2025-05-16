package tkcy.tktech.api.machines;

import java.util.List;

import net.minecraft.util.EnumFacing;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.modules.toolmodule.ToolsModule;

public interface IOnAnyToolClick {

    void onAnyToolClickTooltip(@NotNull List<String> tooltips);

    /**
     * Called when player clicks with any tool on specific side of this meta tile entity.
     * </br>
     * Called <i>BEFORE</i> the other {@code onToolClick} methods are called.
     */
    default void onAnyToolClick(ToolsModule.GtTool gtTool, boolean isPlayerSneaking, EnumFacing faceClick) {}

    default boolean showAnyToolClickTooltip() {
        return false;
    }
}
