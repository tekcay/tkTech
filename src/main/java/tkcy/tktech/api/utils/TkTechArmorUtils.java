package tkcy.tktech.api.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

import gregtech.api.items.armor.ArmorMetaItem;

import tkcy.tktech.common.item.TkTechMetaItems;

public class TkTechArmorUtils {

    public static boolean isFullyHeatHazardEquipped(EntityPlayer player) {
        return BooleanHelper.and(
                isEquipped(player, EntityEquipmentSlot.CHEST, TkTechMetaItems.HEAT_HAZARD_SUITE_CHEST),
                isEquipped(player, EntityEquipmentSlot.HEAD, TkTechMetaItems.HEAT_HAZARD_SUITE_HEAD),
                isEquipped(player, EntityEquipmentSlot.LEGS, TkTechMetaItems.HEAT_HAZARD_SUITE_LEGS),
                isEquipped(player, EntityEquipmentSlot.FEET, TkTechMetaItems.HEAT_HAZARD_SUITE_FEET));
    }

    public static boolean isFullyChemicalHazardEquipped(EntityPlayer player) {
        return BooleanHelper.and(
                isEquipped(player, EntityEquipmentSlot.CHEST, TkTechMetaItems.CHEMICAL_HAZARD_SUITE_CHEST),
                isEquipped(player, EntityEquipmentSlot.HEAD, TkTechMetaItems.CHEMICAL_HAZARD_SUITE_HEAD),
                isEquipped(player, EntityEquipmentSlot.LEGS, TkTechMetaItems.CHEMICAL_HAZARD_SUITE_LEGS),
                isEquipped(player, EntityEquipmentSlot.FEET, TkTechMetaItems.CHEMICAL_HAZARD_SUITE_FEET));
    }

    public static boolean isEquipped(EntityPlayer player, EntityEquipmentSlot slot,
                                     ArmorMetaItem<?>.ArmorMetaValueItem armorMetaValueItem) {
        return (player.getItemStackFromSlot(slot).isItemEqual(armorMetaValueItem.getStackForm()));
    }
}
