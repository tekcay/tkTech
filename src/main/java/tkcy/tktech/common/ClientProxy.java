package tkcy.tktech.common;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tkcy.tktech.TekCaySimpleAddon;
import tkcy.tktech.api.render.TKCYSATextures;
import tkcy.tktech.common.block.TKCYSAMetaBlocks;
import tkcy.tktech.common.item.TKCYSAToolItems;

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
