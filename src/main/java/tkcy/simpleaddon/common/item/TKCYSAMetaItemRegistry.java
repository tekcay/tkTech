package tkcy.simpleaddon.common.item;

import gregtech.api.items.metaitem.StandardMetaItem;

public class TKCYSAMetaItemRegistry extends StandardMetaItem {

    public TKCYSAMetaItemRegistry(short i) {
        super(i);
    }

    @Override
    public void registerSubItems() {
        TKCYSAMetaItems.MICA_SHEET = addItem(1, "mica.sheet");
        TKCYSAMetaItems.MICA_INSULATOR_SHEET = addItem(2, "mica_insulator.sheet");
        TKCYSAMetaItems.MICA_INSULATOR_FOIL = addItem(3, "mica_insulator.foil");
    }
}
