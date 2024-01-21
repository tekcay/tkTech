package tkcy.simpleaddon.common.item;

import gregtech.api.items.metaitem.MetaItem;

public final class TKCYSAMetaItems {

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;

    public static void init() {
        TKCYSAMetaItemRegistry metaItem1 = new TKCYSAMetaItemRegistry((short) 0);
        metaItem1.setRegistryName("tkcysa");
    }
}
