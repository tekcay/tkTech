package tkcy.tktech.common.item.armor;

import static tkcy.tktech.api.utils.TkTechArmorUtils.isEquipped;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import tkcy.tktech.api.items.armor.ISimpleArmorLogicHelper;
import tkcy.tktech.api.utils.TkTechArmorUtils;
import tkcy.tktech.common.item.TkTechArmor;
import tkcy.tktech.common.item.TkTechMetaItems;
import tkcy.tktech.common.item.potions.TkTechPotion;

public class ChemicalHazardArmor implements ISimpleArmorLogicHelper {

    public static final String unlocalizedName = "chemical_hazard_suite";
    private final EntityEquipmentSlot slot;

    public ChemicalHazardArmor(EntityEquipmentSlot slot) {
        this.slot = slot;
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack itemStack) {
        return slot;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (isEquipped(player, EntityEquipmentSlot.HEAD, TkTechMetaItems.CHEMICAL_HAZARD_SUITE_CHEST)) {
            if (player.isPotionActive(MobEffects.POISON)) {
                player.removeActivePotionEffect(MobEffects.POISON);
            }
        }

        if (TkTechArmorUtils.isFullyChemicalHazardEquipped(player)) {
            if (player.isPotionActive(TkTechPotion.CORROSION)) {
                player.removeActivePotionEffect(TkTechPotion.CORROSION);
            }
            if (player.isPotionActive(MobEffects.POISON)) {
                player.removeActivePotionEffect(MobEffects.POISON);
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public void addInfo(ItemStack itemStack, List<String> lines) {
        EntityEquipmentSlot slot = getEquipmentSlot(itemStack);
        if (slot.equals(EntityEquipmentSlot.HEAD)) {
            String str = String.format("%s.%s.%s.tooltip.toxic", TkTechArmor.registryName, getUnlocalizedName(),
                    slot.getName());
            lines.add(I18n.format(str));
        }
        ISimpleArmorLogicHelper.super.addInfo(itemStack, lines);
    }
}
