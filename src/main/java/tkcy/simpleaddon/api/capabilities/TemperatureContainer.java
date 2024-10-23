package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public interface TemperatureContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.TEMPERATURE;
    }

    @Override
    default int getMinValue() {
        return 0;
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
