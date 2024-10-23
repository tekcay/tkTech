package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

public interface TorqueContainer extends DefaultContainer {

    @Override
    default CapabilityModule.ContainerType getContainerType() {
        return CapabilityModule.ContainerType.TORQUE;
    }

    @Override
    default int getDefaultValue() {
        return 0;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.newtonMeter;
    }
}
