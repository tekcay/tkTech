package tkcy.tktech.modules;

import static gregtech.api.unification.material.Materials.*;

import java.util.Arrays;
import java.util.List;

import gregtech.api.unification.material.Material;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PetroChemModule {

    public static final List<Material> sulfuricLayers = Arrays.asList(SulfuricHeavyFuel, SulfuricNaphtha,
            SulfuricLightFuel, SulfuricGas);
    public static final List<Material> desulfurizedFuels = Arrays.asList(HeavyFuel, Naphtha, LightFuel, RefineryGas);
    public static final List<Material> lightlyHydroCracked = Arrays.asList(LightlyHydroCrackedHeavyFuel,
            LightlyHydroCrackedNaphtha, LightlyHydroCrackedLightFuel, LightlyHydroCrackedGas);
    public static final List<Material> lightlySteamCracked = Arrays.asList(LightlySteamCrackedHeavyFuel,
            LightlySteamCrackedNaphtha, LightlySteamCrackedLightFuel, LightlySteamCrackedGas);
    public static final List<Material> severelyHydroCracked = Arrays.asList(SeverelyHydroCrackedHeavyFuel,
            SeverelyHydroCrackedNaphtha, SeverelyHydroCrackedLightFuel, SeverelyHydroCrackedGas);
    public static final List<Material> severelySteamCracked = Arrays.asList(SeverelySteamCrackedHeavyFuel,
            SeverelySteamCrackedNaphtha, SeverelySteamCrackedLightFuel, SeverelySteamCrackedGas);
    public static final List<Material> hydrocarbonMaterials = Arrays.asList(Ethane, Ethylene, Propene, Propane, Butane,
            Butene,
            Butadiene);
    public static final List<Material> hydroCrackedHydrocarbonMaterials = Arrays.asList(HydroCrackedEthane,
            HydroCrackedEthylene, HydroCrackedPropene, HydroCrackedPropane, HydroCrackedButane, HydroCrackedButene,
            HydroCrackedButadiene);
    public static final List<Material> steamCrackedHydrocarbonMaterials = Arrays.asList(SteamCrackedEthane,
            SteamCrackedEthylene, SteamCrackedPropene, SteamCrackedPropane, SteamCrackedButane, SteamCrackedButene,
            SteamCrackedButadiene);
}
