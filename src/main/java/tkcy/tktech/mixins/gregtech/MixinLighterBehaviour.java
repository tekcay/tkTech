package tkcy.tktech.mixins.gregtech;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.items.behaviors.LighterBehaviour;

import tkcy.tktech.api.metatileentities.IIgnitable;
import tkcy.tktech.api.utils.MetaTileEntityUtils;

@Mixin(value = LighterBehaviour.class, remap = false)
public abstract class MixinLighterBehaviour {

    /**
     * Adds to {@link LighterBehaviour} the ability to {@link IIgnitable#ignite()} a {@link MetaTileEntity} implementing
     * {@link IIgnitable}.
     */
    @Inject(method = "onItemUseFirst",
            at = @At(
                     value = "INVOKE",
                     target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"),
            cancellable = true)
    private void handleIIgnitable(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
                                  float hitY, float hitZ, EnumHand hand, CallbackInfoReturnable<EnumActionResult> cir) {
        MetaTileEntity metaTileEntity = MetaTileEntityUtils.getMetaTileEntity(world, pos);

        if (metaTileEntity instanceof IIgnitable iIgnitable) {
            iIgnitable.ignite();
            cir.setReturnValue(EnumActionResult.SUCCESS);
        }
    }
}
