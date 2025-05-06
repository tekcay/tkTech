package tkcy.simpleaddon.common.metatileentities.primitive;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.core.sound.GTSoundEvents;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.simpleaddon.api.machines.PrimitiveSingleBlock;
import tkcy.simpleaddon.api.recipes.recipemaps.TKCYSARecipeMaps;

public class PrimitiveBath extends PrimitiveSingleBlock {

    public PrimitiveBath(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TKCYSARecipeMaps.PRIMITIVE_BATH_RECIPES, Textures.COKE_OVEN_OVERLAY);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new PrimitiveBath(metaTileEntityId);
    }

    @Override
    public ICubeRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    protected SoundEvent getRecipeSound() {
        return GTSoundEvents.BATH;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        getBaseRenderer().render(renderState, translation, pipeline);
        Textures.COKE_OVEN_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), false,
                false);

        renderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), primitiveLogic.isActive(),
                primitiveLogic.isWorkingEnabled());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {}
}
