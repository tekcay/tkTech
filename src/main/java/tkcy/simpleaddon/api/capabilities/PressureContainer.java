package tkcy.simpleaddon.api.capabilities;

public interface PressureContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.PRESSURE;
    }

    /**
     * Value in bar
     * 
     * @return 1;
     */
    @Override
    default int getDefaultValue() {
        return 1;
    }
}
