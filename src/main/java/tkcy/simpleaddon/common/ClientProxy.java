package tkcy.simpleaddon.common;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
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
    }
}
