package tkcy.simpleaddon.modules;

import static gregtech.api.unification.material.Materials.*;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.unification.material.Material;

public class AlloyingModules {

    public static List<Material> materialsAlloy = new ArrayList<>();
    public static List<Material> missingFluidPropertyMaterial = new ArrayList<>();

    static {
        materialsAlloy.add(Bronze);
        materialsAlloy.add(SolderingAlloy);
        materialsAlloy.add(TinAlloy);
        materialsAlloy.add(RedAlloy);
        materialsAlloy.add(Magnalium);
        materialsAlloy.add(Invar);
        materialsAlloy.add(TungstenSteel);
        materialsAlloy.add(StainlessSteel);
        materialsAlloy.add(BlueAlloy);
        materialsAlloy.add(HSSE);
        materialsAlloy.add(HSSG);
        materialsAlloy.add(HSSS);
        materialsAlloy.add(BlueSteel);
        materialsAlloy.add(Osmiridium);
        materialsAlloy.add(CobaltBrass);
        materialsAlloy.add(Brass);
        materialsAlloy.add(Ultimet);
        materialsAlloy.add(VanadiumSteel);
        materialsAlloy.add(Potin);
        materialsAlloy.add(BatteryAlloy);
        materialsAlloy.add(Cupronickel);
        materialsAlloy.add(Electrum);
        materialsAlloy.add(Kanthal);
        materialsAlloy.add(Nichrome);
        materialsAlloy.add(SterlingSilver);
        materialsAlloy.add(RoseGold);
        materialsAlloy.add(BismuthBronze);
        materialsAlloy.add(Ruridit);
        materialsAlloy.add(BlackSteel);
        materialsAlloy.add(IndiumTinBariumTitaniumCuprate);
        materialsAlloy.add(YttriumBariumCuprate);
    }

    static {
        missingFluidPropertyMaterial.add(Barium);
        missingFluidPropertyMaterial.add(Electrotine);
    }
}
