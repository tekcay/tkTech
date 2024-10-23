package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

public interface DefaultContainer {

    int getDefaultValue();

    int getValue();

    int getMaxValue();

    default int getMinValue() {
        return 0;
    }

    boolean increaseValue(int amount);

    CapabilityModule.ContainerType getContainerType();

    CommonUnits getBaseUnit();

    default boolean isTypeOf(CapabilityModule.ContainerType containerType) {
        return containerType == getContainerType();
    }

    default boolean isEmpty() {
        return getValue() == getMinValue();
    }

    default boolean isFull() {
        return getValue() == getMaxValue();
    }
}
