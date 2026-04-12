package tkcy.tktech.api.logic.pipeplacer;

import java.util.Objects;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import gregtech.api.pipenet.block.ItemBlockPipe;
import gregtech.api.pipenet.block.material.BlockMaterialPipe;
import gregtech.api.pipenet.block.material.IMaterialPipeTile;
import gregtech.api.pipenet.longdist.BlockLongDistancePipe;
import gregtech.api.pipenet.tile.TileEntityPipeBase;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTTransferUtils;

import tkcy.tktech.api.utils.StreamHelper;
import tkcy.tktech.api.utils.WorldInteractionsHelper;
import tkcy.tktech.common.metatileentities.electric.MTePipePlacer;

public class PipePlacerLogic {

    private final MTePipePlacer pipePlacer;
    private BlockPos workBlockPos;
    private boolean isPipeStackLongDistance;
    private ItemStack pipeStack;
    private TileEntityPipeBase<?, ?> pipeEntityAtBlockPos;

    public PipePlacerLogic(MTePipePlacer pipePlacer) {
        this.pipePlacer = pipePlacer;
    }

    private boolean isPipeStack(@NotNull ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBlock itemBlock) {
            if (itemBlock instanceof ItemBlockPipe<?, ?>) {
                isPipeStackLongDistance = false;
                return true;
            }
            if (itemBlock.getBlock() instanceof BlockLongDistancePipe) {
                isPipeStackLongDistance = true;
                return true;
            }
        }
        return false;
    }

    public void setBlockPosToPlace() {
        workBlockPos = StreamHelper.initIntStream(1, pipePlacer.getMaxRange())
                .map(this::getBlockPos)
                .filter(blockPos -> WorldInteractionsHelper.canPlaceBlockInWorld(pipePlacer.getWorld(), blockPos))
                .findFirst()
                .orElse(null);
    }

    private void setBlockPosToRemove() {
        for (int offset = 1; offset < pipePlacer.getMaxRange(); offset++) {
            BlockPos blockPos = getBlockPos(offset);

            if (getPipeEntity(blockPos) != null) {
                isPipeStackLongDistance = false;
                continue;
            }

            BlockLongDistancePipe blockLongDistancePipe = getLongDistancePipeBlock(blockPos);
            if (blockLongDistancePipe != null) {
                isPipeStackLongDistance = true;
                continue;
            }

            workBlockPos = getBlockPos(offset - 1);
            return;
        }
        workBlockPos = getBlockPos(pipePlacer.getMaxRange());
    }

    private void setPipeStackFromInventory() {
        pipeStack = StreamHelper.initIntStream(pipePlacer.getImportItems().getSlots())
                .map(pipePlacer.getImportItems()::getStackInSlot)
                .filter(itemStack -> !itemStack.isEmpty())
                .filter(this::isPipeStack)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private FluidStack getPaintingFluidStack() {
        // Do it with recipeMap ?
        FluidStack fluidStack = pipePlacer.getImportFluids().drain(Integer.MAX_VALUE, false);
        if (fluidStack == null) return null;
        if (fluidStack.getUnlocalizedName().contains("dye")) {
            return fluidStack;
        } else return null;
    }

    private BlockPos getBlockPos(int offset) {
        return pipePlacer.getPos().offset(pipePlacer.getFrontFacing(), offset);
    }

    private boolean placePipe(@NotNull ItemStack pipeStack) {
        ItemBlock pipeBlock = (ItemBlock) pipeStack.getItem();

        IBlockState blockState = pipeBlock.getBlock().getStateForPlacement(
                pipePlacer.getWorld(),
                workBlockPos,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                pipeStack.getMetadata(),
                null,
                null);

        return pipeBlock.placeBlockAt(
                pipeStack,
                null,
                pipePlacer.getWorld(),
                workBlockPos,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                blockState);
    }

    @Nullable
    private ItemStack getInWorldPipeStack(@NotNull TileEntityPipeBase<?, ?> te) {
        if (te instanceof IMaterialPipeTile<?, ?>materialPipeTile) {
            Material material = materialPipeTile.getPipeMaterial();
            if (te.getPipeBlock() instanceof BlockMaterialPipe<?, ?, ?>materialPipeBase) {
                return materialPipeBase.getItem(material);
            }
        }
        return null;
    }

    private ItemStack getInWorldPipeStack(@NotNull BlockLongDistancePipe blockLongDistancePipe) {
        return Item.getItemFromBlock(blockLongDistancePipe).getDefaultInstance();
    }

    public boolean tryPlacePipe() {
        setPipeStackFromInventory();
        if (pipeStack == null) return false;

        setBlockPosToPlace();
        if (workBlockPos == null) return false;

        if (placePipe(pipeStack)) {
            pipeStack.shrink(1);
            setPipeEntity();
            return true;
        } else return false;
    }

    public boolean tryRemovePipe() {
        setBlockPosToRemove();
        if (workBlockPos == pipePlacer.getPos()) return false;

        ItemStack pipeStack = ItemStack.EMPTY;
        setPipeEntity();
        if (pipeEntityAtBlockPos != null) {
            pipeStack = getInWorldPipeStack(pipeEntityAtBlockPos);
        } else {
            BlockLongDistancePipe blockLongDistancePipe = getLongDistancePipeBlock(workBlockPos);
            if (blockLongDistancePipe != null) {
                pipeStack = getInWorldPipeStack(blockLongDistancePipe);
            }
        }

        if (pipeStack == null) return false;

        if (GTTransferUtils.insertItem(pipePlacer.getImportItems(), pipeStack, true).isEmpty()) {
            GTTransferUtils.insertItem(pipePlacer.getImportItems(), pipeStack, false);
            pipePlacer.getWorld().destroyBlock(workBlockPos, false);
            return true;
        }
        return false;
    }

    public boolean tryPaintPipe() {
        FluidStack paintingStack = getPaintingFluidStack();
        if (paintingStack == null) return false;

        int fluidColor = paintingStack.getFluid().getColor();

        pipeEntityAtBlockPos = getPipeToWorkOn(pipe -> pipe.getPaintingColor() != fluidColor);
        if (pipeEntityAtBlockPos == null) return false;

        pipeEntityAtBlockPos.setPaintingColor(fluidColor);
        pipePlacer.getImportFluids().drain(1, true);
        return true;
    }

    public boolean tryRemovePipePainting() {
        FluidStack fluidStack = pipePlacer.getImportFluids().drain(Integer.MAX_VALUE, false);
        if (fluidStack == null || !fluidStack.isFluidEqual(pipePlacer.getPaintingRemovalFluid())) {
            return false;
        }

        pipeEntityAtBlockPos = getPipeToWorkOn(pipe -> pipe.getPaintingColor() != pipe.getDefaultPaintingColor());
        if (pipeEntityAtBlockPos == null) return false;

        pipeEntityAtBlockPos.setPaintingColor(pipeEntityAtBlockPos.getDefaultPaintingColor());
        pipePlacer.getImportFluids().drain(1, true);
        return true;
    }

    public boolean tryBlockFacePipe() {
        if (pipePlacer.getBlockingFaceBehavior() == BlockingPipeFaceBehavior.REMOVAL) {

            for (int offset = 1; offset < pipePlacer.getMaxRange(); offset++) {
                BlockPos blockPos = getBlockPos(offset);
                TileEntityPipeBase<?, ?> pipe = getPipeEntity(blockPos);
                if (pipe == null) return false;
                for (EnumFacing facing : EnumFacing.VALUES) {
                    if (pipe.isFaceBlocked(facing)) {
                        pipe.setFaceBlocked(facing, false);
                        return true;
                    }
                }
            }
            return false;
        }
        EnumFacing blockingPipeFace = pipePlacer.getBlockingFaceBehavior().getBlockingPipeFace(pipePlacer);
        pipeEntityAtBlockPos = getPipeToWorkOn(pipe -> !pipe.isFaceBlocked(blockingPipeFace));
        if (pipeEntityAtBlockPos == null) return true;

        pipeEntityAtBlockPos.setFaceBlocked(blockingPipeFace.getOpposite(), false);
        pipeEntityAtBlockPos.setFaceBlocked(blockingPipeFace, true);
        return true;
    }

    @Nullable
    private TileEntityPipeBase<?, ?> getPipeToWorkOn(Predicate<TileEntityPipeBase<?, ?>> pipePredicate) {
        return StreamHelper.initIntStream(1, pipePlacer.getMaxRange())
                .map(this::getBlockPos)
                .map(this::getPipeEntity)
                .filter(Objects::nonNull)
                .filter(pipePredicate)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private BlockLongDistancePipe getLongDistancePipeBlock(BlockPos blockPos) {
        Block block = pipePlacer.getWorld().getBlockState(blockPos).getBlock();
        if (block instanceof BlockLongDistancePipe blockLongDistancePipe) {
            return blockLongDistancePipe;
        } else return null;
    }

    private void setPipeEntity() {
        pipeEntityAtBlockPos = getPipeEntity(workBlockPos);
    }

    @Nullable
    private TileEntityPipeBase<?, ?> getPipeEntity(BlockPos blockPos) {
        TileEntity tileEntity = pipePlacer.getWorld().getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityPipeBase<?, ?>tileEntityPipeBase) {
            return tileEntityPipeBase;
        } else return null;
    }

    public void reset() {
        workBlockPos = null;
        pipeStack = null;
        pipeEntityAtBlockPos = null;
        isPipeStackLongDistance = false;
    }
}
