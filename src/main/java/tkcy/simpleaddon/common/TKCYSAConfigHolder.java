package tkcy.simpleaddon.common;

import net.minecraftforge.common.config.Config;

import tkcy.simpleaddon.TekCaySimpleAddon;

@Config(modid = TekCaySimpleAddon.MODID)
public class TKCYSAConfigHolder {

    @Config.Comment("Config options applying to all chains")
    @Config.Name("Chains")
    public static Chains chains = new Chains();

    public static class Chains {

        public boolean enableGoldChain = true;
        public boolean enableIronChain = true;
        public boolean enableChromiteChain = true;
        public boolean enableTungstenChain = true;
        public boolean enableRoastingChain = true;
        public boolean enableZincChain = true;
        public boolean enableGermaniumChain = true;
        public boolean enableCopperChain = true;
        public boolean enableAluminiumChain = true;
        public boolean enablePlatinumGroupChains = true;
    }

    @Config.Comment("Config options applying to all chains")
    @Config.Name("Harder stuff")
    public static HarderStuff harderStuff = new HarderStuff();

    public static class HarderStuff {

        public boolean enableHarderMachineCasings = true;
        public boolean enableAlloyingAndCasting = true;
        public boolean enableHarderCoils = true;
        public boolean enableHarderPolarization = true;
        public boolean removeTinCircuitRecipes = true;
        public boolean enableHarderComponents = true;
        public boolean enableMethaneCracking = true;
        public boolean enableHarderHydrogenation = true;
    }
}
