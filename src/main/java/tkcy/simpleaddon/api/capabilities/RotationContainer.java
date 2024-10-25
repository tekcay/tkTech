package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface RotationContainer extends DefaultContainer {

    @Override
    default ContainerTypeWrapper<RotationContainer> getContainerTypeWrapper() {
        return ContainerTypeWrapper.ROTATION_WRAPPER;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.roundPerMinute;
    }
}
