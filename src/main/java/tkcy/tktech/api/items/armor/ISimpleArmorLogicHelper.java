package tkcy.tktech.api.items.armor;

import static tkcy.tktech.common.item.TkTechArmor.basePath;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.items.armor.ISpecialArmorLogic;

public interface ISimpleArmorLogicHelper extends ISpecialArmorLogic {

    String getUnlocalizedName();

    default boolean isEquipped(EntityPlayer player, EntityEquipmentSlot slot,
                               ArmorMetaItem<?>.ArmorMetaValueItem armorMetaValueItem) {
        return (player.getItemStackFromSlot(slot).isItemEqual(armorMetaValueItem.getStackForm()));
    }

    @Override
    default ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, @NotNull ItemStack armor,
                                                        DamageSource source, double damage,
                                                        EntityEquipmentSlot equipmentSlot) {
        return new ISpecialArmor.ArmorProperties(0, 0, 0);
    }

    @Override
    default int getArmorDisplay(EntityPlayer player, @NotNull ItemStack armor, int slot) {
        return 0;
    }

    @Override
    default String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        switch (slot) {
            case CHEST -> {
                return String.format("%s/%s/chest.png", basePath, getUnlocalizedName());
            }
            case LEGS -> {
                return String.format("%s/%s/legs.png", basePath, getUnlocalizedName());
            }
            case HEAD -> {
                return String.format("%s/%s/head.png", basePath, getUnlocalizedName());
            }
            case FEET -> {
                return String.format("%s/%s/feet.png", basePath, getUnlocalizedName());
            }
        }
        return "";
    }
}
