package tkcy.simpleaddon.modules.capabilitiesmodule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import lombok.experimental.UtilityClass;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.api.capabilities.helpers.MultipleContainerWrapper;

@UtilityClass
public class CapabilityModule {

    /**
     * Acts as a simple marker to retrieve any class related to this feature
     */
    @Retention(RetentionPolicy.SOURCE)
    @Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
    public @interface Capabilities {}

    private static int containerTypeIndex = 0;

    public static int setIndex() {
        return containerTypeIndex++;
    }

    public static DefaultContainer getContainer(@NotNull DefaultContainer[] containers,
                                                ContainerType containerType) {
        return containers[containerType.getIndex()];
    }

    public static DefaultContainer getContainer(@NotNull MultipleContainerWrapper wrapper,
                                                ContainerType containerType) {
        return getContainer(wrapper.getContainers(), containerType);
    }

    public static void fillContainerList(@NotNull DefaultContainer[] containers, DefaultContainer container) {
        containers[container.getContainerType().getIndex()] = container;
    }

    /**
     * Provides an empty array to be filled later via
     * {@link CapabilityModule#fillContainerList(DefaultContainer[], DefaultContainer)} by
     * {@link DefaultContainer}s that will be used.
     * <br>
     * The purpose of this is to easily retrieve a container by its index.
     * 
     * @return an empty {@code DefaultContainer[]} which length corresponds to the {@link ContainerType} enum
     *         length - 1.
     */
    public static DefaultContainer[] initContainerTypeList() {
        return new DefaultContainer[ContainerType.values().length - 1];
    }

    public static void doStuff(Consumer<ContainerType> consumer) {
        Arrays.stream(ContainerType.values()).forEach(consumer);
    }
}
