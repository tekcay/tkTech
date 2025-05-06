package tkcy.tktech.mixins.gregtech;

import java.util.List;
import java.util.Set;

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

import gregtech.api.cover.CoverRayTracer;
import gregtech.api.items.toolitem.ToolClasses;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.client.utils.TooltipHelper;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import tkcy.tktech.api.items.toolitem.TkTechToolClasses;
import tkcy.tktech.api.machines.*;
import tkcy.tktech.modules.toolmodule.ToolsModule;

@Mixin(value = MetaTileEntity.class, remap = false)
public abstract class MixinToolClickMetaTileEntity implements IOnSolderingIronClick, IOnAxeClick, IOnSawClick,
                                                   IOnAnyToolClick,
                                                   IRightClickItemTransfer {

    @Shadow
    public abstract IItemHandlerModifiable getImportItems();

    @Shadow
    public abstract IItemHandlerModifiable getExportItems();

    @Inject(method = "onToolClick", at = @At(value = "HEAD", ordinal = 0), cancellable = true)
    public void extraToolClick(EntityPlayer playerIn, @NotNull Set<String> toolClasses, EnumHand hand,
                               CuboidRayTraceResult hitResult, CallbackInfoReturnable<Boolean> callback) {
        boolean result = false;
        EnumFacing gridSideHit = CoverRayTracer.determineGridSideHit(hitResult);

        ToolsModule.GtTool tool = ToolsModule.getGtTool(toolClasses);
        if (tool != null) onAnyToolClick(tool, playerIn.isSneaking());

        if (toolClasses.contains(TkTechToolClasses.SOLDERING_IRON)) {
            result = onSolderingIronClick(playerIn, hand, gridSideHit, hitResult);
            callback.setReturnValue(result);
        }
        if (toolClasses.contains(ToolClasses.AXE)) {
            result = onAxeClick(playerIn, hand, gridSideHit, hitResult);
            callback.setReturnValue(result);
        }
        if (toolClasses.contains(ToolClasses.SAW)) {
            result = onSawClick(playerIn, hand, gridSideHit, hitResult);
            callback.setReturnValue(result);
        }
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

    @Inject(method = "addInformation", at = @At(value = "TAIL"))
    public void addSpecialRightClickInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                                                boolean advanced, CallbackInfo ci) {
        if (showAnyToolClickTooltip()) onAnyToolClickTooltip(tooltip);
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
