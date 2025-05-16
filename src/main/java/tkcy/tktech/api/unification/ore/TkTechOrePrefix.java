package tkcy.tktech.api.unification.ore;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static gregtech.api.unification.ore.OrePrefix.Flags.SELF_REFERENCING;

import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.info.MaterialFlags;
import gregtech.api.unification.ore.OrePrefix;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.unification.flags.TkTechMaterialFlags;
import tkcy.tktech.api.unification.iconset.TkTechMaterialIconType;

@UtilityClass
public class TkTechOrePrefix {

    public static final OrePrefix cathode = new OrePrefix("cathode", M, null, TkTechMaterialIconType.cathode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TkTechMaterialFlags.GENERATE_ELECTRODES));

    public static final OrePrefix anode = new OrePrefix("anode", M, null, TkTechMaterialIconType.anode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TkTechMaterialFlags.GENERATE_ELECTRODES));

    public static final OrePrefix electrode = new OrePrefix("electrode", M, null, TkTechMaterialIconType.electrode,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TkTechMaterialFlags.GENERATE_ELECTRODES));

    public static final OrePrefix curvedPlate = new OrePrefix("curved_plate", M, null,
            TkTechMaterialIconType.curvedPlate,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(MaterialFlags.GENERATE_ROTOR));

    public static final OrePrefix denseScrap = new OrePrefix("dense_scrap", M / 9, null,
            TkTechMaterialIconType.denseScrap,
            ENABLE_UNIFICATION,
            mat -> mat.hasFlag(MaterialFlags.GENERATE_PLATE));

    // Components
    public static final OrePrefix lvComponents = new OrePrefix("lvComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.lvComponents, SELF_REFERENCING, null);
    public static final OrePrefix mvComponents = new OrePrefix("mvComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.mvComponents, SELF_REFERENCING, null);
    public static final OrePrefix hvComponents = new OrePrefix("hvComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.hvComponents, SELF_REFERENCING, null);
    public static final OrePrefix evComponents = new OrePrefix("evComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.evComponents, SELF_REFERENCING, null);
    public static final OrePrefix ivComponents = new OrePrefix("ivComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.ivComponents, SELF_REFERENCING, null);
    public static final OrePrefix luvComponents = new OrePrefix("luvComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.luvComponents, SELF_REFERENCING, null);
    public static final OrePrefix zpmComponents = new OrePrefix("zpmComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.zpmComponents, SELF_REFERENCING, null);
    public static final OrePrefix uvComponents = new OrePrefix("uvComponents", -1, MarkerMaterials.Empty,
            TkTechMaterialIconType.uvComponents, SELF_REFERENCING, null);

    // Tools
    public static final OrePrefix toolTipSolderingIron = new OrePrefix("toolTipSolderingIron", 1, null,
            TkTechMaterialIconType.toolTipSolderingIron, ENABLE_UNIFICATION,
            mat -> mat.hasFlag(TkTechMaterialFlags.GENERATE_SOLDERING_IRON_TIP));

    // OreBlocks
    public static final OrePrefix casing = new OrePrefix("casing", M * 9, null, TkTechMaterialIconType.casing,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix wall = new OrePrefix("wall", M * 9, null, TkTechMaterialIconType.wall,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix coil = new OrePrefix("coil", M * 9, null, TkTechMaterialIconType.coil,
            ENABLE_UNIFICATION, null);
    public static final OrePrefix strippedWood = new OrePrefix("strippedWood", M * 9, MarkerMaterials.Empty,
            TkTechMaterialIconType.strippedWood,
            SELF_REFERENCING, null);
}
