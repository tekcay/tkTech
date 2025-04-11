package tkcy.simpleaddon.common.metatileentities.electric;

import java.util.*;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.util.BlockInfo;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockCleanroomCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
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
import tkcy.simpleaddon.api.metatileentities.cleanroom.MetaTileEntityCleanroomBase;

public class MetaTileEntityAdvancedCleanroom extends MetaTileEntityCleanroomBase
                                             implements IAdvancedCleanroomProvider, IWorkable, IDataInfoProvider {

    private int cleanAmount;
    private IEnergyContainer energyContainer;
    private IMultipleTankHandler inputFluidInventory;
    private final AdvancedCleanroomLogic cleanroomLogic;
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
    @NotNull
    protected TraceabilityPredicate filterPredicate() {
        return states(getCasingState());
    }

    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
    }

    @Override
    protected @NotNull Widget getFlexButton(int x, int y, int width, int height) {
            return new ImageCycleButtonWidget(x, y, width, height, GuiTextures.BUTTON_MULTI_MAP,
                    AdvancedCleanroomType.ADVANCED_CLEANROOM_TYPES.size(),
                    this::getCleanroomTypeIndex,
                    this::setCleanroomTypeIndex)
                            .shouldUseBaseBackground().singleTexture()
                            .setTooltipHoverString(i -> LocalizationUtils
                                    .format("tkcysa.multiblock.advanced_cleanroom.gas.header",
                                            getCleanroomType().getIntertGasMaterial().getLocalizedName(),
                                            gasAmountToDrain()));
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
            writeCustomData(TKCYSADataCodes.CLEANROOM_TYPE_INDEX, buf -> buf.writeByte(index));
            markDirty();
        }
    }

    @Override
    public void addGasConsumptionInfos(List<ITextComponent> textComponents) {
        textComponents.add(TextComponentUtil.translationWithColor(
                TextFormatting.YELLOW,
                "tkcysa.multiblock.advanced_cleanroom.gas_consumption_infos",
                gasAmountToDrain(),
                getCleanroomType().getIntertGasMaterial().getLocalizedName()));
    }

    @Override
    public boolean drainGas(boolean simulate) {
        int gasToDrain = isClean() ? gasAmountToDrainWhenClean() : gasAmountToDrain();
        FluidStack fluidStackToDrain = getCleanroomType().getIntertGasMaterial().getFluid(gasToDrain);
        FluidStack drainedStack = fluidInventory.drain(fluidStackToDrain, false);
        return drainedStack != null && drainedStack.amount == gasToDrain;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = MultiblockShapeInfo.builder()
                .aisle("XXXXX", "XIHLX", "XXDXX", "XXXXX", "XXXXX")
                .aisle("XXXXX", "X   X", "G   G", "X   X", "XFFFX")
                .aisle("XXXXX", "X   X", "G   G", "X   X", "XFSFX")
                .aisle("XXXXX", "X   X", "G   G", "X   X", "XFFFX")
                .aisle("XMXEX", "XXOXX", "XXRXX", "XXXXX", "XXXXX")
                .where('X', MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.PLASCRETE))
                .where('G', MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.CLEANROOM_GLASS))
                .where('S', MetaTileEntities.CLEANROOM, EnumFacing.SOUTH)
                .where(' ', Blocks.AIR.getDefaultState())
                .where('E', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.LV], EnumFacing.SOUTH)
                .where('I', MetaTileEntities.PASSTHROUGH_HATCH_ITEM, EnumFacing.NORTH)
                .where('L', MetaTileEntities.PASSTHROUGH_HATCH_FLUID, EnumFacing.NORTH)
                .where('H', MetaTileEntities.HULL[GTValues.HV], EnumFacing.NORTH)
                .where('D', MetaTileEntities.DIODES[GTValues.HV], EnumFacing.NORTH)
                .where('M',
                        () -> ConfigHolder.machines.enableMaintenance ? MetaTileEntities.MAINTENANCE_HATCH :
                                MetaBlocks.CLEANROOM_CASING.getState(BlockCleanroomCasing.CasingType.PLASCRETE),
                        EnumFacing.SOUTH)
                .where('O',
                        Blocks.IRON_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.NORTH)
                                .withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER))
                .where('R', Blocks.IRON_DOOR.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.NORTH)
                        .withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER));

        return shapeInfo;
    }
}
