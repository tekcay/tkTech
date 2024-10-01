package tkcy.simpleaddon.api.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import gregtech.api.capability.SimpleCapabilityManager;

public class TKCYSATileCapabilities {

    @CapabilityInject(HeatContainer.class)
    public static Capability<HeatContainer> CAPABILITY_HEAT_CONTAINER = null;

    @CapabilityInject(PressureContainer.class)
    public static Capability<PressureContainer> CAPABILITY_PRESSURE_CONTAINER = null;

    @CapabilityInject(RotationContainer.class)
    public static Capability<RotationContainer> CAPABILITY_ROTATION_CONTAINER = null;

    public static void register() {
        SimpleCapabilityManager.registerCapabilityWithNoDefault(HeatContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(PressureContainer.class);
        SimpleCapabilityManager.registerCapabilityWithNoDefault(RotationContainer.class);
    }
}
