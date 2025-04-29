package tkcy.simpleaddon.api.machines;

import java.util.List;

import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public interface IOnAnyToolClick {

    void onAnyToolClickTooltip(List<String> tooltips);

    /**
     * Called when player clicks with any tool on specific side of this meta tile entity
     */
    default void onAnyToolClick(ToolsModule.GtTool gtTool, boolean isPlayerSneaking) {}

    default boolean showAnyToolClickTooltip() {
        return false;
    }
}
