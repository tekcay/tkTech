package tkcy.simpleaddon.modules.capabilitiesmodule;

import net.minecraftforge.common.capabilities.Capability;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import tkcy.simpleaddon.api.capabilities.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public final class ContainerTypeWrapper<T extends DefaultContainer> {

    private final ContainerType containerType;
    private final Capability<T> capability;
    private MultiblockAbility<T> inputMultiblockAbility;
    private MultiblockAbility<T> outputMultiblockAbility;

    public String getName() {
        return this.containerType.name();
    }

    public static final ContainerTypeWrapper<HeatContainer> HEAT_WRAPPER = new ContainerTypeWrapperBuilder<HeatContainer>()
            .containerType(ContainerType.HEAT)
            .capability(TKCYSATileCapabilities.CAPABILITY_HEAT_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.HEAT_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.HEAT_CONTAINER_OUTPUT)
            .build();

    public static final ContainerTypeWrapper<RotationContainer> ROTATION_WRAPPER = new ContainerTypeWrapperBuilder<RotationContainer>()
            .containerType(ContainerType.ROTATION)
            .capability(TKCYSATileCapabilities.CAPABILITY_ROTATION_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_CONTAINER_OUTPUT)
            .build();

    public static final ContainerTypeWrapper<RotationPowerContainer> ROTATION_POWER_WRAPPER = new ContainerTypeWrapperBuilder<RotationPowerContainer>()
            .containerType(ContainerType.ROTATION_POWER)
            .capability(TKCYSATileCapabilities.CAPABILITY_ROTATION_POWER_CONTAINER)
            .inputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_POWER_CONTAINER_INPUT)
            .outputMultiblockAbility(TKCYSAMultiblockAbility.ROTATION_POWER_CONTAINER_OUTPUT)
            .build();

    public static final ContainerTypeWrapper<PressureContainer> PRESSURE_WRAPPER = new ContainerTypeWrapperBuilder<PressureContainer>()
            .containerType(ContainerType.PRESSURE)
            .capability(TKCYSATileCapabilities.CAPABILITY_PRESSURE_CONTAINER)
            .build();

    public static final ContainerTypeWrapper<TemperatureContainer> TEMPERATURE_WRAPPER = new ContainerTypeWrapperBuilder<TemperatureContainer>()
            .containerType(ContainerType.TEMPERATURE)
            .capability(TKCYSATileCapabilities.CAPABILITY_TEMPERATURE_CONTAINER)
            .build();

    public static final ContainerTypeWrapper<TorqueContainer> TORQUE_WRAPPER = new ContainerTypeWrapperBuilder<TorqueContainer>()
            .containerType(ContainerType.TORQUE)
            .capability(TKCYSATileCapabilities.CAPABILITY_TORQUE_CONTAINER)
            .build();
}
