package tkcy.simpleaddon.api.unification.ore;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static gregtech.api.unification.ore.OrePrefix.Flags.SELF_REFERENCING;

import gregtech.api.unification.material.MarkerMaterials;
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

    // Components
    public static final OrePrefix lvComponents = new OrePrefix("lvComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.lvComponents, SELF_REFERENCING, null);
    public static final OrePrefix mvComponents = new OrePrefix("mvComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.mvComponents, SELF_REFERENCING, null);
    public static final OrePrefix hvComponents = new OrePrefix("hvComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.hvComponents, SELF_REFERENCING, null);
    public static final OrePrefix evComponents = new OrePrefix("evComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.evComponents, SELF_REFERENCING, null);
    public static final OrePrefix ivComponents = new OrePrefix("ivComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.ivComponents, SELF_REFERENCING, null);
    public static final OrePrefix luvComponents = new OrePrefix("luvComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.luvComponents, SELF_REFERENCING, null);
    public static final OrePrefix zpmComponents = new OrePrefix("zpmComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.zpmComponents, SELF_REFERENCING, null);
    public static final OrePrefix uvComponents = new OrePrefix("uvComponents", -1, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.uvComponents, SELF_REFERENCING, null);
}
