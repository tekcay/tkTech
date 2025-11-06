package tkcy.tktech.common.metatileentities.multiprimitive;

import static gregtech.api.util.RelativeDirection.*;
import static tkcy.tktech.api.predicates.TkTechPredicates.*;
import static tkcy.tktech.api.utils.BlockPatternUtils.growGrow;

import java.util.*;

import net.minecraft.entity.player.EntityPlayer;
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

import codechicken.lib.raytracer.CuboidRayTraceResult;
import lombok.Getter;
import tkcy.tktech.api.capabilities.TkTechMultiblockAbilities;
import tkcy.tktech.api.machines.IOnSolderingIronClick;
import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.MultiblockShapeInfoHelper;

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

    protected @NotNull BlockPattern createStructurePattern(int size) {
        return FactoryBlockPattern.start()
                .aisle(growGrow(size, "AAA", "XXX", "BBB"))
                .aisle(growGrow(size, "AAA", "X#X", "BCB")).setRepeatable(Math.max(1, 1 + size * 2))
                .aisle(growGrow(size, "YAA", "XXX", "BBB"))
                .where('A', cokeBrick().or(brickFluidHatch(true, 1)))
                .where('B', cokeBrick().or(brickItemBus(false, 2)))
                .where('C', cokeBrick().or(brickFluidHatch(false, 1)))
                .where('X', cokeBrick())
                .where('#', air())
                .where('Y', selfPredicate())
                .build();
    }

    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return createStructurePattern(getSize());
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        List<MultiblockShapeInfo> matchingShapes = new ArrayList<>();

        for (int sizee = 0; sizee <= getMaxSize(); sizee++) {
            this.structurePattern = createStructurePattern(sizee);
            int[][] aisleRepetitions = this.structurePattern.aisleRepetitions;
            matchingShapes.addAll(MultiblockShapeInfoHelper.repetitionDFS(new ArrayList<>(), aisleRepetitions,
                    new Stack<>(), structurePattern));
        }
        return matchingShapes;
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
