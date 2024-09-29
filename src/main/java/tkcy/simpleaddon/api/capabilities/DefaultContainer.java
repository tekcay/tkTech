package tkcy.simpleaddon.api.capabilities;

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
}
