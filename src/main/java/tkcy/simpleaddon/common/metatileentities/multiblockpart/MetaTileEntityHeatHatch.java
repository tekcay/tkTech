package tkcy.simpleaddon.common.metatileentities.multiblockpart;

import gregtech.client.renderer.texture.Textures;
import net.minecraft.util.ResourceLocation;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;

import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.impl.HeatContainerImpl;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public class MetaTileEntityHeatHatch extends MetaTileEntityCapabilityHatch<HeatContainer> {

    private final int maxHeat = 40000;

    public MetaTileEntityHeatHatch(ResourceLocation metaTileEntityId, boolean isInput, int tier) {
        super(metaTileEntityId, ContainerTypeWrapper.HEAT_WRAPPER, isInput, tier);
    }

    @Override
    public HeatContainer initializeContainer() {
        return new HeatContainerImpl(this, getTier() * this.maxHeat);
    }

    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return Textures.FURNACE_OVERLAY;
    }

    @Override
    protected ICubeRenderer getMainTexture() {
        return Textures.AIR_VENT_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHeatHatch(metaTileEntityId, isInput(), getTier());
    }
}
