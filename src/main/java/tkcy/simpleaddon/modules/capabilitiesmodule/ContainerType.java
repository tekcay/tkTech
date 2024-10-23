package tkcy.simpleaddon.modules.capabilitiesmodule;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;

@Getter
@AllArgsConstructor
public enum ContainerType {

    HEAT(1),
    PRESSURE(2),
    ROTATION(3),
    TEMPERATURE(4),
    TORQUE(5),
    POWER(6);

    private final int index;

    public static DefaultContainer getContainer(@NotNull List<DefaultContainer> containers,
                                                ContainerType containerType) {
        return containers.get(containerType.getIndex());
    }

    public static void fillContainerList(@NotNull List<DefaultContainer> containers, DefaultContainer container) {
        containers.add(container.getContainerType().getIndex(), container);
    }
}
