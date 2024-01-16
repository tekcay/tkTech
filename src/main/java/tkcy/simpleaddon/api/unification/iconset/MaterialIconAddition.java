package tkcy.simpleaddon.api.unification.iconset;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconSet;

public class MaterialIconAddition {

    public static void init() {
        Materials.StainlessSteel.setMaterialIconSet(MaterialIconSet.METALLIC);
    }
}
