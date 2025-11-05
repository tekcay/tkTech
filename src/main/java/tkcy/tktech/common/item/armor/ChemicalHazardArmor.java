package tkcy.tktech.common.item.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import tkcy.tktech.api.items.armor.ISimpleArmorLogicHelper;
import tkcy.tktech.common.item.TkTechMetaItems;

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
        if (isEquipped(player, EntityEquipmentSlot.HEAD, TkTechMetaItems.HEAT_HAZARD_SUITE_HEAD)) {
            if (player.isPotionActive(MobEffects.POISON)) {
                player.removeActivePotionEffect(MobEffects.POISON);
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}
