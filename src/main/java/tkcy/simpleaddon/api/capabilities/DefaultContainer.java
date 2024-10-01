package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;

public interface DefaultContainer {

    int getDefaultValue();

    int getValue();

    int getMaxValue();

    int getMinValue();

    boolean increaseValue(int amount);

    ContainerType getContainerType();

    default boolean isTypeOf(ContainerType containerType) {
        return containerType == getContainerType();
    }

    CommonUnits getBaseUnit();
}
