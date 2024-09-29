package tkcy.simpleaddon.api.capabilities;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public class MultipleContainerWrapper {

    @Getter
    private List<DefaultContainer> containers;

    MultipleContainerWrapper(DefaultContainer... containers) {
        this.containers = Arrays.asList(containers);
    }

    public boolean hasTypeContainer(ContainerType type) {
        return this.containers.stream()
                .anyMatch(defaultContainer -> defaultContainer.isTypeOf(type));
    }

    @Nullable
    public HeatContainer getHeatContainer() {
        return this.containers.stream()
                .filter(defaultContainer -> defaultContainer.isTypeOf(ContainerType.HEAT))
                .map(defaultContainer -> (HeatContainer) defaultContainer)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public PressureContainer getPressureContainer() {
        return this.containers.stream()
                .filter(defaultContainer -> defaultContainer.isTypeOf(ContainerType.PRESSURE))
                .map(defaultContainer -> (PressureContainer) defaultContainer)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public RotationContainer getRotationContainer() {
        return this.containers.stream()
                .filter(defaultContainer -> defaultContainer.isTypeOf(ContainerType.ROTATION))
                .map(defaultContainer -> (RotationContainer) defaultContainer)
                .findFirst()
                .orElse(null);
    }
}
