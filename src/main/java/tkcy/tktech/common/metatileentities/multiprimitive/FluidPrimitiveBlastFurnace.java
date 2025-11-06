package tkcy.tktech.common.metatileentities.multiprimitive;

import static tkcy.tktech.api.predicates.TkTechPredicates.*;
import static tkcy.tktech.api.utils.BlockPatternUtils.growAisle;

import java.util.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import lombok.Getter;
import tkcy.tktech.api.capabilities.TkTechMultiblockAbilities;
import tkcy.tktech.api.machines.IOnFileClick;
import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.metatileentities.IIgnitable;
import tkcy.tktech.api.metatileentities.IScalableMultiblock;
import tkcy.tktech.api.metatileentities.RepetitiveSide;
import tkcy.tktech.api.predicates.TkTechPredicates;
import tkcy.tktech.api.recipes.properties.IsIgnitedRecipeProperty;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;

public class FluidPrimitiveBlastFurnace extends NoEnergyMultiController
                                        implements IOnFileClick, IIgnitable, IScalableMultiblock {

    @Getter
    private int size = 0;
    private boolean isIgnited;

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    @Override
    public void setSize(int size) {
        this.size = size;
        reinitializeStructurePattern();
        markDirty();
    }

    @Override
    public MultiblockControllerBase multiblock() {
        return this;
    }

    @Override
    public int getMaxSize() {
        return 4;
    }

    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(TkTechMultiblockAbilities.BRICK_BUS_INPUT));
        this.inputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(),
                getAbilities(TkTechMultiblockAbilities.BRICK_HATCH_INPUT));
        this.outputInventory = new ItemHandlerList(getAbilities(TkTechMultiblockAbilities.BRICK_BUS_OUTPUT));
        this.outputFluidInventory = new FluidTankList(allowSameFluidFillForOutputs(),
                getAbilities(TkTechMultiblockAbilities.BRICK_HATCH_OUTPUT));
    }

    @Override
    public boolean onFileClick(EntityPlayer playerIn, EnumHand hand,
                               EnumFacing wrenchSide,
                               CuboidRayTraceResult hitResult) {
        setSizeOnToolClick(playerIn);
        return IOnFileClick.super.onFileClick(playerIn, hand, wrenchSide, hitResult);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        int parallelLimit = context.getOrDefault(RepetitiveSide.getHeightMarker(), 1);
        recipeMapWorkable.setParallelLimit(parallelLimit);
    }

    @Override
    public @NotNull BlockPattern createStructurePattern(int size) {
        return FactoryBlockPattern.start()
                .aisle(growAisle(size, "AAA", "XXX", "BBB"))
                .aisle(growAisle(size, "AAA", "X#X", "BCB")).setRepeatable(Math.max(1, 1 + size * 2))
                .aisle(growAisle(size, "YAA", "XXX", "BBB"))
                .where('A', cokeBrick().or(brickFluidHatch(true, 1)))
                .where('B', cokeBrick().or(brickItemBus(false, 2)))
                .where('C', cokeBrick().or(brickFluidHatch(false, 1)))
                .where('X', cokeBrick())
                .where('#', TkTechPredicates.isAir(RepetitiveSide.getHeightMarker()))
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return createStructurePattern(getSize());
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        return getMatchingShapes(this.structurePattern);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, @NotNull List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        addInformation(tooltip);
        addIgnitableInformation(tooltip);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("size", getSize());
        data.setBoolean(IIgnitable.NBT_LABEL, isIgnited());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        setSize(data.getInteger("size"));
        isIgnited = data.getBoolean(IIgnitable.NBT_LABEL);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(getSize());
        buf.writeBoolean(isIgnited());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        setSize(buf.readInt());
        isIgnited = buf.readBoolean();
    }

    @Override
    public boolean isIgnited() {
        return isIgnited;
    }

    @Override
    public void ignite() {
        isIgnited = true;
        markDirty();
    }

    @Override
    public void shutOff() {
        isIgnited = false;
        markDirty();
    }

    @Override
    public boolean checkRecipe(@NotNull Recipe recipe, boolean consumeIfSuccess) {
        if (recipe.hasProperty(IsIgnitedRecipeProperty.getInstance())) {
            return isIgnited();
        }
        return super.checkRecipe(recipe, consumeIfSuccess);
    }
}
