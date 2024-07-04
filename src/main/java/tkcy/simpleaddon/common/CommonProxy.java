package tkcy.simpleaddon.common;

import java.util.Objects;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.common.items.ToolItems;

import tkcy.simpleaddon.TekCaySimpleAddon;
import tkcy.simpleaddon.api.unification.flags.FlagsAddition;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.api.unification.ore.OrePrefixRegistry;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;
import tkcy.simpleaddon.loaders.recipe.TKCYSARecipeLoader;
import tkcy.simpleaddon.loaders.recipe.parts.OreProcessingsHandler;
import tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule;

@Mod.EventBusSubscriber(modid = TekCaySimpleAddon.MODID)
public class CommonProxy {

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TekCaySimpleAddon.MODID)) {
            ConfigManager.sync(TekCaySimpleAddon.MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TKCYSALog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TKCYSALog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (IGTTool tool : TKCYSAToolItems.getAllTKCYSATools()) {
            registry.register(tool.get());
        }
        AlloyingModule.setAlloyFluidTemperature();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterials(MaterialEvent event) {
        TKCYSAMaterials.init();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void postRegisterMaterials(@NotNull PostMaterialEvent event) {
        OrePrefixRegistry.register();
    }

    // this is called almost last, to make sure all mods registered their ore dictionary
    // items and blocks for running first phase of material handlers
    // it will also clear generated materials
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void runEarlyMaterialHandlers(RegistryEvent.Register<IRecipe> event) {
        TKCYSALog.logger.info("Running early material handlers...");
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TKCYSALog.logger.info("Registering recipe low...");
        OreProcessingsHandler.init();
        // TKCYSAToolItems.registerOreDict();
        ToolItems.registerOreDict();

        // ePrefixAdditions.init();
    }

    @SubscribeEvent
    public static void registerMaterialsPost(PostMaterialEvent event) {
        FlagsAddition.init();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        TKCYSALog.logger.info("Registering and removing some GTCEu recipes...");

        // Main recipe registration
        // This is called AFTER GregTech registers recipes, so
        // anything here is safe to call removals in

        TKCYSARecipeLoader.latestInit();
    }

    public void preLoad() {}

    public void onLoad() {}
}
