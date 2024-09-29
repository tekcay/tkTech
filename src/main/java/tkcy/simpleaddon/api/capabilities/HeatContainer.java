package tkcy.simpleaddon.api.capabilities;

public interface HeatContainer extends DefaultContainer {

    @Override
    default ContainerType getContainerType() {
        return ContainerType.HEAT;
    }

    @Override
    default int getMinValue() {
        return 0;
    }

    @Override
    default int getDefaultValue() {
        return 0;
    }
}
