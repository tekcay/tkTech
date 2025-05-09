package tkcy.tktech.common.metatileentities.storage;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.tktech.api.metatileentities.BlockMaterialMetaTileEntityPaint;
import tkcy.tktech.api.metatileentities.MaterialMetaTileEntity;
import tkcy.tktech.api.render.TkTechTextures;

@Getter
public abstract class MetaTileEntityModulableValve<T> extends MetaTileEntityMultiblockPart
                                                  implements IMultiblockAbilityPart<T>,
                                                  BlockMaterialMetaTileEntityPaint, MaterialMetaTileEntity {

    private final Material material;

    public MetaTileEntityModulableValve(ResourceLocation metaTileEntityId, Material material) {
        super(metaTileEntityId, 0);
        this.material = material;
    }

    @Override
    public ICubeRenderer getBaseTexture() {
        return TkTechTextures.WALL_TEXTURE;
    }

    @Override
    public int getPaintingColorForRendering() {
        return getPaintingColorForRendering(this.material);
    }

    @Override
    public int getPaintingColorForRendering(Material material) {
        return this.material.getMaterialRGB();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        Textures.PIPE_IN_OVERLAY.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    public int getDefaultPaintingColor() {
        return 0xFFFFFF;
    }

    @Override
    public void update() {
        super.update();

        if (!doesAutoOutput()) return;

        if (!getWorld().isRemote && getOffsetTimer() % 5 == 0L && isAttachedToMultiBlock() &&
                getFrontFacing() == EnumFacing.DOWN) {
            TileEntity tileEntity = getNeighbor(getFrontFacing());
            T handler = getHandler(tileEntity);
            if (handler != null) {
                autoOutputInventory(handler);
            }
        }
    }

    protected T getHandler(@Nullable TileEntity tileEntity) {
        return tileEntity == null ?
                null : tileEntity.getCapability(this.getCapability(), getFrontFacing().getOpposite());
    }

    /**
     * When this block is not connected to any multiblock it uses dummy inventory to prevent problems with capability
     * checks
     */
    protected abstract void initializeDummyInventory();

    protected abstract void autoOutputInventory(T handler);

    protected abstract Capability<T> getCapability();

    protected abstract boolean doesAutoOutput();

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        initializeDummyInventory();
    }

    @Override
    public void removeFromMultiBlock(MultiblockControllerBase controllerBase) {
        super.removeFromMultiBlock(controllerBase);
        initializeDummyInventory();
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    public boolean canPartShare() {
        return false;
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.tank_valve.tooltip"));
    }

    @Override
    public boolean needsSneakToRotate() {
        return true;
    }

    @Override
    public void addToolUsages(ItemStack stack, @Nullable World world, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.tool_action.screwdriver.access_covers"));
        tooltip.add(I18n.format("gregtech.tool_action.wrench.set_facing"));
        super.addToolUsages(stack, world, tooltip, advanced);
    }
}
