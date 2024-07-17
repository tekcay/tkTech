package tkcy.simpleaddon.common.metatileentities.multiblockpart;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.capability.IFluidHandler;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.MetaTileEntityTankValve;

import lombok.Getter;
import tkcy.simpleaddon.api.metatileentities.BlockMaterialMetaTileEntityPaint;
import tkcy.simpleaddon.api.metatileentities.MaterialMetaTileEntity;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class MetaTileEntityModulableTankValve extends MetaTileEntityTankValve
                                              implements IMultiblockAbilityPart<IFluidHandler>,
                                              BlockMaterialMetaTileEntityPaint, MaterialMetaTileEntity {

    @Getter
    private final Material material;

    public MetaTileEntityModulableTankValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, true);
        this.material = material;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityModulableTankValve(metaTileEntityId, material);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return material.equals(Materials.TreatedWood) ? Textures.WOOD_WALL : TKCYSATextures.WALL_TEXTURE;
    }

    @Override
    public int getPaintingColorForRendering() {
        return material.equals(Materials.TreatedWood) ? super.getPaintingColorForRendering() :
                getPaintingColorForRendering(this.material);
    }

    @Override
    public int getPaintingColorForRendering(Material material) {
        return this.material.getMaterialRGB();
    }
}
