package tkcy.tktech.common.metatileentities.electric;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.tktech.api.logic.BlockingPipeFaceBehavior;
import tkcy.tktech.api.logic.PipePlacerBehavior;
import tkcy.tktech.api.logic.PipePlacerLogic;
import tkcy.tktech.api.machines.IOnFileClick;
import tkcy.tktech.api.utils.StreamHelper;

public class MTePipePlacer extends TieredMetaTileEntity implements IOnFileClick {

    protected final GTItemStackHandler chargerInventory;
    private final int inventorySize;
    private final PipePlacerLogic pipePlacerLogic;
    @Getter
    private final int maxRange;
    @Getter
    private BlockingPipeFaceBehavior blockingPipeFaceBehavior = BlockingPipeFaceBehavior.NONE;
    @Getter
    private PipePlacerBehavior pipePlacerBehavior = PipePlacerBehavior.PLACE;

    public MTePipePlacer(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.maxRange = 20 * (getTier() + 1);
        this.chargerInventory = new GTItemStackHandler(this, 1);
        this.inventorySize = (tier + 1) * (tier + 1);
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
    public void update() {
        super.update();
        if (!getWorld().isRemote) {

            ((EnergyContainerHandler) this.energyContainer).dischargeOrRechargeEnergyContainers(chargerInventory, 0);

            if (getOffsetTimer() % getTimePerOperation() == 0 &&
                    !isBlockRedstonePowered() &&
                    this.energyContainer.getEnergyStored() >= getEuPerOperation()) {

                boolean didOperate;

                if (pipePlacerBehavior == PipePlacerBehavior.PLACE) {
                    didOperate = pipePlacerLogic.placePipe();
                } else didOperate = pipePlacerLogic.removePipe();

                if (didOperate) this.energyContainer.changeEnergy(-getEuPerOperation());

            }
        }
        checkWeatherOrTerrainExplosion(getTier(), getTier() * 10, energyContainer);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_OUT_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        int rowSize = (int) Math.sqrt(inventorySize);

        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
                18 + 18 * rowSize + 94)
                .label(10, 5, getMetaFullName());

        StreamHelper.initIntStream(inventorySize)
                .forEach(slotIndex -> builder
                        .widget(new SlotWidget(importItems, slotIndex, (18 + 1) * (slotIndex + 1), 18, true, true)
                                .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.STRING_SLOT_OVERLAY)));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * rowSize + 12);
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
            blockingPipeFaceBehavior = blockingPipeFaceBehavior.next();
            playerIn.sendMessage(blockingPipeFaceBehavior.getMessage());
        }
        return true;
    }

    @Override
    public boolean onFileClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                               CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            pipePlacerBehavior = pipePlacerBehavior.next();
            playerIn.sendMessage(pipePlacerBehavior.getMessage());
        }
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ChargerInventory", chargerInventory.serializeNBT());
        blockingPipeFaceBehavior.serialize(data);
        pipePlacerBehavior.serialize(data);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        chargerInventory.deserializeNBT(data.getCompoundTag("ChargerInventory"));
        blockingPipeFaceBehavior = blockingPipeFaceBehavior.deserialize(data);
        pipePlacerBehavior = pipePlacerBehavior.deserialize(data);
    }
}
