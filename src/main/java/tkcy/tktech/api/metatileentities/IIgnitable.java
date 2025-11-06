package tkcy.tktech.api.metatileentities;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.MetaItems;

public interface IIgnitable {

    String NBT_LABEL = "isIgnited";
    List<MetaItem<?>.MetaValueItem> IGNITER_ITEMS = new ArrayList<>() {

        {
            IGNITER_ITEMS.add(MetaItems.TOOL_LIGHTER_INVAR);
            IGNITER_ITEMS.add(MetaItems.TOOL_LIGHTER_PLATINUM);
        }
    };

    boolean isIgnited();

    void ignite();

    void shutOff();
}
