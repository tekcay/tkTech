package tkcy.simpleaddon.api.capabilities.helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityContainerModule;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleContainerWrapper {

    @Getter
    private DefaultContainer[] containers;

    @Nullable
    public DefaultContainer getContainer(ContainerType containerType) {
        return CapabilityContainerModule.getContainer(this, containerType);
    }

    public boolean hasTypeContainer(ContainerType containerType) {
        return CapabilityContainerModule.hasTypeContainer(this.containers, containerType);
    }

    /**
     * Provides an empty array to be filled later via
     * {@link CapabilityContainerModule#fillContainerList(DefaultContainer[], DefaultContainer)} by
     * {@link DefaultContainer}s that will be used.
     * <br>
     * The purpose of this is to easily retrieve a container by its id.
     * 
     * @return an empty {@code DefaultContainer[]} which length corresponds to the {@link ContainerType} enum
     *         length - 1.
     */
    private static DefaultContainer[] initContainerTypeList() {
        return new DefaultContainer[ContainerType.values().length - 1];
    }

    public static class MultipleContainerWrapperBuilder {

        private final DefaultContainer[] containers;

        public MultipleContainerWrapperBuilder() {
            this.containers = initContainerTypeList();
        }

        public MultipleContainerWrapperBuilder addContainer(@NotNull DefaultContainer defaultContainer) {
            CapabilityContainerModule.fillContainerList(this.containers, defaultContainer);
            return this;
        }

        public MultipleContainerWrapper build() {
            return new MultipleContainerWrapper(this.containers);
        }
    }
}
