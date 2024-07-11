package tkcy.simpleaddon;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.jetbrains.annotations.NotNull;

import tkcy.simpleaddon.api.TKCYSAInternalTags;
import tkcy.simpleaddon.api.utils.TKCYSALog;
import tkcy.simpleaddon.common.CommonProxy;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.common.item.TKCYSAMetaItems;
import tkcy.simpleaddon.common.item.TKCYSAToolItems;
import tkcy.simpleaddon.common.metatileentities.TKCYSAMetaTileEntities;

@Mod(modid = TekCaySimpleAddon.MODID,
     name = TekCaySimpleAddon.NAME,
     version = TekCaySimpleAddon.VERSION,
     dependencies = TKCYSAInternalTags.DEP_VERSION_STRING)
public class TekCaySimpleAddon {

    public static final String MODID = "tkcysa";
    public static final String NAME = "TeK CaY Simple Addon";
    public static final String VERSION = tkcy.TKCYSAInternalTags.VERSION;

    @SidedProxy(modId = MODID,
                clientSide = "tkcy.simpleaddon.common.ClientProxy",
                serverSide = "tkcy.simpleaddon.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        proxy.preLoad();

        TKCYSALog.init(event.getModLog());
        TKCYSAMetaBlocks.init();
        TKCYSAMetaItems.init();
        TKCYSAMetaTileEntities.init();
        TKCYSAToolItems.init();
    }

    @Mod.EventHandler
    public void onInit(@NotNull FMLInitializationEvent event) {
        proxy.onLoad();
    }
}
