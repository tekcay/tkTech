package tkcy.tktech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.api.TkTechInternalTags;
import tkcy.tktech.api.fluid.FluidRegistration;
import tkcy.tktech.api.utils.TkTechLog;
import tkcy.tktech.common.CommonProxy;
import tkcy.tktech.common.block.TkTechMetaBlocks;
import tkcy.tktech.common.item.TkTechMetaItems;
import tkcy.tktech.common.item.TkTechToolItems;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

@Mod(modid = TkTech.MODID,
     name = TkTech.NAME,
     version = TkTech.VERSION,
     dependencies = TkTechInternalTags.DEP_VERSION_STRING)
public class TkTech {

    public static final String MODID = "tktech";
    public static final String NAME = "TkTech";
    public static final String VERSION = tkcy.TkTechInternalTags.VERSION;

    @SidedProxy(modId = MODID,
                clientSide = "tkcy.tktech.common.ClientProxy",
                serverSide = "tkcy.tktech.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(@NotNull FMLPreInitializationEvent event) {
        proxy.preLoad();

        TkTechLog.init(event.getModLog());
        TkTechMetaBlocks.init();
        TkTechMetaItems.init();
        TkTechMetaTileEntities.init();
        TkTechToolItems.init();
        FluidRegistration.register();
    }

    @Mod.EventHandler
    public void onInit(@NotNull FMLInitializationEvent event) {
        proxy.onLoad();
    }
}
