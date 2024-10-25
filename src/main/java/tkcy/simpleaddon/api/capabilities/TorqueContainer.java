package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface TorqueContainer extends DefaultContainer {

    @Override
    default ContainerTypeWrapper<TorqueContainer> getContainerTypeWrapper() {
        return ContainerTypeWrapper.TORQUE_WRAPPER;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.newtonMeter;
    }
}
