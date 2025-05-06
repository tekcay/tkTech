package tkcy.tktech.common.metatileentities.electric;

import java.util.*;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.*;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.*;
import gregtech.api.util.LocalizationUtils;
import gregtech.api.util.Mods;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.utils.TooltipHelper;

import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import tkcy.tktech.api.logic.AdvancedCleanroomLogic;
import tkcy.tktech.api.metatileentities.cleanroom.AdvancedCleanroomType;
import tkcy.tktech.api.metatileentities.cleanroom.IAdvancedCleanroomProvider;
import tkcy.tktech.api.metatileentities.cleanroom.MetaTileEntityCleanroomBase;
import tkcy.tktech.modules.TkTechDataCodes;

public class MetaTileEntityAdvancedCleanroom extends MetaTileEntityCleanroomBase
                                             implements IAdvancedCleanroomProvider, IWorkable, IDataInfoProvider {

    protected IMultipleTankHandler inputFluidInventory;
    protected int cleanroomTypeIndex;
    protected boolean hasEnoughGas = false;

    public MetaTileEntityAdvancedCleanroom(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        cleanroomLogic = new AdvancedCleanroomLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedCleanroom(metaTileEntityId);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.cleanroomType = getCleanroomType();
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
    }

    @Override
    protected void resetTileAbilities() {
        super.resetTileAbilities();
        this.inputFluidInventory = new FluidTankList(true);
    }

    @Override
    protected boolean doesHandleFilter() {
        return false;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.inputFluidInventory = new FluidTankList(true);
    }

    @Override
    public TraceabilityPredicate autoAbilities() {
        return super.autoAbilities().or(abilities(MultiblockAbility.IMPORT_FLUIDS)).setMaxGlobalLimited(2);
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed(), false)
                .addLowPowerLine(!hasEnoughEnergy())
                .addCustom(tl -> {
                    if (isStructureFormed() && !isClean()) {
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.RED,
                                "gregtech.multiblock.cleanroom.warning_contaminated"));
                    }
                })
                .addMaintenanceProblemLines(getMaintenanceProblems())
                .addCustom(l -> {
                    if (isStructureFormed() && !hasEnoughGas()) {
                        l.add(TextComponentUtil.translationWithColor(
                                getCleanroomType().getDisplayColor(),
                                "tktech.multiblock.advanced_cleanroom.not_enough_gas",
                                getCleanroomType().getIntertGasMaterial().getLocalizedName()));
                    }
                });
    }

    @Override
    protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
        return new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_MULTI_MAP,
                AdvancedCleanroomType.ADVANCED_CLEANROOM_TYPES.size(),
                this::getCleanroomTypeIndex,
                this::setCleanroomTypeIndex)
                        .shouldUseBaseBackground().singleTexture()
                        .setTooltipHoverString(i -> LocalizationUtils
                                .format("tktech.multiblock.advanced_cleanroom.gas.header",
                                        getCleanroomType().getIntertGasMaterial().getLocalizedName(),
                                        gasAmountToDrain()));
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == TkTechDataCodes.CLEANROOM_TYPE_INDEX) {
            this.cleanroomTypeIndex = buf.readByte();
            cleanroomType = getCleanroomType();
            scheduleRenderUpdate();
        }
    }

    @Override
    public long energyToDrainWhenClean() {
        return getRoomVolume() / 2;
    }

    @Override
    public long energyToDrain() {
        return isClean() ? energyToDrainWhenClean() : getRoomVolume() * 2L;
    }

    @Override
    public int gasAmountToDrainWhenClean() {
        return getRoomVolume();
    }

    @Override
    public int gasAmountToDrain() {
        return isClean() ? gasAmountToDrainWhenClean() : getRoomVolume() * 2;
    }

    @Override
    public int getRoomVolume() {
        return getMaxProgress();
    }

    @Override
    public int getCleanroomTypeIndex() {
        return cleanroomTypeIndex;
    }

    @Override
    public void setCleanroomTypeIndex(int index) {
        this.cleanroomTypeIndex = index;
        if (!getWorld().isRemote) {
            this.cleanroomType = getCleanroomType();
            setCleanAmount(0);
            writeCustomData(TkTechDataCodes.CLEANROOM_TYPE_INDEX, buf -> buf.writeByte(index));
            markDirty();
        }
    }

    @Override
    public void addGasConsumptionInfos(List<ITextComponent> textComponents) {
        ITextComponent gasMaterial = TextComponentUtil.stringWithColor(
                getCleanroomType().getDisplayColor(),
                getCleanroomType().getIntertGasMaterial().getLocalizedName());

        if (cleanroomLogic.isWorking()) {
            textComponents.add(TextComponentUtil.translationWithColor(
                    TextFormatting.YELLOW,
                    "tktech.multiblock.advanced_cleanroom.gas_consumption_infos",
                    TextComponentUtil.stringWithColor(TextFormatting.WHITE, gasAmountToDrain() + "L/t"),
                    gasMaterial));
        } else {
            textComponents.add(TextComponentUtil.translationWithColor(
                    TextFormatting.DARK_RED,
                    "tktech.multiblock.advanced_cleanroom.gas_require_infos",
                    TextComponentUtil.stringWithColor(TextFormatting.RED, gasAmountToDrain() + "L/t"),
                    gasMaterial));

        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tktech.machine.cleanroom.tooltip"));
        tooltip.add(I18n.format("tktech.machine.cleanroom.tooltip.1"));
        tooltip.add(I18n.format("tktech.machine.cleanroom.tooltip.2"));
        tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.3"));
        tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.4"));

        if (TooltipHelper.isCtrlDown()) {
            tooltip.add("");
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.5"));
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.6"));
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.7"));
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.8"));
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.9"));
            if (Mods.AppliedEnergistics2.isModLoaded()) {
                tooltip.add(I18n.format(AEConfig.instance().isFeatureEnabled(AEFeature.CHANNELS) ?
                        "gregtech.machine.cleanroom.tooltip.ae2.channels" :
                        "gregtech.machine.cleanroom.tooltip.ae2.no_channels"));
            }
            tooltip.add("");
        } else {
            tooltip.add(I18n.format("gregtech.machine.cleanroom.tooltip.hold_ctrl"));
        }
    }

    @Override
    public boolean hasEnoughGas() {
        return hasEnoughGas;
    }

    @Override
    public boolean drainGas(boolean simulate) {
        int gasToDrain = isClean() ? gasAmountToDrainWhenClean() : gasAmountToDrain();
        FluidStack fluidStackToDrain = getCleanroomType().getIntertGasMaterial().getFluid(gasToDrain);
        FluidStack drainedStack = inputFluidInventory.drain(fluidStackToDrain, false);
        hasEnoughGas = drainedStack != null && drainedStack.amount == gasToDrain;
        return hasEnoughGas;
    }
}
