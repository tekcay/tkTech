package tkcy.simpleaddon.api.unification.ore;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

import gregtech.api.unification.ore.OrePrefix;

import tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags;
import tkcy.simpleaddon.api.unification.iconset.TKCYSAMaterialIconType;

public class TKCYSAOrePrefix {

    public static final OrePrefix cathode = new OrePrefix("cathode", M, null, TKCYSAMaterialIconType.cathode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TKCYSAMaterialFlags.GENERATE_ELECTRODES));

    public static final OrePrefix anode = new OrePrefix("anode", M, null, TKCYSAMaterialIconType.anode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TKCYSAMaterialFlags.GENERATE_ELECTRODES));

    public static final OrePrefix electrode = new OrePrefix("electrode", M, null, TKCYSAMaterialIconType.electrode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TKCYSAMaterialFlags.GENERATE_ELECTRODES));
}
