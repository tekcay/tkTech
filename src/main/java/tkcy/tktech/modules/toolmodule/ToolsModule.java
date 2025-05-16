package tkcy.tktech.modules.toolmodule;

import java.util.*;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.common.items.ToolItems;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import tkcy.tktech.api.items.toolitem.TkTechToolClasses;
import tkcy.tktech.api.utils.StringsHelper;
import tkcy.tktech.common.item.TkTechToolItems;
import tkcy.tktech.modules.NBTLabel;

@UtilityClass
public class ToolsModule {

    public static final List<GtTool> GT_TOOLS = new ArrayList<>();

    public static void serializeTool(NBTTagCompound mainTagCompound, GtTool gtTool) {
        gtTool.serialize(mainTagCompound);
    }

    public static GtTool deserializeTool(NBTTagCompound mainTagCompound) {
        int ordinal = mainTagCompound.getInteger(NBTLabel.TOOL_ORDINAL.toString());
        return GtTool.values()[ordinal];
    }

    @Getter
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
        SOLDERING_IRON(TkTechToolClasses.SOLDERING_IRON, TkTechToolItems.SOLDERING_IRON);

        private final String toolClassName;
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

        public void serialize(NBTTagCompound mainTagCompound) {
            mainTagCompound.setInteger(NBTLabel.TOOL_ORDINAL.toString(), this.ordinal());
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
    public static GtTool getGtTool(@NotNull Set<String> toolClasses) {
        if (toolClasses.contains(GtTool.HARD_HAMMER.getToolClassName())) return GtTool.HARD_HAMMER;
        for (GtTool gtTool : GT_TOOLS) {
            if (toolClasses.contains(gtTool.toolClassName)) {
                return gtTool;
            }
        }
        return null;
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

    public static boolean isTool(ItemStack itemStack) {
        return GT_TOOLS.stream()
                .map(GtTool::getToolStack)
                .anyMatch(itemStack1 -> itemStack1.isItemEqual(itemStack));
    }
}
