package tkcy.tktech.api.unification.iconset;

import gregtech.api.unification.material.info.MaterialIconType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TKCYSAMaterialIconType {

    public static final MaterialIconType electrode = new MaterialIconType("electrode");

    public static final MaterialIconType cathode = new MaterialIconType("cathode");

    public static final MaterialIconType anode = new MaterialIconType("anode");
    public static final MaterialIconType curvedPlate = new MaterialIconType("curved_plate");

    // Components
    public static MaterialIconType lvComponents = new MaterialIconType("lvComponents");
    public static MaterialIconType mvComponents = new MaterialIconType("mvComponents");
    public static MaterialIconType hvComponents = new MaterialIconType("hvComponents");
    public static MaterialIconType evComponents = new MaterialIconType("evComponents");
    public static MaterialIconType ivComponents = new MaterialIconType("ivComponents");
    public static MaterialIconType luvComponents = new MaterialIconType("luvComponents");
    public static MaterialIconType zpmComponents = new MaterialIconType("zpmComponents");
    public static MaterialIconType uvComponents = new MaterialIconType("uvComponents");

    // Tools
    public static MaterialIconType toolTipSolderingIron = new MaterialIconType("toolTipSolderingIron");

    // Blocks
    public static MaterialIconType casing = new MaterialIconType("casing");
    public static MaterialIconType wall = new MaterialIconType("wall");
    public static MaterialIconType coil = new MaterialIconType("coil");
    public static MaterialIconType strippedWood = new MaterialIconType("stripped_wood");
}
