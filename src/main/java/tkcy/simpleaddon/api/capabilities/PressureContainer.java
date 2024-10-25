package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface PressureContainer extends DefaultContainer {

    @Override
    default ContainerTypeWrapper<PressureContainer> getContainerTypeWrapper() {
        return ContainerTypeWrapper.PRESSURE_WRAPPER;
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
