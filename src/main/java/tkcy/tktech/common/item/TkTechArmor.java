package tkcy.tktech.common.item;

import net.minecraft.inventory.EntityEquipmentSlot;

import gregtech.api.items.armor.ArmorMetaItem;

import tkcy.tktech.common.item.armor.ChemicalHazardArmor;

public class TkTechArmor extends ArmorMetaItem<ArmorMetaItem<?>.ArmorMetaValueItem> {

    public static final String basePath = "tktech:textures/items/armors";

    @Override
    public void registerSubItems() {
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_CHEST = addItem(1, ChemicalHazardArmor.unlocalizedName + ".chest")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.CHEST));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_HEAD = addItem(2, ChemicalHazardArmor.unlocalizedName + ".head")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.HEAD));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_FEET = addItem(3, ChemicalHazardArmor.unlocalizedName + ".feet")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.FEET));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_LEGS = addItem(4, ChemicalHazardArmor.unlocalizedName + ".legs")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.LEGS));
    }
}
