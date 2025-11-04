package tkcy.tktech.common.item.armor;

import static tkcy.tktech.common.item.TkTechArmor.basePath;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import org.jetbrains.annotations.NotNull;

import gregtech.api.items.armor.ISpecialArmorLogic;

import tkcy.tktech.common.item.TkTechMetaItems;

public class ChemicalHazardArmor implements ISpecialArmorLogic {

    public static final String unlocalizedName = "chemical_hazard_suite";
    private final EntityEquipmentSlot slot;

    public ChemicalHazardArmor(EntityEquipmentSlot slot) {
        this.slot = slot;
    }

    @Override
    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, @NotNull ItemStack armor,
                                                       DamageSource source, double damage,
                                                       EntityEquipmentSlot equipmentSlot) {
        return new ISpecialArmor.ArmorProperties(0, 0, 0);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @NotNull ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack itemStack) {
        return slot;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)
                .isItemEqual(TkTechMetaItems.CHEMICAL_HAZARD_SUITE_HEAD.getStackForm())) {
            if (player.isPotionActive(MobEffects.POISON)) {
                player.removeActivePotionEffect(MobEffects.POISON);
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        switch (slot) {
            case CHEST -> {
                return String.format("%s/%s/chest_icon.png", basePath, unlocalizedName);
            }
            case LEGS -> {
                return String.format("%s/%s/legs_icon.png", basePath, unlocalizedName);
            }
            case HEAD -> {
                return String.format("%s/%s/head_icon.png", basePath, unlocalizedName);
            }
            case FEET -> {
                return String.format("%s/%s/feet_icon.png", basePath, unlocalizedName);
            }
        }
        return "";
    }
}
