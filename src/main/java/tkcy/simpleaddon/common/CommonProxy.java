package tkcy.simpleaddon.common;

import static gregtech.common.blocks.MetaBlocks.BOILER_CASING;
import static tkcy.simpleaddon.TekCaySimpleAddon.MODID;

import java.util.Objects;
import java.util.function.Function;

import gregtech.api.block.VariantItemBlock;
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

import gregtech.api.GregTechAPI;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.metatileentity.registry.MTEManager;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.MaterialRegistryEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.common.blocks.MaterialItemBlock;

import tkcy.simpleaddon.api.unification.flags.FlagsAddition;
import tkcy.simpleaddon.api.unification.materials.TKCYSAMaterials;
import tkcy.simpleaddon.api.unification.ore.OrePrefixRegistry;
import tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.common.block.BlockMaterialCasing;
import tkcy.simpleaddon.common.block.BlockMaterialCoil;
import tkcy.simpleaddon.common.block.BlockMaterialWall;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;
import tkcy.simpleaddon.loaders.recipe.TKCYSARecipeLoader;
import tkcy.simpleaddon.loaders.recipe.parts.OreProcessingsHandler;
import tkcy.simpleaddon.modules.alloyingmodule.AlloyingModule;

@Mod.EventBusSubscriber(modid = MODID)
public class CommonProxy {

    @SubscribeEvent
    public static void registerMTERegistry(MTEManager.MTERegistryEvent event) {
        GregTechAPI.mteManager.createRegistry(MODID);
    }

    @SubscribeEvent
    public static void createMaterialRegistry(MaterialRegistryEvent event) {
        GregTechAPI.materialManager.createRegistry(MODID);
    }

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TKCYSALog.logger.info("Registering blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        for (BlockMaterialCasing blockMaterialCasing : TKCYSAMetaBlocks.CASINGS_BLOCKS)
            registry.register(blockMaterialCasing);
        for (BlockMaterialWall blockMaterialWall : TKCYSAMetaBlocks.WALLS_BLOCKS)
            registry.register(blockMaterialWall);
        for (BlockMaterialCoil blockMaterialCoil : TKCYSAMetaBlocks.COIL_BLOCKS)
            registry.register(blockMaterialCoil);

        registry.register(TKCYSAMetaBlocks.STRIPPED_WOOD);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TKCYSALog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (IGTTool tool : TKCYSAToolItems.getAllTKCYSATools()) {
            registry.register(tool.get());
        }
        AlloyingModule.setAlloyFluidTemperature();

        for (BlockMaterialCasing block : TKCYSAMetaBlocks.CASINGS_BLOCKS) {
            registry.register(createItemBlock(block, b -> new MaterialItemBlock(b, TKCYSAOrePrefix.casing)));
        }

        for (BlockMaterialWall block : TKCYSAMetaBlocks.WALLS_BLOCKS) {
            registry.register(createItemBlock(block, b -> new MaterialItemBlock(b, TKCYSAOrePrefix.wall)));
        }

        for (BlockMaterialCoil block : TKCYSAMetaBlocks.COIL_BLOCKS) {
            registry.register(createItemBlock(block, b -> new MaterialItemBlock(b, TKCYSAOrePrefix.coil)));
        }

        registry.register(createItemBlock(TKCYSAMetaBlocks.STRIPPED_WOOD, VariantItemBlock::new));
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
        TKCYSAMetaBlocks.registerOreDict();
        OreProcessingsHandler.init();
        TKCYSAToolItems.registerOreDict();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerMaterialsPost(PostMaterialEvent event) {
        FlagsAddition.init();
        OrePrefixRegistry.register();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        TKCYSALog.logger.info("Registering and removing some GTCEu recipes...");

        // Main recipe registration
        // This is called AFTER GregTech registers recipes, so
        // anything here is safe to call removals in

        TKCYSARecipeLoader.latestInit();
    }

    public void onLoad() {}

    public void preLoad() {}
}
