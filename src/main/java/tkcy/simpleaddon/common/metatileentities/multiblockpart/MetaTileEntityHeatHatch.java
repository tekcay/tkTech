package tkcy.simpleaddon.common.metatileentities.multiblockpart;

import java.util.Collections;
import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.unification.material.Material;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.capabilities.HeatContainer;
import tkcy.simpleaddon.api.capabilities.impl.HeatContainerImpl;
import tkcy.simpleaddon.api.metatileentities.BlockMaterialMetaTileEntityPaint;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.api.utils.rendering.RenderingUtils;
import tkcy.simpleaddon.modules.capabilitiesmodule.ContainerTypeWrapper;

public class MetaTileEntityHeatHatch extends MetaTileEntityCapabilityHatch<HeatContainer>
                                     implements BlockMaterialMetaTileEntityPaint {

    private final int maxHeat = 40000;
    private final Material backgroundMaterial;
    private final Material heatExchangerMaterial;

    public MetaTileEntityHeatHatch(ResourceLocation metaTileEntityId, boolean isInput, int tier,
                                   Material backgroundMaterial, Material heatExchangerMaterial) {
        super(metaTileEntityId, ContainerTypeWrapper.HEAT_WRAPPER, isInput, tier);
        this.backgroundMaterial = backgroundMaterial;
        this.heatExchangerMaterial = heatExchangerMaterial;
    }

    @Override
    public HeatContainer initializeContainer() {
        return new HeatContainerImpl(this, getTier() * this.maxHeat);
    }

    @Override
    protected List<EnumFacing> capabilitySides() {
        return Collections.singletonList(getFrontFacing());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPaintingColorForRendering() {
        return getPaintingColorForRendering(backgroundMaterial);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHeatHatch(metaTileEntityId, isInput(), getTier(), backgroundMaterial,
                heatExchangerMaterial);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        RenderingUtils.renderAllSidesColour(TKCYSATextures.HEAT_HATCH_BACKGROUND, backgroundMaterial, renderState,
                translation, pipeline);

        RenderingUtils.renderAllSidesColour(TKCYSATextures.HEATING_HATCH_0, heatExchangerMaterial, renderState,
                translation, pipeline);

        TKCYSATextures.HEATING_PLATE_FRAME.renderSided(getFrontFacing(), renderState, translation, pipeline);
        RenderingUtils.renderSideColour(TKCYSATextures.HEATING_PLATE, heatExchangerMaterial, getFrontFacing(),
                renderState, translation, pipeline);
    }

    @Override
    public int getPaintingColorForRendering(Material material) {
        return material.getMaterialRGB();
    }
}
