package tkcy.tktech.api.items.armor;

import static tkcy.tktech.common.item.TkTechArmor.texturePath;

import java.util.List;

import net.minecraft.client.resources.I18n;
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

import tkcy.tktech.common.item.TkTechArmor;

public interface ISimpleArmorLogicHelper extends ISpecialArmorLogic {

    String getUnlocalizedName();

    @Override
    default void addToolComponents(ArmorMetaItem.ArmorMetaValueItem metaValueItem) {
        metaValueItem.addComponents(new BasicArmorItemBehavior(this::addInfo));
    }

    default void addInfo(ItemStack itemStack, List<String> lines) {
        EntityEquipmentSlot slot = getEquipmentSlot(itemStack);
        String str = String.format("%s.%s.%s.tooltip", TkTechArmor.registryName, getUnlocalizedName(), slot.getName());
        lines.add(I18n.format(str));
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
                return String.format("%s/%s/chest.png", texturePath, getUnlocalizedName());
            }
            case LEGS -> {
                return String.format("%s/%s/legs.png", texturePath, getUnlocalizedName());
            }
            case HEAD -> {
                return String.format("%s/%s/head.png", texturePath, getUnlocalizedName());
            }
            case FEET -> {
                return String.format("%s/%s/feet.png", texturePath, getUnlocalizedName());
            }
        }
        return "";
    }
}
