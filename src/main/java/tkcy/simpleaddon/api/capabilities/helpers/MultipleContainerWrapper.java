package tkcy.simpleaddon.api.capabilities.helpers;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.DefaultContainer;
import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

@AllArgsConstructor
@Getter
public class MultipleContainerWrapper {

    @Getter
    private List<DefaultContainer> containers;

    public boolean hasTypeContainer(CapabilityModule.ContainerType type) {
        return this.containers.stream()
                .anyMatch(defaultContainer -> defaultContainer.isTypeOf(type));
    }

    public static class MultipleContainerWrapperBuilder {

        private final List<DefaultContainer> containers;

        public MultipleContainerWrapperBuilder() {
            this.containers = new ArrayList<>();
        }

        public MultipleContainerWrapperBuilder addContainer(@NotNull DefaultContainer defaultContainer) {
            CapabilityModule.ContainerType.fillContainerList(this.containers, defaultContainer);
            return this;
        }

        public MultipleContainerWrapper build() {
            return new MultipleContainerWrapper(this.containers);
        }
    }
}
