package tkcy.tktech.common.item.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;

import org.jetbrains.annotations.NotNull;

import tkcy.tktech.TkTech;

public class TkTechPotion extends Potion {

    public TkTechPotion(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    @Override
    public void performEffect(@NotNull EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (this == CORROSION) {
            if (entityLivingBaseIn.getHealth() > 1.0F) {
                entityLivingBaseIn.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }
        }
        super.performEffect(entityLivingBaseIn, amplifier);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        if (this == CORROSION) {
            int j = 25 >> amplifier;

            if (j > 0) {
                return duration % j == 0;
            } else {
                return true;
            }
        }
        return super.isReady(duration, amplifier);
    }

    public static Potion CORROSION = new TkTechPotion(true, 8171462)
            .setRegistryName(TkTech.MODID, "effect.corrosion")
            .setPotionName("potion.corrosion");
}
