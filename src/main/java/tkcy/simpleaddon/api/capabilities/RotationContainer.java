package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

public interface RotationContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.ROTATION;
    }

    @Override
    default int getDefaultValue() {
        return 0;
    }

    @Override
    default CommonUnits getBaseUnit() {
        return CommonUnits.roundPerMinute;
    }
}
