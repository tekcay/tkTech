package tkcy.tktech.common.item;

import net.minecraft.inventory.EntityEquipmentSlot;

import gregtech.api.items.armor.ArmorMetaItem;

import tkcy.tktech.common.item.armor.ChemicalHazardArmor;
import tkcy.tktech.common.item.armor.HeatHazardArmor;

public class TkTechArmor extends ArmorMetaItem<ArmorMetaItem<?>.ArmorMetaValueItem> {

    public static final String texturePath = "tktech:textures/items/armors";
    public static final String registryName = "tk_armor";

    @Override
    public void registerSubItems() {
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_CHEST = addItem(1, ChemicalHazardArmor.unlocalizedName + ".chest_icon")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.CHEST));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_HEAD = addItem(2, ChemicalHazardArmor.unlocalizedName + ".head_icon")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.HEAD));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_FEET = addItem(3, ChemicalHazardArmor.unlocalizedName + ".feet_icon")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.FEET));
        TkTechMetaItems.CHEMICAL_HAZARD_SUITE_LEGS = addItem(4, ChemicalHazardArmor.unlocalizedName + ".legs_icon")
                .setArmorLogic(new ChemicalHazardArmor(EntityEquipmentSlot.LEGS));

        TkTechMetaItems.HEAT_HAZARD_SUITE_CHEST = addItem(5, HeatHazardArmor.unlocalizedName + ".chest_icon")
                .setArmorLogic(new HeatHazardArmor(EntityEquipmentSlot.CHEST));
        TkTechMetaItems.HEAT_HAZARD_SUITE_HEAD = addItem(6, HeatHazardArmor.unlocalizedName + ".head_icon")
                .setArmorLogic(new HeatHazardArmor(EntityEquipmentSlot.HEAD));
        TkTechMetaItems.HEAT_HAZARD_SUITE_FEET = addItem(7, HeatHazardArmor.unlocalizedName + ".feet_icon")
                .setArmorLogic(new HeatHazardArmor(EntityEquipmentSlot.FEET));
        TkTechMetaItems.HEAT_HAZARD_SUITE_LEGS = addItem(8, HeatHazardArmor.unlocalizedName + ".legs_icon")
                .setArmorLogic(new HeatHazardArmor(EntityEquipmentSlot.LEGS));
    }
}
