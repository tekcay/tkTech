package tkcy.simpleaddon.modules.capabilitiesmodule;

import net.minecraftforge.common.capabilities.Capability;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.*;

@Getter
@AllArgsConstructor
@Builder
public final class ContainerTypeWrapper<T extends DefaultContainer> {

    private Capability<T> capability;
    private MultiblockAbility<T> inputMultiblockAbility;
    private MultiblockAbility<T> outputMultiblockAbility;

    public ContainerTypeWrapper(int index, String name, Capability<T> capability) {
        this.capability = capability;
    }

    static final ContainerTypeWrapper<HeatContainer> HEAT_WRAPPER = new ContainerTypeWrapper.ContainerTypeWrapperBuilder<HeatContainer>()
            .capability(TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.HEAT_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.HEAT_CONTAINER_OUTPUT)
            .build();

    static final ContainerTypeWrapper<RotationContainer> ROTATION_WRAPPER = new ContainerTypeWrapper.ContainerTypeWrapperBuilder<RotationContainer>()
            .capability(TKCYSATileCapabilities.CAPABILITY_ROTATION_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_CONTAINER_OUTPUT)
            .build();

    static final ContainerTypeWrapper<RotationPowerContainer> ROTATION_POWER_WRAPPER = new ContainerTypeWrapper.ContainerTypeWrapperBuilder<RotationPowerContainer>()
            .capability(TKCYSATileCapabilities.CAPABILITY_ROTATION_POWER_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_POWER_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_POWER_CONTAINER_OUTPUT)
            .build();

    static final ContainerTypeWrapper<PressureContainer> PRESSURE_WRAPPER = new ContainerTypeWrapper.ContainerTypeWrapperBuilder<PressureContainer>()
            .capability(TKCYSATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)
            .build();
}
