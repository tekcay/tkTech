package tkcy.tktech.common.metatileentities.electric;

import static gregtech.api.unification.material.Materials.Steel;
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

import tkcy.tktech.api.capabilities.multiblock.TkTechMultiblockAbilities;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class MTeAssemblingMachine extends RecipeMapMultiblockController {

    public MTeAssemblingMachine(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.ADVANCED_ASSEMBLING);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "XXX", "F#F")
                .aisle("XXX", "XXX", "F#F")
                .aisle("XXX", "XYX", "F#F")
                .where('X', states(getCasingState())
                        .or(autoAbilities())
                        .or(abilities(TkTechMultiblockAbilities.CONTROLLER_PROXY).setMaxGlobalLimited(1, 1)))
                .where('Y', selfPredicate())
                .where('#', air())
                .where('F', frames(Steel))
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
        return new MTeAssemblingMachine(metaTileEntityId);
    }
}
