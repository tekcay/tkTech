package tkcy.tktech.api.fluid;

import gregtech.api.fluids.FluidState;
import gregtech.api.fluids.store.FluidStorageKey;
import gregtech.api.unification.material.info.MaterialIconType;

import lombok.experimental.UtilityClass;
import tkcy.tktech.api.utils.TkTechUtil;

@UtilityClass
public class TkTechFluidStorageKeys {

    public static final FluidStorageKey DISTILLED = new FluidStorageKey(TkTechUtil.tktech("distilled"),
            MaterialIconType.liquid,
            m -> "distilled." + m.getName(),
            m -> "tktech.fluid.distilled",
            FluidState.LIQUID);
}
