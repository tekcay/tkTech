package tkcy.simpleaddon.common.metatileentities.multiprimitive;

import static gregtech.api.util.RelativeDirection.*;
import static gregtech.api.util.RelativeDirection.UP;

import java.util.List;
import java.util.function.Function;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import gregtech.api.capability.impl.FilteredFluidHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.PropertyFluidFilter;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.FluidPipeProperties;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import lombok.Getter;
import tkcy.simpleaddon.api.metatileentities.BlockMaterialMetaTileEntityPaint;
import tkcy.simpleaddon.api.metatileentities.MaterialMetaTileEntity;
import tkcy.simpleaddon.api.metatileentities.MetaTileEntityStorageFormat;
import tkcy.simpleaddon.api.metatileentities.RepetitiveSide;
import tkcy.simpleaddon.api.predicates.TKCYSAPredicates;
import tkcy.simpleaddon.api.render.TKCYSATextures;
import tkcy.simpleaddon.api.utils.MaterialHelper;
import tkcy.simpleaddon.api.utils.StorageUtils;
import tkcy.simpleaddon.api.utils.units.CommonUnits;
import tkcy.simpleaddon.api.utils.units.UnitsConversions;
import tkcy.simpleaddon.common.block.TKCYSAMetaBlocks;
import tkcy.simpleaddon.modules.storagemodule.StorageModule;

@StorageModule.StorageModulable
public class ModulableTank extends MultiblockWithDisplayBase
                           implements RepetitiveSide, BlockMaterialMetaTileEntityPaint, MaterialMetaTileEntity,
                           MetaTileEntityStorageFormat<FluidStack> {

    @Getter
    private final Material material;
    private int height;
    private final boolean isLarge;
    private final int layerCapacity;
    private int totalCapacity;
    private FluidPipeProperties fluidPipeProperties;
    private FilteredFluidHandler tank;

    public ModulableTank(ResourceLocation metaTileEntityId, Material material, boolean isLarge) {
        super(metaTileEntityId);
        this.material = material;
        this.isLarge = isLarge;
        this.layerCapacity = (int) Math.pow(10, 6) * (isLarge ? 21 : 1);
        initializeInventory();
    }

    private void setFluidPipeProperties() {
        this.fluidPipeProperties = MaterialHelper.getMaterialProperty(this.material, PropertyKey.FLUID_PIPE);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.height = context.getOrDefault(RepetitiveSide.getHeightMarker(), 0) + 1;
        this.totalCapacity = this.layerCapacity * this.height;
    }

    @Override
    protected void initializeInventory() {
        if (this.material == null) return;
        super.initializeInventory();

        setFluidPipeProperties();

        this.tank = new FilteredFluidHandler(this.totalCapacity);
        this.tank.setFilter(new PropertyFluidFilter(
                this.fluidPipeProperties.getMaxFluidTemperature(),
                false,
                this.fluidPipeProperties.isAcidProof(),
                false,
                false));

        this.exportFluids = this.importFluids = new FluidTankList(true, this.tank);
        this.fluidInventory = this.tank;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new ModulableTank(metaTileEntityId, material, isLarge);
    }

    @Override
    protected void updateFormedValid() {
        this.tank.setCapacity(this.totalCapacity);
    }

    @NotNull
    @Override
    protected BlockPattern createStructurePattern() {
        return (this.isLarge ? getLargeTankPattern() : getTankPattern())
                .where('S', selfPredicate())
                .where(' ', any())
                .where('A', air())
                .where('I', TKCYSAPredicates.isAir(RepetitiveSide.getHeightMarker()))
                .where('X',
                        TKCYSAPredicates.iBlockStatePredicate(getSideBlockBlockState())
                                .or(TKCYSAPredicates.metaTileEntityPredicate(StorageModule.getValve(this.material))
                                        .setMaxGlobalLimited(4)))
                .build();
    }

    private FactoryBlockPattern getLargeTankPattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("  XXX  ", " XXXXX ", "XXXXXXX", "XXXXXXX", "XXXXXXX", " XXXXX ", "  XXX  ")
                .aisle("  XSX  ", " XAAAX ", "XAAAAAX", "XAAAAAX", "XAAAAAX", " XAAAX ", "  XXX  ")
                .aisle("  XXX  ", " XAAAX ", "XAAAAAX", "XAAIAAX", "XAAAAAX", " XAAAX ", "  XXX  ")
                .setRepeatable(getMinSideLength(), getMaxSideLength())
                .aisle("  XXX  ", " XXXXX ", "XXXXXXX", "XXXXXXX", "XXXXXXX", " XXXXX ", "  XXX  ");
    }

    private FactoryBlockPattern getTankPattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XXX", "XXX", "XXX")
                .aisle("XSX", "X X", "XXX")
                .aisle("XXX", "XIX", "XXX").setRepeatable(getMinSideLength(), getMaxSideLength())
                .aisle("XXX", "XXX", "XXX");
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return TKCYSATextures.WALL_TEXTURE;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                CuboidRayTraceResult hitResult) {
        if (!isStructureFormed())
            return false;
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (isStructureFormed()) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidInventory);
            } else {
                return null;
            }
        }
        return super.getCapability(capability, side);
    }

    @Override
    public int getMinSideLength() {
        return 1;
    }

    @Override
    public int getMaxSideLength() {
        return 8;
    }

    @Override
    public IBlockState getSideBlockBlockState() {
        return TKCYSAMetaBlocks.WALLS.get(this.material).getBlock(this.material);
    }

    @Override
    public CommonUnits getBaseContentUnit() {
        return CommonUnits.liter;
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
        return UnitsConversions.convertAndFormatToSizeOfOrder(this.layerCapacity, getBaseContentUnit());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("tkcysa.multiblock.modulable_tank.tooltip"));
        tooltip.add(I18n.format(
                "tkcysa.multiblock.modulable_storage.layer_infos", getCapacityPerLayerFormatted(), getMaxSideLength()));

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            tooltip.add(I18n.format("gregtech.fluid_pipe.max_temperature",
                    this.fluidPipeProperties.getMaxFluidTemperature()));
            if (this.fluidPipeProperties.isAcidProof()) {
                tooltip.add(I18n.format("gregtech.fluid_pipe.acid_proof"));
            }
        } else {
            tooltip.add(I18n.format("gregtech.tooltip.fluid_pipe_hold_shift"));
        }
    }

    @Override
    public void displayInfos(List<ITextComponent> textList) {
        StorageUtils<FluidStack> stackStorageUtil = getStorageUtil();

        textList.add(stackStorageUtil.getCapacityTextTranslation());
        textList.add(stackStorageUtil.getContentTextTranslation());
        textList.add(stackStorageUtil.getFillPercentageTextTranslation());
    }

    @Override
    public int getMaxCapacity() {
        return this.totalCapacity;
    }

    @Override
    public Function<FluidStack, String> getContentLocalizedNameProvider() {
        return FluidStack::getLocalizedName;
    }

    @Override
    public Function<FluidStack, Integer> getContentAmountProvider() {
        return fluidStack -> fluidStack.amount;
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
    public StorageUtils<FluidStack> getStorageUtil() {
        return new StorageUtils<>(this);
    }

    @Override
    @Nullable
    public FluidStack getContent() {
        return this.fluidInventory.drain(Integer.MAX_VALUE, false);
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
}
