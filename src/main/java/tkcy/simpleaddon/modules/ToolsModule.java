package tkcy.simpleaddon.modules;

import java.util.*;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.common.items.ToolItems;

import lombok.Getter;
import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolClasses;
import tkcy.simpleaddon.api.utils.StringsHelper;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;

public class ToolsModule {

    public static final List<GtTool> GT_TOOLS = new ArrayList<>();

    public enum GtTool {

        SWORD(ToolClasses.SWORD, ToolItems.SWORD),
        PICKAXE(ToolClasses.PICKAXE, ToolItems.PICKAXE),
        SHOVEL(ToolClasses.SHOVEL, ToolItems.SHOVEL),
        AXE(ToolClasses.AXE, ToolItems.AXE),
        HOE(ToolClasses.HOE, ToolItems.HOE),
        SAW(ToolClasses.SAW, ToolItems.SAW),
        HARD_HAMMER(ToolClasses.HARD_HAMMER, ToolItems.HARD_HAMMER),
        SOFT_MALLET(ToolClasses.SOFT_MALLET, ToolItems.SOFT_MALLET),
        WRENCH(ToolClasses.WRENCH, ToolItems.WRENCH),
        FILE(ToolClasses.FILE, ToolItems.FILE),
        CROWBAR(ToolClasses.CROWBAR, ToolItems.CROWBAR),
        SCREWDRIVER(ToolClasses.SCREWDRIVER, ToolItems.SCREWDRIVER),
        MORTAR(ToolClasses.MORTAR, ToolItems.MORTAR),
        WIRE_CUTTER(ToolClasses.WIRE_CUTTER, ToolItems.WIRE_CUTTER),
        SCYTHE(ToolClasses.SCYTHE, ToolItems.SCYTHE),
        KNIFE(ToolClasses.KNIFE, ToolItems.KNIFE),
        BUTCHERY_KNIFE(ToolClasses.BUTCHERY_KNIFE, ToolItems.BUTCHERY_KNIFE),
        PLUNGER(ToolClasses.PLUNGER, ToolItems.PLUNGER),
        SOLDERING_IRON(TKCYSAToolClasses.SOLDERING_IRON, TKCYSAToolItems.SOLDERING_IRON);

        @Getter
        private final String toolClassName;

        @Getter
        private final IGTTool tool;

        GtTool(String toolClassName, IGTTool igtTool) {
            this.toolClassName = toolClassName;
            this.tool = igtTool;
            GT_TOOLS.add(this);
        }

        @Override
        public String toString() {
            return StringsHelper.convertScreamingSnakeToTileCase(this.name());
        }

        public ItemStack getToolStack() {
            return new ItemStack(this.tool.get());
        }
    }

    @Nullable
    public static String getToolClass(@NotNull Set<String> toolClasses) {
        return GT_TOOLS.stream()
                .map(GtTool::getToolClassName)
                .filter(toolClasses::contains)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public static GtTool getGtTool(String toolClassName) {
        for (GtTool tool : GT_TOOLS) {
            if (tool.toolClassName.equals(toolClassName)) {
                return tool;
            }
        }
        return null;
    }
}
