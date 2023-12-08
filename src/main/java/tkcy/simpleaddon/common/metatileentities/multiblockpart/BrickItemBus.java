package tkcy.simpleaddon.common.metatileentities.multiblockpart;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityItemBus;

import tkcy.simpleaddon.api.capabilities.TKCYSAMultiblockAbility;

public class BrickItemBus extends MetaTileEntityItemBus {

    private final int inventorySize = 2;

    public BrickItemBus(ResourceLocation metaTileEntityId, boolean isExportHatch) {
        super(metaTileEntityId, 0, isExportHatch);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new BrickItemBus(metaTileEntityId, isExportHatch);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return Textures.COKE_BRICKS;
    }

    @Override
    public MultiblockAbility<IItemHandlerModifiable> getAbility() {
        return TKCYSAMultiblockAbility.BRICK_ITEMS;
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return isExportHatch ? new NotifiableItemStackHandler(this, inventorySize, getController(), true) :
                new GTItemStackHandler(this, 0);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return isExportHatch ? new GTItemStackHandler(this, 0) :
                new NotifiableItemStackHandler(this, inventorySize, getController(), false);
    }
}
