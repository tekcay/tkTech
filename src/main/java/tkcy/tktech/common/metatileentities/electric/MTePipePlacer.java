package tkcy.tktech.common.metatileentities.electric;

import static gregtech.api.capability.GregtechDataCodes.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

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

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.logic.PipePlacerLogic;
import tkcy.tktech.api.utils.StreamHelper;

public class MTePipePlacer extends TieredMetaTileEntity {

    protected final GTItemStackHandler chargerInventory;
    private final int inventorySize;
    private final PipePlacerLogic pipePlacerLogic;

    public MTePipePlacer(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.chargerInventory = new GTItemStackHandler(this, 1);
        this.inventorySize = (tier + 1) * (tier + 1);
        this.pipePlacerLogic = new PipePlacerLogic(this, tier * 10);
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
            if (getOffsetTimer() % getTimePerOperation() == 0) {
                if (this.energyContainer.getEnergyStored() >= getEuPerOperation()) {
                    if (pipePlacerLogic.placePipe()) {
                        this.energyContainer.changeEnergy(-getEuPerOperation());
                    }
                }
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("ChargerInventory", chargerInventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.chargerInventory.deserializeNBT(data.getCompoundTag("ChargerInventory"));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
    }
}
