package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import static tkcy.simpleaddon.api.predicates.Predicates.*;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import tkcy.simpleaddon.api.machines.NoEnergyMultiController;
import tkcy.simpleaddon.api.recipes.TKCYSARecipeMaps;

public class FluidPrimitiveBlastFurnace extends NoEnergyMultiController {

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("AAA", "XXX", "XXX", "BBB")
                .aisle("AAA", "XXX", "X#X", "BCB")
                .aisle("AAA", "XYX", "XXX", "BBB")
                .where('A', cokeBrick().or(fluidHatch(true, 1)))
                .where('B', cokeBrick().or(itemBus(false, 2)))
                .where('C', cokeBrick().or(fluidHatch(false, 1)))
                .where('X', cokeBrick())
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_BLAST_FURNACE_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.COKE_BRICKS;
    }
}
