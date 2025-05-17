package tkcy.tktech.common.metatileentities.electric;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;

import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class MTeDryer extends RecipeMapMultiblockController {

    public MTeDryer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.DRYING);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.FRONT, RelativeDirection.UP)
                .aisle("XYX", "XXX", "XXX")
                .aisle("CCC", "C#C", "CCC")
                .aisle("XXX", "XXX", "XXX")
                .where('X', states(getCasingState())
                        .or(autoAbilities()))
                .where('C', heatingCoils())
                .where('Y', selfPredicate())
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.VOLTAGE_CASINGS[0];
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.MACHINE_CASING.getState(BlockMachineCasing.MachineCasingType.ULV);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeDryer(metaTileEntityId);
    }
}
