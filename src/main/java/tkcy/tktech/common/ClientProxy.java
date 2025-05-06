package tkcy.tktech.common;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tkcy.tktech.TkTech;
import tkcy.tktech.api.render.TkTechTextures;
import tkcy.tktech.common.block.TkTechMetaBlocks;
import tkcy.tktech.common.item.TkTechToolItems;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = TkTech.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preLoad() {
        super.preLoad();
        TkTechTextures.preInit();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TkTechMetaBlocks.registerItemModels();
        TkTechToolItems.registerModels();
    }

    @Override
    public void onLoad() {
        registerColors();
    }

    public static void registerColors() {
        TkTechToolItems.registerColors();
        TkTechMetaBlocks.registerColors();
    }
}
