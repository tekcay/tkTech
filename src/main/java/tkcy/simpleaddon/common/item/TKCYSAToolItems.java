package tkcy.simpleaddon.common.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.api.items.toolitem.ToolBuilder;
import gregtech.api.unification.OreDictUnifier;
import gregtech.core.sound.GTSoundEvents;

import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolClasses;
import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolOreDict;

public class TKCYSAToolItems {

    private static final List<IGTTool> TKCYSA_TOOLS = new ArrayList<>();
    public static IGTTool SOLDERING_IRON;

    public static List<IGTTool> getAllTKCYSATools() {
        return TKCYSA_TOOLS;
    }

    public static void init() {
        SOLDERING_IRON = tkcysaRegister(ItemGTTool.Builder.of(GTValues.MODID, "soldering_iron")
                .toolStats(builder -> builder.crafting().damagePerAction(1))
                .sound(GTSoundEvents.ELECTROLYZER)
                .symbol('i')
                .toolClasses(TKCYSAToolClasses.SOLDERING_IRON)
                .oreDict(TKCYSAToolOreDict.solderingIron));
    }

    public static IGTTool tkcysaRegister(@NotNull ToolBuilder<?> builder) {
        IGTTool tool = builder.build();
        TKCYSA_TOOLS.add(tool);
        return tool;
    }

    public static IGTTool tkcysaRegister(@NotNull IGTTool tool) {
        TKCYSA_TOOLS.add(tool);
        return tool;
    }

    public static void registerModels() {
        TKCYSA_TOOLS
                .forEach(tool -> ModelLoader.setCustomModelResourceLocation(tool.get(), 0, tool.getModelLocation()));
    }

    public static void registerColors() {
        TKCYSA_TOOLS.forEach(
                tool -> Minecraft.getMinecraft().getItemColors().registerItemColorHandler(tool::getColor, tool.get()));
    }

    public static void registerOreDict() {
        TKCYSA_TOOLS.forEach(tool -> {
            final ItemStack stack = new ItemStack(tool.get(), 1, GTValues.W);
            if (tool.getOreDictName() != null) {
                OreDictUnifier.registerOre(stack, tool.getOreDictName());
            }
            tool.getSecondaryOreDicts().forEach(oreDict -> OreDictUnifier.registerOre(stack, oreDict));
        });
    }
}
