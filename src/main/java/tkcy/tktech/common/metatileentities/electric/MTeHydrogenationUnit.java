package tkcy.tktech.common.metatileentities.electric;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockHermeticCasing;
import gregtech.common.blocks.MetaBlocks;

import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class MTeHydrogenationUnit extends RecipeMapMultiblockController {

    public MTeHydrogenationUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.HYDROGENATION);
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

    public IBlockState getCasingState() {
        return MetaBlocks.HERMETIC_CASING.getState(BlockHermeticCasing.HermeticCasingsType.HERMETIC_MV);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.STEEL_FIREBOX;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MTeHydrogenationUnit(metaTileEntityId);
    }
}
