package tkcy.simpleaddon.api.capabilities.helpers;

import org.jetbrains.annotations.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MultipleContainerWrapper {

    @Getter
    private DefaultContainer[] containers;

    public boolean hasTypeContainer(CapabilityModule.ContainerType type) {
        return this.containers[type.getIndex()] != null;
    }

    public DefaultContainer getContainer(CapabilityModule.ContainerType type) {
        return CapabilityModule.getContainer(this, type);
    }

    public static class MultipleContainerWrapperBuilder {

        private final DefaultContainer[] containers;

        public MultipleContainerWrapperBuilder() {
            this.containers = CapabilityModule.initContainerTypeList();
        }

        public MultipleContainerWrapperBuilder addContainer(@NotNull DefaultContainer defaultContainer) {
            CapabilityModule.fillContainerList(this.containers, defaultContainer);
            return this;
        }

        public MultipleContainerWrapper build() {
            return new MultipleContainerWrapper(this.containers);
        }
    }
}
