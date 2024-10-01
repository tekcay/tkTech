package tkcy.simpleaddon.modules.alloyingmodule;

import static gregtech.api.unification.material.Materials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.utils.MaterialHelper;

@Alloying
@UtilityClass
public class AlloyingModule {

    public static List<Material> alloysMaterials = new ArrayList<>();

    static {
        alloysMaterials.add(Bronze);
        alloysMaterials.add(SolderingAlloy);
        alloysMaterials.add(TinAlloy);
        alloysMaterials.add(RedAlloy);
        alloysMaterials.add(Magnalium);
        alloysMaterials.add(Invar);
        alloysMaterials.add(TungstenSteel);
        alloysMaterials.add(StainlessSteel);
        alloysMaterials.add(BlueAlloy);
        alloysMaterials.add(HSSE);
        alloysMaterials.add(HSSG);
        alloysMaterials.add(HSSS);
        alloysMaterials.add(BlueSteel);
        alloysMaterials.add(Osmiridium);
        alloysMaterials.add(CobaltBrass);
        alloysMaterials.add(Brass);
        alloysMaterials.add(Ultimet);
        alloysMaterials.add(VanadiumSteel);
        alloysMaterials.add(Potin);
        alloysMaterials.add(BatteryAlloy);
        alloysMaterials.add(Cupronickel);
        alloysMaterials.add(Electrum);
        alloysMaterials.add(Kanthal);
        alloysMaterials.add(Nichrome);
        alloysMaterials.add(SterlingSilver);
        alloysMaterials.add(RoseGold);
        alloysMaterials.add(BismuthBronze);
        alloysMaterials.add(Ruridit);
        alloysMaterials.add(BlackSteel);
        alloysMaterials.add(IndiumTinBariumTitaniumCuprate);
        alloysMaterials.add(YttriumBariumCuprate);

    }

    public static void setAlloyFluidTemperature() {
        AlloyingModule.alloysMaterials.stream()
                .filter(Material::hasFluid)
                .forEach(MaterialHelper::setWeightMp);
    }
}
