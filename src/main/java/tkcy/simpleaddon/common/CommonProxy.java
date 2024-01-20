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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import tkcy.simpleaddon.TekCaySimpleAddon;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.loaders.recipe.TKCYSARecipeLoader;
import tkcy.simpleaddon.modules.AlloyingModule;

@Mod.EventBusSubscriber(modid = TekCaySimpleAddon.MODID)
public class CommonProxy {

    public void preLoad() {}

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

        AlloyingModule.setAlloyFluidTemperature();
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
        itemBlock.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
        return itemBlock;
    }

    @SubscribeEvent()
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TKCYSALog.logger.info("Registering recipes...");

        // Main recipe registration
        // This is called AFTER GregTech registers recipes, so
        // anything here is safe to call removals in
        TKCYSARecipeLoader.init();
    }
}
