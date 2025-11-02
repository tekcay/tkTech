package tkcy.tktech.common.metatileentities.multiprimitive;

import static gregtech.api.util.RelativeDirection.*;
import static tkcy.tktech.api.predicates.TkTechPredicates.*;
import static tkcy.tktech.api.utils.BlockPatternUtils.growGrow;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import lombok.Getter;
import tkcy.tktech.api.capabilities.TkTechMultiblockAbilities;
import tkcy.tktech.api.machines.IOnSolderingIronClick;
import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.MultiblockShapeInfoHelper;
import tkcy.tktech.common.metatileentities.TkTechMetaTileEntities;

public class FluidPrimitiveBlastFurnace extends NoEnergyMultiController implements IOnSolderingIronClick {

    @Getter
    private int size = 0;

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.FLUID_PRIMITIVE_BLAST);
    }

    protected void setSize(int size) {
        this.size = size;
        markDirty();
        reinitializeStructurePattern();
        recipeMapWorkable.setParallelLimit(size + 1);
    }

    protected int getMaxSize() {
        return 7;
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
    public boolean onSolderingIronClick(EntityPlayer playerIn, EnumHand hand,
                                        EnumFacing wrenchSide,
                                        CuboidRayTraceResult hitResult) {
        if (playerIn.isSneaking()) {
            setSize(Math.max(0, getSize() - 1));
        } else setSize(Math.min(getSize() + 1, getMaxSize()));
        playerIn.sendMessage(new TextComponentString("Size : " + getSize()));
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(growGrow(getSize(), "AAA", "XXX", "BBB"))
                .aisle(growGrow(getSize(), "AAA", "X#X", "BCB")).setRepeatable(Math.max(1, 1 + getSize() * 2))
                .aisle(growGrow(getSize(), "YAA", "XXX", "BBB"))
                .where('A', cokeBrick().or(brickFluidHatch(true, 1)))
                .where('B', cokeBrick().or(brickItemBus(false, 2)))
                .where('C', cokeBrick().or(brickFluidHatch(false, 1)))
                .where('X', cokeBrick())
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        String[] firstAisle = new String[] { "AXX", "XXX", "BXX" };
        String[] repeatableAisle = new String[] { "XXX", "X#X", "XXX" };
        String[] lastAisle = new String[] { "YXX", "XXX", "BXC" };

        MultiblockShapeInfo.Builder baseBuilder = MultiblockShapeInfo.builder(LEFT, DOWN, FRONT)
                .where('Y', TkTechMetaTileEntities.FLUID_PRIMITIVE_BLAST_FURNACE, EnumFacing.SOUTH)
                .where('A', TkTechMetaTileEntities.BRICK_FLUID_HATCH[1], EnumFacing.DOWN)
                .where('C', TkTechMetaTileEntities.BRICK_FLUID_HATCH[0], EnumFacing.UP)
                .where('B', TkTechMetaTileEntities.BRICK_ITEM_BUS[0], EnumFacing.SOUTH)
                .where('X', MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.COKE_BRICKS))
                .where('#', Blocks.AIR.getDefaultState());

        return MultiblockShapeInfoHelper.generateMultiblockShapeInfos(baseBuilder, getMaxSize(), firstAisle,
                repeatableAisle, lastAisle);
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
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("size", getSize());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        setSize(data.getInteger("size"));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(getSize());
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        setSize(buf.readInt());
    }
}
