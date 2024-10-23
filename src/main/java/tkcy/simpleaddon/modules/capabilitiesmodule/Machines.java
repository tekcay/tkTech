package tkcy.simpleaddon.modules.capabilitiesmodule;

import org.jetbrains.annotations.Nullable;

import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.PressureContainer;
import tkcy.simpleaddon.api.capabilities.RotationContainer;

public class Machines {

    public interface HeatMachine {

        @Nullable
        HeatContainer getHeatContainer();
    }

    public interface PressureMachine {

        @Nullable
        PressureContainer getPressureContainer();
    }

    public interface RotationMachine {

        @Nullable
        RotationContainer getRotationContainer();
    }
}
