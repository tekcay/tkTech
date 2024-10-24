package tkcy.simpleaddon.api.capabilities;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;

import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerType;

@CapabilityModule.Capabilities
@SuppressWarnings("InstantiationOfUtilityClass")
public class TKCYSAMultiblockAbility {

    public static final MultiblockAbility<HeatContainer> HEAT_CONTAINER_INPUT = new MultiblockAbility<>(
            ContainerType.HEAT + "_input");
    public static final MultiblockAbility<HeatContainer> HEAT_CONTAINER_OUTPUT = new MultiblockAbility<>(
            ContainerType.HEAT + "_output");
    public static final MultiblockAbility<RotationContainer> ROTATION_CONTAINER_INPUT = new MultiblockAbility<>(
            ContainerType.ROTATION + "_input");
    public static final MultiblockAbility<RotationContainer> ROTATION_CONTAINER_OUTPUT = new MultiblockAbility<>(
            ContainerType.ROTATION + "_output");
    public static final MultiblockAbility<RotationPowerContainer> ROTATION_POWER_CONTAINER_INPUT = new MultiblockAbility<>(
            ContainerType.ROTATION_POWER + "_input");
    public static final MultiblockAbility<RotationPowerContainer> ROTATION_POWER_CONTAINER_OUTPUT = new MultiblockAbility<>(
            ContainerType.ROTATION_POWER + "_output");
}
