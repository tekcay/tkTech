package tkcy.simpleaddon.common.metatileentities;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static tkcy.simpleaddon.api.utils.TKCYSAUtil.tkcysa;

import tkcy.simpleaddon.common.metatileentities.multiprimitive.PrimitiveRoastingOven;

public final class TKCYSAMetaTileEntities {

    public static PrimitiveRoastingOven PRIMITIVE_ROASTING_OVEN;

    private TKCYSAMetaTileEntities() {}

    public static void init() {
        PRIMITIVE_ROASTING_OVEN = registerMetaTileEntity(4000, new PrimitiveRoastingOven(tkcysa("primitive_roasting_oven")));
    }
}
