package tkcy.simpleaddon.modules;

import java.util.*;

import org.jetbrains.annotations.Nullable;

import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;
import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolClasses;

public class ToolsModule {

    public static final List<GtTool> GT_TOOLS = new ArrayList<>();

    public enum GtTool {

        SWORD(ToolClasses.SWORD),
        PICKAXE(ToolClasses.PICKAXE),
        SHOVEL(ToolClasses.SHOVEL),
        AXE(ToolClasses.AXE),
        HOE(ToolClasses.HOE),
        SAW(ToolClasses.SAW),
        HARD_HAMMER(ToolClasses.HARD_HAMMER),
        SOFT_MALLET(ToolClasses.SOFT_MALLET),
        WRENCH(ToolClasses.WRENCH),
        FILE(ToolClasses.FILE),
        CROWBAR(ToolClasses.CROWBAR),
        SCREWDRIVER(ToolClasses.SCREWDRIVER),
        MORTAR(ToolClasses.MORTAR),
        WIRE_CUTTER(ToolClasses.WIRE_CUTTER),
        SCYTHE(ToolClasses.SCYTHE),
        SHEARS(ToolClasses.SHEARS),
        KNIFE(ToolClasses.KNIFE),
        BUTCHERY_KNIFE(ToolClasses.BUTCHERY_KNIFE),
        GRAFTER(ToolClasses.GRAFTER),
        PLUNGER(ToolClasses.PLUNGER),
        SOLDERING_IRON(TKCYSAToolClasses.SOLDERING_IRON);

        @Getter
        private String toolClassName;

        GtTool(String toolClassName) {
            this.toolClassName = toolClassName;
            GT_TOOLS.add(this);
        }
    }

    @Nullable
    public static String getToolClass(Set<String> toolClasses) {
        /*
         * GT_TOOLS.stream()
         * .map(GtTool::getToolClassName)
         * .
         * 
         * 
         * 
         * return toolClasses.stream()
         * .filter(Objects::nonNull)
         * .filter(TOOLS::contains)
         * .findFirst()
         * .orElse(null);
         * 
         */
        return GtTool.HARD_HAMMER.toolClassName;
    }
}
