package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public interface PressureContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.PRESSURE;
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
