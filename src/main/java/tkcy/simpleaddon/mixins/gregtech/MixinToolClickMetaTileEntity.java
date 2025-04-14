package tkcy.simpleaddon.mixins.gregtech;

import java.util.List;
import java.util.Set;

import gregtech.api.items.toolitem.ToolClasses;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.cover.Cover;
import gregtech.api.cover.CoverRayTracer;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.client.utils.TooltipHelper;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import tkcy.simpleaddon.api.items.toolitem.TKCYSAToolClasses;
import tkcy.simpleaddon.api.machines.IOnAxeClick;
import tkcy.simpleaddon.api.machines.IOnSolderingIronClick;
import tkcy.simpleaddon.api.machines.IRightClickItemTransfer;

@Mixin(value = MetaTileEntity.class, remap = false)
public abstract class MixinToolClickMetaTileEntity implements IOnSolderingIronClick, IOnAxeClick, IRightClickItemTransfer {

    @Shadow
    public abstract boolean onToolClick(EntityPlayer playerIn, @NotNull Set<String> toolClasses, EnumHand hand,
                                        CuboidRayTraceResult hitResult);

    @Shadow
    public abstract Cover getCoverAtSide(EnumFacing gridSideHit);

    @Shadow
    public abstract IItemHandlerModifiable getImportItems();

    @Shadow
    public abstract IItemHandlerModifiable getExportItems();

    @Shadow
    public abstract boolean hasAnyCover();

    @Shadow
    public abstract boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                         CuboidRayTraceResult hitResult);

    @Inject(method = "onToolClick", at = @At(value = "TAIL", ordinal = 0), cancellable = true)
    public void onToolClick(EntityPlayer playerIn, @NotNull Set<String> toolClasses, EnumHand hand,
                            CuboidRayTraceResult hitResult, CallbackInfoReturnable<Boolean> callback) {
        boolean result = false;
        EnumFacing gridSideHit = CoverRayTracer.determineGridSideHit(hitResult);

        if (toolClasses.contains(TKCYSAToolClasses.SOLDERING_IRON)) {
            result = onSolderingIronClick(playerIn, hand, gridSideHit, hitResult);
        }
        if (toolClasses.contains(ToolClasses.AXE)) {
            result = onAxeClick(playerIn, hand, gridSideHit, hitResult);
        }
        callback.setReturnValue(result);
    }

    @Inject(method = "onRightClick", at = @At(value = "HEAD", ordinal = 0), cancellable = true)
    public void onRightClickOverload(@NotNull EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                     CuboidRayTraceResult hitResult, CallbackInfoReturnable<Boolean> callback) {
        if (!playerIn.isSneaking()) {

            ItemStack heldStack = playerIn.getHeldItem(hand);
            boolean didTransferHappened = false;

            if (heldStack.isEmpty()) {

                if (doesTransferOutputToPlayer()) {
                    didTransferHappened = tryTransferOutputToPlayer(playerIn, getExportItems());

                } else if (doesTransferInputToPlayer()) {
                    didTransferHappened = tryTransferInputToPlayer(playerIn, getImportItems());

                }

            } else if (doesTransferHandStackToInput()) {
                didTransferHappened = tryTransferHandStackToInput(playerIn, hand, getImportItems());
            }
            if (didTransferHappened) callback.setReturnValue(true);
        }
    }

    @Inject(method = "addInformation", at = @At(value = "TAIL", ordinal = 0), cancellable = true)
    public void addSpecialRightClickInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                                                boolean advanced, CallbackInfo ci) {
        if (showSpecialRightClickTooltips()) {
            if (TooltipHelper.isCtrlDown()) {
                if (doesTransferHandStackToInput()) transferHandStackToInputTooltip(tooltip);
                if (doesTransferInputToPlayer()) transferInputToPlayerTooltip(tooltip);
                if (doesTransferOutputToPlayer()) transferOutputToPlayerTooltip(tooltip);
            } else {
                tooltip.add(I18n.format("tkcysa.metatilentity.special_right.show_tooltips"));
            }
        }
    }
}
