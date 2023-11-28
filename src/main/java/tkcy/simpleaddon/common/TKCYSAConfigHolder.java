package tkcy.simpleaddon.common;

import net.minecraftforge.common.config.Config;

import tkcy.simpleaddon.TekCaySimpleAddon;

@Config(modid = TekCaySimpleAddon.MODID)
public class TKCYSAConfigHolder {

    @Config.Comment("Config options applying to all GCYM Multiblocks")
    @Config.Name("Global Multiblock Options")
    public static GlobalMultiblocks globalMultiblocks = new GlobalMultiblocks();

    public static class GlobalMultiblocks {

        @Config.Comment({ "Makes nearly every GCYM Multiblock require blocks which set their maximum voltages.",
                "Default: false" })
        public boolean enableTieredCasings = false;
    }
}
