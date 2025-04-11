package tkcy.simpleaddon.common.metatileentities.electric;

import java.util.*;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import gregtech.api.GTValues;
import gregtech.api.block.ICleanroomFilter;
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
import gregtech.api.util.TextComponentUtil;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityCleanroom;

import tkcy.simpleaddon.api.capabilities.TKCYSADataCodes;
import tkcy.simpleaddon.api.logic.AdvancedCleanroomLogic;
import tkcy.simpleaddon.api.metatileentities.cleanroom.AdvancedCleanroomType;
import tkcy.simpleaddon.api.metatileentities.cleanroom.IAdvancedCleanroomProvider;

public class MetaTileEntityAdvancedCleanroom extends MetaTileEntityCleanroom
                                             implements IAdvancedCleanroomProvider, IWorkable, IDataInfoProvider {

    private int cleanAmount;

    private IEnergyContainer energyContainer;
    private IMultipleTankHandler inputFluidInventory;

    private ICleanroomFilter cleanroomFilter;
    private final AdvancedCleanroomLogic cleanroomLogic;
    private final Collection<ICleanroomReceiver> cleanroomReceivers = new HashSet<>();
    private int cleanroomTypeIndex;

    public MetaTileEntityAdvancedCleanroom(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.cleanroomLogic = new AdvancedCleanroomLogic(this);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAdvancedCleanroom(metaTileEntityId);
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
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

    @NotNull
    protected TraceabilityPredicate filterPredicate() {
        return states(getCasingState());
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(cleanroomLogic.isWorkingEnabled(), cleanroomLogic.isActive())
                .addEnergyUsageLine(energyContainer)
                .addCustom(tl -> {
                    // Cleanliness status line
                    if (isStructureFormed()) {
                        ITextComponent cleanState;
                        if (isClean()) {
                            cleanState = TextComponentUtil.translationWithColor(
                                    TextFormatting.GREEN,
                                    "gregtech.multiblock.cleanroom.clean_state",
                                    this.cleanAmount);
                        } else {
                            cleanState = TextComponentUtil.translationWithColor(
                                    TextFormatting.DARK_RED,
                                    "gregtech.multiblock.cleanroom.dirty_state",
                                    this.cleanAmount);
                        }

                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.GRAY,
                                "gregtech.multiblock.cleanroom.clean_status",
                                cleanState));
                    }
                })
                .addCustom(tl -> {
                    if (!cleanroomLogic.isVoltageHighEnough()) {
                        ITextComponent energyNeeded = new TextComponentString(
                                GTValues.VNF[cleanroomFilter.getMinTier()]);
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.YELLOW,
                                "gregtech.multiblock.cleanroom.low_tier", energyNeeded));
                    }
                })
                .addEnergyUsageExactLine(isClean() ? 4 : GTValues.VA[getEnergyTier()])
                .addWorkingStatusLine()
                .addProgressLine(getProgressPercent() / 100.0);
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed(), false)
                .addLowPowerLine(!drainEnergy(true))
                .addCustom(tl -> {
                    if (isStructureFormed() && !isClean()) {
                        tl.add(TextComponentUtil.translationWithColor(
                                TextFormatting.YELLOW,
                                "gregtech.multiblock.cleanroom.warning_contaminated"));
                    }
                })
                .addMaintenanceProblemLines(getMaintenanceProblems());
    }

    @Override
    protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
        if (getAvailableCleanroomTypes() != null && getAvailableCleanroomTypes().length > 1) {
            return new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_MULTI_MAP,
                    getAvailableCleanroomTypes().length, this::getCleanroomTypeIndex, this::setCleanroomTypeIndex)
                            .shouldUseBaseBackground().singleTexture()
                            .setTooltipHoverString(i -> LocalizationUtils
                                    .format("tkcysa.multiblock.advanced_cleanroom.gas.header",
                                            getCleanroomType().getIntertGasMaterial().getLocalizedName()));
        }
        return super.getFlexButton(x, y, width, height);
    }

    @Override
    public boolean isClean() {
        return this.cleanAmount >= CLEAN_AMOUNT_THRESHOLD;
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == TKCYSADataCodes.CLEANROOM_TYPE_INDEX) {
            cleanroomTypeIndex = buf.readByte();
            scheduleRenderUpdate();
        }
    }

    @Override
    public long energyToDrainWhenClean() {
        return getRoomVolume() / 2;
    }

    @Override
    public long energyToDrain() {
        return getRoomVolume() * 2L;
    }

    @Override
    public int gasAmountToDrainWhenClean() {
        return getRoomVolume();
    }

    @Override
    public int gasAmountToDrain() {
        return getRoomVolume() * 2;
    }

    @Override
    public int getRoomVolume() {
        return getMaxProgress();
    }

    @Override
    public AdvancedCleanroomType[] getAvailableCleanroomTypes() {
        return new AdvancedCleanroomType[] {
                AdvancedCleanroomType.NITROGEN_CLEANROOM,
                AdvancedCleanroomType.ARGON_CLEANROOM
        };
    }

    @Override
    public int getCleanroomTypeIndex() {
        return cleanroomTypeIndex;
    }

    @Override
    public void setCleanroomTypeIndex(int index) {
        this.cleanroomTypeIndex = index;
        if (!getWorld().isRemote) {
            writeCustomData(TKCYSADataCodes.CLEANROOM_TYPE_INDEX, buf -> buf.writeByte(index));
            markDirty();
        }
    }

    @Override
    public boolean drainGas(boolean simulate) {
        int gasToDrain = isClean() ? gasAmountToDrainWhenClean() : gasAmountToDrain();
        FluidStack drainedStack = fluidInventory.drain(gasToDrain, false);
        return drainedStack != null && drainedStack.amount == gasToDrain;
    }

    @Override
    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = isClean() ? energyToDrainWhenClean() : energyToDrain();
        long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
        if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
            if (!simulate)
                energyContainer.changeEnergy(-energyToDrain);
            return true;
        }
        return false;
    }
}
