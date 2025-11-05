package tkcy.tktech.common.item.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import tkcy.tktech.api.items.armor.ISimpleArmorLogicHelper;
import tkcy.tktech.api.utils.BooleanHelper;
import tkcy.tktech.common.item.TkTechMetaItems;

public class HeatHazardArmor implements ISimpleArmorLogicHelper {

    public static final String unlocalizedName = "heat_hazard_suite";
    private final EntityEquipmentSlot slot;

    public HeatHazardArmor(EntityEquipmentSlot slot) {
        this.slot = slot;
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack itemStack) {
        return slot;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        boolean isFullyEquipped = BooleanHelper.and(
                isEquipped(player, EntityEquipmentSlot.CHEST, TkTechMetaItems.HEAT_HAZARD_SUITE_CHEST),
                isEquipped(player, EntityEquipmentSlot.HEAD, TkTechMetaItems.HEAT_HAZARD_SUITE_HEAD),
                isEquipped(player, EntityEquipmentSlot.LEGS, TkTechMetaItems.HEAT_HAZARD_SUITE_LEGS),
                isEquipped(player, EntityEquipmentSlot.FEET, TkTechMetaItems.HEAT_HAZARD_SUITE_FEET));

        if (isFullyEquipped) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
        }
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}
