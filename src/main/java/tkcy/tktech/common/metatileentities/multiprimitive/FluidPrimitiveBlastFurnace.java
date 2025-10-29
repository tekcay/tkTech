package tkcy.tktech.common.metatileentities.multiprimitive;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import tkcy.tktech.api.capabilities.TkTechMultiblockAbilities;
import tkcy.tktech.api.machines.IOnSolderingIronClick;
import tkcy.tktech.api.machines.NoEnergyMultiController;
import tkcy.tktech.api.recipes.recipemaps.TkTechRecipeMaps;
import tkcy.tktech.api.utils.StreamHelper;

import static tkcy.tktech.api.predicates.TkTechPredicates.*;

public class FluidPrimitiveBlastFurnace extends NoEnergyMultiController implements IOnSolderingIronClick {
    
    private int size = 0;

    public FluidPrimitiveBlastFurnace(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, TkTechRecipeMaps.FLUID_PRIMITIVE_BLAST);
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
            size = Math.max(0, size - 1);
        } else size++;
        reinitializeStructurePattern();
        markDirty();
        playerIn.sendMessage(new TextComponentString("Size : " + size));
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new FluidPrimitiveBlastFurnace(metaTileEntityId);
    }

    private String growSubAisle(String subAisle, int size) {
        if (size == 0) return subAisle;
        char firstLetter = subAisle.charAt(0);
        char lastLetter = subAisle.charAt(subAisle.length() - 1);
        return growSubAisle(firstLetter + subAisle + lastLetter, size - 1);
    }

    private String[] growAisle2(String[] aisle, int size) {
        if (size == 0) return aisle;
        size--;
        int middleIndex = aisle.length / 2;
        String toRepeat = aisle[middleIndex];
        String[] result = ArrayUtils.add(aisle, middleIndex, toRepeat);
        return growAisle2(result, size);
    }

    private String[] growGrow(String[] aisle, int size) {
        String[] preResult = growAisle2(aisle, size);
        StreamHelper.initIntStream(preResult.length).forEach(i -> preResult[i] = growSubAisle(preResult[i], size));
        return preResult;
    }

    private String[] growGrow(int size, String... subAisles) {
        return growGrow(subAisles, size);
    }


    @Override
    protected @NotNull BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(growGrow(size, "AAA", "XXX", "BBB"))
                .aisle(growGrow(size, "AAA", "X#X", "BCB")).setRepeatable(Math.max(1, size * 2))
                .aisle(growGrow(size, "AYA", "XXX", "BBB"))
                .where('A', cokeBrick().or(brickFluidHatch(true, 1)))
                .where('B', cokeBrick().or(brickItemBus(false, 2)))
                .where('C', cokeBrick().or(brickFluidHatch(false, 1)))
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
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("size", size);
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        size = data.getInteger("size");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(size);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        size = buf.readInt();
    }
}
