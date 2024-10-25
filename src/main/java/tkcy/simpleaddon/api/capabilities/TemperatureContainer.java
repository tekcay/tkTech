package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface TemperatureContainer extends DefaultContainer {

    @Override
    default ContainerTypeWrapper<TemperatureContainer> getContainerTypeWrapper() {
        return ContainerTypeWrapper.TEMPERATURE_WRAPPER;
    }

    @Override
    default int getDefaultValue() {
        return 298;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.kelvin;
    }
}
