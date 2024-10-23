package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

public interface PressureContainer extends DefaultContainer {

    @Override
    default CapabilityModule.ContainerType getContainerType() {
        return CapabilityModule.ContainerType.PRESSURE;
    }

    @Override
    default int getDefaultValue() {
        return 1;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.bar;
    }
}
