package tkcy.tktech.common.metatileentities.electric;

import static tkcy.tktech.api.utils.IEnumUtils.getMaxButtonWidth;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.tktech.api.logic.pipeplacer.BlockingPipeFaceBehavior;
import tkcy.tktech.api.logic.pipeplacer.PipePlacerBehavior;
import tkcy.tktech.api.logic.pipeplacer.PipePlacerLogic;
import tkcy.tktech.api.logic.pipeplacer.PipePlacerPaintingBehavior;
import tkcy.tktech.api.machines.IOnFileClick;
import tkcy.tktech.api.utils.IEnumUtils;
import tkcy.tktech.api.utils.StreamHelper;

public class MTePipePlacer extends TieredMetaTileEntity implements IOnFileClick {

    protected final GTItemStackHandler chargerInventory;
    private final int inventorySize;
    private final PipePlacerLogic pipePlacerLogic;
    @Getter
    private final int maxRange;
    @Getter
    private BlockingPipeFaceBehavior blockingFaceBehavior = BlockingPipeFaceBehavior.NONE;
    @Getter
    private PipePlacerBehavior placingBehavior = PipePlacerBehavior.PLACE;
    @Getter
    private PipePlacerPaintingBehavior paintingBehavior = PipePlacerPaintingBehavior.NONE;
    @Getter
    private final FluidStack paintingRemovalFluid = Materials.Acetone.getFluid(1);

    private static final int PADDING = 3;
    private static final int SIZE = 18;

    public MTePipePlacer(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.maxRange = 20 * (getTier() + 1);
        this.chargerInventory = new GTItemStackHandler(this, 1);
        this.inventorySize = (tier + 1);
        this.pipePlacerLogic = new PipePlacerLogic(this);
        initializeInventory();
    }

    public int getTimePerOperation() {
        return 20 / (getTier() + 1);
    }

    public int getEuPerOperation() {
        return GTValues.VA[getTier() + 1];
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTePipePlacer(metaTileEntityId, getTier());
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new GTItemStackHandler(this, inventorySize);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, new FluidTank(1000));
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {

            ((EnergyContainerHandler) this.energyContainer).dischargeOrRechargeEnergyContainers(chargerInventory, 0);

            if (getOffsetTimer() % getTimePerOperation() == 0 &&
                    !isBlockRedstonePowered() &&
                    this.energyContainer.getEnergyStored() >= getEuPerOperation()) {

                boolean didOperate = false;

                if (placingBehavior != PipePlacerBehavior.REMOVAL) {
                    if (placingBehavior == PipePlacerBehavior.PLACE) {
                        didOperate = pipePlacerLogic.tryPlacePipe();
                    }

                    if (paintingBehavior == PipePlacerPaintingBehavior.PAINT) {
                        didOperate = pipePlacerLogic.tryPaintPipe();
                    } else if (paintingBehavior == PipePlacerPaintingBehavior.REMOVAL) {
                        didOperate = pipePlacerLogic.tryRemovePipePainting();
                    }

                    if (blockingFaceBehavior != BlockingPipeFaceBehavior.NONE) {
                        didOperate = pipePlacerLogic.tryBlockFacePipe();
                    }

                } else didOperate = pipePlacerLogic.tryRemovePipe();

                if (didOperate) this.energyContainer.changeEnergy(-getEuPerOperation());
            }
        }
        pipePlacerLogic.reset();
        checkWeatherOrTerrainExplosion(getTier(), getTier() * 10, energyContainer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    private ModularUI.Builder mp(ModularUI.Builder builder, int y, IEnumUtils<?> enumUtils, IntConsumer consumer) {
        return builder.widget(new CycleButtonWidget(
                20,
                y,
                getMaxButtonWidth(placingBehavior),
                SIZE,
                enumUtils.valuesString(),
                () -> enumUtils.getEnum().ordinal(),
                consumer));
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(inventorySize);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
                18 + 18 * rowSize + 204)
                .label(10, 5, getMetaFullName());

        AtomicInteger x = new AtomicInteger();
        StreamHelper.initIntStream(inventorySize)
                .forEach(slotIndex -> builder
                        .widget(new SlotWidget(importItems, slotIndex, x.addAndGet(19), 18, true, true)
                                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.STRING_SLOT_OVERLAY)));

        builder.widget(new TankWidget(importFluids.getTankAt(0), x.addAndGet(19), 18, 18, 18)
                .setAlwaysShowFull(true)
                .setBackgroundTexture(GuiTextures.FLUID_SLOT)
                .setContainerClicking(true, true));

        int y = 40;

        placingBehavior.widget(builder, 20, y, () -> placingBehavior.ordinal(),
                () -> placingBehavior = placingBehavior.next());
        blockingFaceBehavior.widget(builder, 20, y += 30, () -> blockingFaceBehavior.ordinal(),
                () -> blockingFaceBehavior = blockingFaceBehavior.next());
        paintingBehavior.widget(builder, 20, y += 30, () -> paintingBehavior.ordinal(),
                () -> paintingBehavior = paintingBehavior.next());

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * 6 + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tktech.pipeplacer.tooltip.range", getMaxRange()));
        tooltip.add(I18n.format("tktech.pipeplacer.tooltip.eu_per_operation", getEuPerOperation()));
        tooltip.add(I18n.format("tktech.pipeplacer.tooltip.time_per_operation", getTimePerOperation()));
        tooltip.add(I18n.format("gregtech.universal.tooltip.voltage_in", energyContainer.getInputVoltage(),
                GTValues.VNF[getTier()]));
        tooltip.add(
                I18n.format("gregtech.universal.tooltip.energy_storage_capacity", energyContainer.getEnergyCapacity()));
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("tktech.pipeplacer.onScrewdriverClick.tooltip"));
        tooltip.add(I18n.format("tktech.pipeplacer.onFileClick.tooltip"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }

    @Override
    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                      CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            blockingFaceBehavior = blockingFaceBehavior.next();
            playerIn.sendMessage(blockingFaceBehavior.getMessage());
        }
        return true;
    }

    @Override
    public boolean onFileClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                               CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            placingBehavior = placingBehavior.next();
            playerIn.sendMessage(placingBehavior.getMessage());
        }
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ChargerInventory", chargerInventory.serializeNBT());
        blockingFaceBehavior.serialize(data);
        placingBehavior.serialize(data);
        paintingBehavior.serialize(data);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        chargerInventory.deserializeNBT(data.getCompoundTag("ChargerInventory"));
        blockingFaceBehavior = blockingFaceBehavior.deserialize(data);
        placingBehavior = placingBehavior.deserialize(data);
        paintingBehavior = paintingBehavior.deserialize(data);
    }
}
