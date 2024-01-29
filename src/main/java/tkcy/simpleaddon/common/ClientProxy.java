package tkcy.simpleaddon.common;

import static tkcy.simpleaddon.common.block.TKCYSAMetaBlocks.registerColors;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TKCYSAMetaBlocks.registerItemModels();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        registerColors();
    }
}
