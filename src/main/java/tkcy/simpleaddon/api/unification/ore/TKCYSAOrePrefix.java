package tkcy.simpleaddon.api.unification.ore;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static gregtech.api.unification.ore.OrePrefix.Flags.SELF_REFERENCING;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.unification.flags.TKCYSAMaterialFlags;
import tkcy.simpleaddon.api.unification.iconset.TKCYSAMaterialIconType;

@UtilityClass
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

    public static final OrePrefix curvedPlate = new OrePrefix("curved_plate", M, null,
            TKCYSAMaterialIconType.curvedPlate,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(MaterialFlags.GENERATE_ROTOR));

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

    // Tools
    public static final OrePrefix toolTipSolderingIron = new OrePrefix("toolTipSolderingIron", 1, null,
            TKCYSAMaterialIconType.toolTipSolderingIron, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TKCYSAMaterialFlags.GENERATE_SOLDERING_IRON_TIP));

    // OreBlocks
    public static final OrePrefix casing = new OrePrefix("casing", M * 9, null, TKCYSAMaterialIconType.casing,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix wall = new OrePrefix("wall", M * 9, null, TKCYSAMaterialIconType.wall,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix coil = new OrePrefix("coil", M * 9, null, TKCYSAMaterialIconType.coil,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix strippedWood = new OrePrefix("strippedWood", M * 9, MarkerMaterials.Empty,
            TKCYSAMaterialIconType.strippedWood,
            SELF_REFERENCING, null);
}
