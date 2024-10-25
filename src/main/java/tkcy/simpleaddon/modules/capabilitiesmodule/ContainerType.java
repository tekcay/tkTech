package tkcy.simpleaddon.modules.capabilitiesmodule;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PACKAGE)
public enum ContainerType {

    HEAT,
    ROTATION,
    ROTATION_POWER,
    TEMPERATURE,
    TORQUE,
    PRESSURE;

    private final int id;

    ContainerType() {
        this.id = setIndex();
    }

    private static int containerTypeId = 0;

    private static int setIndex() {
        return containerTypeId++;
    }
}
