package tkcy.tktech.common.metatileentities.multiblockpart;

import net.minecraft.util.ResourceLocation;

import gregtech.api.capability.IMufflerHatch;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMufflerHatch;

import tkcy.tktech.api.capabilities.multiblock.TkTechMultiblockAbilities;

public class MTeBrickMufflerHatch extends MetaTileEntityMufflerHatch {

    public MTeBrickMufflerHatch(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, 0);
    }

    @Override
    public MultiblockAbility<IMufflerHatch> getAbility() {
        return TkTechMultiblockAbilities.BRICK_MUFFLER;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeBrickMufflerHatch(metaTileEntityId);
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return Textures.COKE_BRICKS;
    }
}
