package tkcy.tktech.common.metatileentities.multiprimitive;

import static tkcy.tktech.modules.storagemodule.StorageModule.getLargeTankPattern;
import static tkcy.tktech.modules.storagemodule.StorageModule.getTankPattern;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.tktech.api.metatileentities.BlockMaterialMetaTileEntityPaint;
import tkcy.tktech.api.metatileentities.MaterialMetaTileEntity;
import tkcy.tktech.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.tktech.api.metatileentities.RepetitiveSide;
import tkcy.tktech.api.predicates.TkTechPredicates;
import tkcy.tktech.api.render.TkTechTextures;
import tkcy.tktech.api.utils.StorageUtils;
import tkcy.tktech.api.utils.units.UnitsConversions;
import tkcy.tktech.common.block.TkTechMetaBlocks;
import tkcy.tktech.modules.storagemodule.StorageModule;

@Getter
@StorageModule.StorageModulable
public abstract class MetaTileEntityMultiblockStorage<ContentHandler, ContentType> extends MultiblockWithDisplayBase
                                                     implements RepetitiveSide, BlockMaterialMetaTileEntityPaint,
                                                     MaterialMetaTileEntity,
                                                     MetaTileEntityStorageFormat<ContentType> {

    private final Material material;
    private final boolean isLarge;
    private int totalCapacity;
    private int height;

    public MetaTileEntityMultiblockStorage(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId);
        this.material = material;
        this.isLarge = isLarge;
        initializeInventory();
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.height = context.getOrDefault(RepetitiveSide.getHeightMarker(), 0) + 1;
        this.totalCapacity = getLayerCapacity() * this.height;
    }

    protected int getLayerCapacity() {
        return (isLarge ? 21 : 1);
    }

    protected abstract Capability<ContentHandler> getCapability();

    protected abstract ContentHandler getHandler();

    protected abstract TraceabilityPredicate getTransferPredicate();

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return (this.isLarge ? getLargeTankPattern(this) : getTankPattern(this))
                .where('S', selfPredicate())
                .where(' ', any())
                .where('A', air())
                .where('I', TkTechPredicates.isAir(RepetitiveSide.getHeightMarker()))
                .where('X',
                        TkTechPredicates.iBlockStatePredicate(getSideBlockBlockState())
                                .or(getTransferPredicate()
                                        .setMaxGlobalLimited(4)))
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TkTechTextures.WALL_TEXTURE;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        if (!isStructureFormed()) return false;
        return super.onRightClick(playerIn, hand, facing, hitResult);
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return isStructureFormed();
    }

    @Override
    public int getPaintingColorForRendering(Material material) {
        return material.getMaterialRGB();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPaintingColorForRendering() {
        return getPaintingColorForRendering(this.material);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @SideOnly(Side.CLIENT)
    @NotNull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.MULTIBLOCK_TANK_OVERLAY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == getCapability()) {
            if (isStructureFormed()) return getCapability().cast(getHandler());
            else return null;
        }
        return super.getCapability(capability, side);
    }

    @Override
    public int getMinSideLength() {
        return 0;
    }

    @Override
    public int getMaxSideLength() {
        return 8;
    }

    @Override
    public IBlockState getSideBlockBlockState() {
        return TkTechMetaBlocks.WALLS.get(this.material).getBlock(this.material);
    }

    @Override
    public MetaTileEntity getMetatileEntity() {
        return this;
    }

    @Override
    public int getLayersPerParallel() {
        return 0;
    }

    @Override
    public Function<Integer, BlockPos> getRepetitiveDirection() {
        return pos -> this.getPos().up(pos);
    }

    public String getCapacityPerLayerFormatted() {
        return UnitsConversions.convertAndFormatToSizeOfOrder(getBaseContentUnit(), getLayerCapacity());
    }

    @Override
    public void displayInfos(List<ITextComponent> textList) {
        StorageUtils<ContentType> stackStorageUtil = getStorageUtil();

        textList.add(stackStorageUtil.getCapacityTextTranslation());
        textList.add(stackStorageUtil.getContentTextTranslation());
        textList.add(stackStorageUtil.getFillPercentageTextTranslation());
    }

    @Override
    public int getMaxCapacity() {
        return this.totalCapacity;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if (!isStructureFormed()) {
            ITextComponent tooltip = new TextComponentTranslation("gregtech.multiblock.invalid_structure.tooltip");
            tooltip.setStyle((new Style()).setColor(TextFormatting.GRAY));
            textList.add(
                    (new TextComponentTranslation("gregtech.multiblock.invalid_structure"))
                            .setStyle((new Style())
                                    .setColor(TextFormatting.RED)
                                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip))));
        } else {
            displayInfos(textList);
        }
    }

    @Override
    public StorageUtils<ContentType> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    public String getPercentageTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.fill.percentage";
    }

    @Override
    public String getCapacityTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.capacity";
    }

    @Override
    public String getContentTextTranslationKey() {
        return "tkcysa.multiblock.modulable_storage.content";
    }

    @Override
    public String getLinkingWordForContentDisplay() {
        return " of ";
    }
}
