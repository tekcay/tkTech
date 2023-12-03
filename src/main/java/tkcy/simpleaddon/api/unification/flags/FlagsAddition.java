package tkcy.simpleaddon.api.unification.flags;

import static gregtech.api.unification.material.Materials.EXT2_METAL;
import static gregtech.api.unification.material.info.MaterialFlags.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gregtech.api.unification.material.info.MaterialFlag;

public class FlagsAddition {

    public static final List<MaterialFlag> GENERATE_ALL = new ArrayList<>();
    public static final List<MaterialFlag> GENERATE_ALL_NO_UNIF = new ArrayList<>();

    static {

        GENERATE_ALL.addAll(EXT2_METAL);
        GENERATE_ALL.addAll(Arrays.asList(GENERATE_ROTOR, GENERATE_SMALL_GEAR, GENERATE_SPRING,
                GENERATE_SPRING_SMALL, GENERATE_FRAME, GENERATE_GEAR, GENERATE_DOUBLE_PLATE, GENERATE_DENSE));

        GENERATE_ALL_NO_UNIF.addAll(GENERATE_ALL);
        GENERATE_ALL_NO_UNIF.addAll(Arrays.asList(NO_UNIFICATION, NO_SMELTING, NO_SMASHING, NO_WORKING, NO_SMASHING));
    }
}
