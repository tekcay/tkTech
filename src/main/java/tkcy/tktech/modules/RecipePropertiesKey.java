package tkcy.tktech.modules;

import lombok.experimental.UtilityClass;
import tkcy.tktech.modules.toolmodule.WorkingTool;

@UtilityClass
public class RecipePropertiesKey {

    @WorkingTool
    public static final String TOOL_USAGE_KEY = "toolUsageKey";
    @WorkingTool
    public static final String TOOL_KEY = "tool";
    @WorkingTool
    public static final String TOOL_CLICK_FACING_KEY = "toolClickFacingKey";
    public static final String COIL_KEY = "coil";
    public static final String HIDE_DURATION_KEY = "hideDuration";
    public static final String INPUT_BLOCK_STATE_KEY = "inputBlockStateKey";
    public static final String OUTPUT_BLOCK_STATE_KEY = "outputBlockStateKey";
}
