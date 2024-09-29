package tkcy.simpleaddon.api.capabilities;

public interface RotationContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.ROTATION;
    }

    @Override
    default int getDefaultValue() {
        return 0;
    }
}
