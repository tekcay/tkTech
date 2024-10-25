package tkcy.simpleaddon.modules.capabilitiesmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.api.capabilities.helpers.MultipleContainerWrapper;

@UtilityClass
public class CapabilityContainerModule {

    /**
     * Acts as a simple marker to retrieve any class related to this feature
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
    public @interface Capabilities {}

    public static <T extends DefaultContainer> boolean hasTypeContainer(T[] containers, ContainerType containerType) {
        return containers.length >= containerType.getId() && containers[containerType.getId()] != null;
    }

    @Nullable
    public static DefaultContainer getContainer(@NotNull DefaultContainer[] containers,
                                                ContainerType containerType) {
        return hasTypeContainer(containers, containerType) ? containers[containerType.getId()] : null;
    }

    @Nullable
    public static DefaultContainer getContainer(@NotNull MultipleContainerWrapper wrapper,
                                                ContainerType containerType) {
        if (hasTypeContainer(wrapper.getContainers(), containerType))
        return getContainer(wrapper.getContainers(), containerType);
        else return null;
    }

    public static void fillContainerList(@NotNull DefaultContainer[] containers, DefaultContainer container) {
        containers[container.getContainerTypeWrapper().getContainerType().getId()] = container;
    }
}
