package tkcy.simpleaddon.api.machines;

import java.util.List;

import tkcy.simpleaddon.modules.toolmodule.ToolsModule;

public interface IOnAnyToolClick {

    /**
     * Called when player clicks with any tool on specific side of this meta tile entity
     *
     * @return true if something happened, so the tool will get damaged and animation will be played
     */
    boolean onAnyToolClick(ToolsModule.GtTool gtTool, boolean isPlayerSneaking);

    void onAnyToolClickTooltip(List<String> tooltips);

    default boolean showAnyToolClickTooltip() {
        return false;
    }
}
