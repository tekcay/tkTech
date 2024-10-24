package tkcy.simpleaddon.modules.capabilitiesmodule;

import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.*;

@CapabilityModule.Capabilities
@Getter
public enum ContainerType {

    HEAT(ContainerTypeWrapper.HEAT_WRAPPER),
    ROTATION(ContainerTypeWrapper.ROTATION_WRAPPER),
    ROTATION_POWER(ContainerTypeWrapper.ROTATION_POWER_WRAPPER),
    TEMPERATURE(),
    TORQUE(),
    PRESSURE(ContainerTypeWrapper.PRESSURE_WRAPPER);

    ContainerType() {
        this.index = CapabilityModule.setIndex();
    }

    ContainerType(ContainerTypeWrapper<? extends DefaultContainer> containerTypeWrapper) {
        this.containerTypeWrapper = containerTypeWrapper;
        this.index = CapabilityModule.setIndex();
    }

    private final int index;
    private ContainerTypeWrapper<? extends DefaultContainer> containerTypeWrapper;
}
