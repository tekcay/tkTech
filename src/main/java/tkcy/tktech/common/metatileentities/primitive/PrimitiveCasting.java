package tkcy.tktech.common.metatileentities.primitive;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.RenderUtil;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import tkcy.tktech.api.machines.PrimitiveSingleBlock;
import tkcy.tktech.api.recipes.CastingInfo;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class PrimitiveCasting extends PrimitiveSingleBlock {

    public PrimitiveCasting(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.CASTING, Textures.COKE_OVEN_OVERLAY);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new PrimitiveCasting(metaTileEntityId);
    }

    @Override
    public ICubeRenderer getBaseRenderer() {
        return Textures.COKE_BRICKS;
    }

    @Override
    protected SoundEvent getRecipeSound() {
        return SoundEvents.BLOCK_LAVA_EXTINGUISH;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);

        getBaseRenderer().render(renderState, translation, pipeline);
        Textures.COKE_OVEN_OVERLAY.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), false,
                false);
        Textures.STEAM_VENT_OVERLAY.renderSided(EnumFacing.UP, renderState,
                RenderUtil.adjustTrans(translation, EnumFacing.UP, 2), pipeline);

        renderer.renderOrientedState(renderState, translation, pipeline, getFrontFacing(), primitiveLogic.isActive(),
                primitiveLogic.isWorkingEnabled());
    }

    @Override
    public void update() {
        super.update();
        if (isActive()) {
            final BlockPos pos = getPos();
            float x = pos.getX() + 0.5F;
            float z = pos.getZ() + 0.5F;
            final float y = pos.getY() + GTValues.RNG.nextFloat() * 0.375F;
            randomDisplayTick(x, y, z);

            if (getOffsetTimer() % 4 == 0) {
                getWorld().playSound(x, y, z, getRecipeSound(), SoundCategory.BLOCKS, 1.0F,
                        1.0F, false);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void randomDisplayTick(float x, float y, float z) {
        getWorld().spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
        getWorld().spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tktech.casting.tooltip.1"));
        tooltip.add(I18n.format("tktech.casting.tooltip.2"));
        tooltip.add(I18n.format("tktech.casting.tooltip.3"));
        tooltip.add(I18n.format("tktech.casting.tooltip.4"));
        tooltip.add(I18n.format("tktech.casting.tooltip.5"));
        CastingInfo.CASTING_INFOS.forEach(castingInfo -> CastingInfo.addToTooltip.accept(castingInfo, tooltip));
    }
}
