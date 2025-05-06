package tkcy.tktech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.api.TKCYSAInternalTags;
import tkcy.tktech.api.utils.TKCYSALog;
import tkcy.tktech.common.CommonProxy;
import tkcy.tktech.common.block.TKCYSAMetaBlocks;
import tkcy.tktech.common.item.TKCYSAMetaItems;
import tkcy.tktech.common.item.TKCYSAToolItems;
import tkcy.tktech.common.metatileentities.TKCYSAMetaTileEntities;

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
