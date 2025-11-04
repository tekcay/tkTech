package tkcy.tktech.common.item;

import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.items.metaitem.MetaItem;

import lombok.experimental.UtilityClass;
import tkcy.tktech.TkTech;

@UtilityClass
public final class TkTechMetaItems {

    public static MetaItem<?>.MetaValueItem MICA_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_SHEET;
    public static MetaItem<?>.MetaValueItem MICA_INSULATOR_FOIL;

    // ULV Components
    public static MetaItem<?>.MetaValueItem ELECTRIC_MOTOR_ULV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PUMP_ULV;
    public static MetaItem<?>.MetaValueItem ELECTRIC_PISTON_ULV;
    public static MetaItem<?>.MetaValueItem CONVEYOR_MODULE_ULV;
    public static MetaItem<?>.MetaValueItem ROBOT_ARM_ULV;

    // Armors
    public static ArmorMetaItem<?>.ArmorMetaValueItem CHEMICAL_HAZARD_SUITE_HEAD;
    public static ArmorMetaItem<?>.ArmorMetaValueItem CHEMICAL_HAZARD_SUITE_LEGS;
    public static ArmorMetaItem<?>.ArmorMetaValueItem CHEMICAL_HAZARD_SUITE_CHEST;
    public static ArmorMetaItem<?>.ArmorMetaValueItem CHEMICAL_HAZARD_SUITE_FEET;

    public static void init() {
        TkTechMetaItemRegistry metaItem1 = new TkTechMetaItemRegistry((short) 0);
        metaItem1.setRegistryName(TkTech.MODID);

        TkTechArmor tkTechArmors = new TkTechArmor();
        tkTechArmors.setRegistryName("tk_armor");
    }
}
