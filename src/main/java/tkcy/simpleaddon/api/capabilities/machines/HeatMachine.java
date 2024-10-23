package tkcy.simpleaddon.api.capabilities.machines;

import org.jetbrains.annotations.Nullable;
import tkcy.simpleaddon.api.capabilities.HeatContainer;

public interface HeatMachine {

    @Nullable
    HeatContainer getHeatContainer();
}
