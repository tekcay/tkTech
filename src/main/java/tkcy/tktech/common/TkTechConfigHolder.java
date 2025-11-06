package tkcy.tktech.common;

import net.minecraftforge.common.config.Config;

import lombok.experimental.UtilityClass;
import tkcy.tktech.TkTech;

@UtilityClass
@Config(modid = TkTech.MODID)
public class TkTechConfigHolder {

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
        public boolean enableHarderHydrogenation = true;
        public boolean enableHarderCracking = true;
        public boolean enableHarderCable = true;
        public boolean enableHarderFineWires = true;
        public boolean enableHarderRotors = true;
        public boolean enablePrimitiveCrafting = true;
        public boolean enableHarderWoodCrafting = true;
        public boolean enableHarderCircuitsCrafting = true;
    }

    @Config.Name("GamePlay")
    public static GamePlay gamePlay = new GamePlay();

    public static class GamePlay {

        public boolean enableGasReleaseDealsDamage = true;
    }
}
