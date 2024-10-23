package tkcy.simpleaddon.modules.capabilitiesmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;

public class CapabilityModule {

    /**
     * Acts as a simple marker to retrieve any class related to this feature
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
    public @interface Capabilities {}

    @Getter
    @AllArgsConstructor
    public enum ContainerType {

        HEAT(1),
        PRESSURE(2),
        ROTATION(3),
        TEMPERATURE(4),
        TORQUE(5),
        ROTATION_POWER(6);

        private final int index;

        public static DefaultContainer getContainer(@NotNull List<DefaultContainer> containers,
                                                    ContainerType containerType) {
            return containers.get(containerType.getIndex());
        }

        public static void fillContainerList(@NotNull List<DefaultContainer> containers, DefaultContainer container) {
            containers.add(container.getContainerType().getIndex(), container);
        }
    }
}
