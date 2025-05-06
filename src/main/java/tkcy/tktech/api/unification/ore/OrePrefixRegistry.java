package tkcy.tktech.api.unification.ore;

import static gregtech.common.items.MetaItems.addOrePrefix;
import static tkcy.tktech.api.unification.ore.TkTechOrePrefix.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrePrefixRegistry {

    public static void register() {
        addOrePrefix(TkTechOrePrefix.electrode);
        addOrePrefix(TkTechOrePrefix.anode);
        addOrePrefix(TkTechOrePrefix.cathode);
        addOrePrefix(curvedPlate);
        addOrePrefix(strippedWood);

        registerComponentsOres();
        registerToolOres();
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

    private static void registerToolOres() {
        addOrePrefix(toolTipSolderingIron);
    }
}
