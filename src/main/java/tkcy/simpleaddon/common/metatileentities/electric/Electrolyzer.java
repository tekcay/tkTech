package tkcy.simpleaddon.common.metatileentities.electric;

import static gregtech.common.blocks.BlockMetalCasing.MetalCasingType.STEEL_SOLID;
import static gregtech.common.blocks.MetaBlocks.METAL_CASING;

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
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class Electrolyzer extends RecipeMapMultiblockController {

    public Electrolyzer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.ADVANCED_ELECTROLYSIS);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "XXX")
                .aisle("XXX", "X#X", "XXX")
                .aisle("XXX", "XYX", "XXX")
                .where('X', states(getCasingState())
                        .or(autoAbilities()))
                .where('Y', selfPredicate())
                .where('#', air())
                .build();
    }

    private IBlockState getCasingState() {
        return METAL_CASING.getState(STEEL_SOLID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new Electrolyzer(metaTileEntityId);
    }
}
