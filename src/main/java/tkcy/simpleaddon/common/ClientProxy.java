package tkcy.simpleaddon.common;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tkcy.simpleaddon.TekCaySimpleAddon;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = TekCaySimpleAddon.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        TKCYSATextures.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TKCYSAMetaBlocks.registerItemModels();
        TKCYSAToolItems.registerModels();
    }

    @Override
    public void onLoad() {
        registerColors();
    }

    public static void registerColors() {
        TKCYSAToolItems.registerColors();
        TKCYSAMetaBlocks.registerColors();
    }
}
