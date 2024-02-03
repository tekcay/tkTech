package tkcy.simpleaddon.modules;

import static gregtech.api.unification.ore.OrePrefix.*;
import static gregtech.api.unification.ore.OrePrefix.wireGtHex;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import gregtech.api.unification.ore.OrePrefix;

public class CablesModule {

    public static final Map<OrePrefix, OrePrefix> cableToWireMap = ImmutableMap.of(
            cableGtSingle, wireGtSingle,
            cableGtDouble, wireGtDouble,
            cableGtQuadruple, wireGtQuadruple,
            cableGtOctal, wireGtOctal,
            cableGtHex, wireGtHex);
}
