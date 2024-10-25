package tkcy.simpleaddon.api.capabilities;

import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public interface DefaultContainer {

    default int getDefaultValue() {
        return 0;
    }

    int getValue();

    int getMaxValue();

    default int getMinValue() {
        return 0;
    }

    boolean increaseValue(int amount);

    void setValue(int amount);

    ContainerTypeWrapper<? extends DefaultContainer> getContainerTypeWrapper();

    CommonUnits getBaseUnit();

    default boolean isTypeOf(ContainerTypeWrapper<? extends DefaultContainer> containerType) {
        return containerType == getContainerTypeWrapper();
    }

    default boolean isEmpty() {
        return getValue() == getMinValue();
    }

    default boolean isFull() {
        return getValue() == getMaxValue();
    }

    default String printValue() {
        return String.format("%d %s", getValue(), getBaseUnit());
    }

    default String printMaxValue() {
        return String.format("%d %s", getMaxValue(), getBaseUnit());
    }

    default String printMinValue() {
        return String.format("%d %s", getMinValue(), getBaseUnit());
    }
}
