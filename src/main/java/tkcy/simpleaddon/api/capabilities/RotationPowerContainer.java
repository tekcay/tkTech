package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface RotationPowerContainer extends DefaultContainer {

    @Override
    default ContainerTypeWrapper<RotationPowerContainer> getContainerTypeWrapper() {
        return ContainerTypeWrapper.ROTATION_POWER_WRAPPER;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.newtonMeter;
    }
}
