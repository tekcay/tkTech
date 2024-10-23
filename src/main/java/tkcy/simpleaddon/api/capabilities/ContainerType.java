package tkcy.simpleaddon.api.capabilities;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public enum ContainerType {

    HEAT,
    PRESSURE,
    ROTATION;

    public static DefaultContainer getContainer(@NotNull List<DefaultContainer> containers,
                                                ContainerType containerType) {
        return containers.get(containerType.ordinal());
    }

    public static void fillContainerList(@NotNull List<DefaultContainer> containers, DefaultContainer container) {
        containers.add(container.getContainerType().ordinal(), container);
    }
}
