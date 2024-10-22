package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;

public interface DefaultContainer {

    int getDefaultValue();

    int getValue();

    int getMaxValue();

    int getMinValue();

    boolean increaseValue(int amount);

    ContainerType getContainerType();

    CommonUnits getBaseUnit();

    default boolean isTypeOf(ContainerType containerType) {
        return containerType == getContainerType();
    }
    default boolean isEmpty() {
        return getValue() == getMinValue();
    }
    default boolean isFull() {
        return getValue() == getMaxValue();
    }
}
