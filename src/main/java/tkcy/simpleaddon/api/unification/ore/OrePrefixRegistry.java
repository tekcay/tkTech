package tkcy.simpleaddon.api.unification.ore;

import static gregtech.common.items.MetaItems.addOrePrefix;
import static tkcy.simpleaddon.api.unification.ore.TKCYSAOrePrefix.*;

public class OrePrefixRegistry {

    public static void register() {
        addOrePrefix(TKCYSAOrePrefix.electrode);
        addOrePrefix(TKCYSAOrePrefix.anode);
        addOrePrefix(TKCYSAOrePrefix.cathode);

        registerComponentsOres();
    }

    private static void registerComponentsOres() {
        addOrePrefix(lvComponents);
        addOrePrefix(mvComponents);
        addOrePrefix(hvComponents);
        addOrePrefix(evComponents);
        addOrePrefix(ivComponents);
        addOrePrefix(luvComponents);
        addOrePrefix(zpmComponents);
        addOrePrefix(uvComponents);
    }
}
