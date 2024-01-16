package tkcy.simpleaddon.api.unification.ore;

import static gregtech.common.items.MetaItems.addOrePrefix;

public class OrePrefixRegitry {

    public static void register() {
        addOrePrefix(TKCYSAOrePrefix.anode);
        addOrePrefix(TKCYSAOrePrefix.cathode);
    }
}
