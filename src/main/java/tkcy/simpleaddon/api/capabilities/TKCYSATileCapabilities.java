package tkcy.simpleaddon.api.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import gregtech.api.capability.SimpleCapabilityManager;

import tkcy.simpleaddon.modules.capabilitiesmodule.CapabilityModule;

@CapabilityModule.Capabilities
public class TKCYSATileCapabilities {

    @CapabilityInject(HeatContainer.class)
    public static Capability<HeatContainer> CAPABILITY_HEAT_CONTAINER = null;
    @CapabilityInject(TemperatureContainer.class)
    public static Capability<TemperatureContainer> CAPABILITY_TEMPERATURE_CONTAINER = null;
    @CapabilityInject(TorqueContainer.class)
    public static Capability<TorqueContainer> CAPABILITY_TORQUE_CONTAINER = null;
    @CapabilityInject(RotationPowerContainer.class)
    public static Capability<RotationPowerContainer> CAPABILITY_ROTATION_POWER_CONTAINER = null;

    @CapabilityInject(PressureContainer.class)
    public static Capability<PressureContainer> CAPABILITY_PRESSURE_CONTAINER = null;

    @CapabilityInject(RotationContainer.class)
    public static Capability<RotationContainer> CAPABILITY_ROTATION_CONTAINER = null;

    public static void register() {
        SimpleCapabilityManager.registerCapabilityWithNoDefault(HeatContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(PressureContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(RotationContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(TemperatureContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(RotationPowerContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(TorqueContainer.class);
    }
}
