package tkcy.tktech.api.logic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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
    private BlockPos blockPosToPlace;
    private boolean isPipeStackLongDistance;

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

    private void setBlockPosToPlace() {
        blockPosToPlace = StreamHelper.initIntStream(1, pipePlacer.getMaxRange())
                .map(this::getBlockPos)
                .filter(blockPos -> WorldInteractionsHelper.canPlaceBlockInWorld(pipePlacer.getWorld(), blockPos))
                .findFirst()
                .orElse(null);
    }

    private void setBlockPosToRemove() {
        for (int offset = 1; offset < pipePlacer.getMaxRange(); offset++) {
            BlockPos blockPos = getBlockPos(offset);

            TileEntityPipeBase<?, ?> tileEntityPipeBase = getPipeEntity(blockPos);
            if (tileEntityPipeBase != null) {
                isPipeStackLongDistance = false;
                continue;
            }

            BlockLongDistancePipe blockLongDistancePipe = getPipeBlock(blockPos);
            if (blockLongDistancePipe != null) {
                isPipeStackLongDistance = true;
                continue;
            }

            blockPosToPlace = getBlockPos(offset - 1);
            return;
        }
        blockPosToPlace = getBlockPos(pipePlacer.getMaxRange());
    }

    @Nullable
    private ItemStack getPipeStack() {
        return StreamHelper.initIntStream(pipePlacer.getImportItems().getSlots())
                .map(pipePlacer.getImportItems()::getStackInSlot)
                .filter(itemStack -> !itemStack.isEmpty())
                .filter(this::isPipeStack)
                .findFirst()
                .orElse(null);
    }

    private BlockPos getBlockPos(int offset) {
        return pipePlacer.getPos().offset(pipePlacer.getFrontFacing(), offset);
    }

    public boolean placePipe() {
        ItemStack pipeStack = getPipeStack();
        if (pipeStack == null) return false;

        setBlockPosToPlace();
        if (blockPosToPlace == null) return false;

        boolean didPlace = placePipe(pipeStack);
        if (didPlace) {
            pipeStack.shrink(1);

            EnumFacing blockingPipeFace = pipePlacer.getBlockingPipeFaceBehavior().getBlockingPipeFace(pipePlacer);
            if (blockingPipeFace != null && !isPipeStackLongDistance) {
                setBlockedFace(blockingPipeFace);
            }
            return true;
        } else return false;
    }

    private boolean placePipe(@NotNull ItemStack pipeStack) {
        ItemBlock pipeBlock = (ItemBlock) pipeStack.getItem();

        IBlockState blockState = pipeBlock.getBlock().getStateForPlacement(
                pipePlacer.getWorld(),
                blockPosToPlace,
                pipePlacer.getFrontFacing(),
                1.0f, 1.0f, 1.0f,
                pipeStack.getMetadata(),
                null,
                null);

        return pipeBlock.placeBlockAt(
                pipeStack,
                null,
                pipePlacer.getWorld(),
                blockPosToPlace,
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

    public boolean removePipe() {
        setBlockPosToRemove();
        if (blockPosToPlace == pipePlacer.getPos()) return false;

        ItemStack pipeStack = ItemStack.EMPTY;
        TileEntityPipeBase<?, ?> te = getPipeEntity();
        if (te != null) {
            pipeStack = getInWorldPipeStack(te);
        } else {
            BlockLongDistancePipe blockLongDistancePipe = getPipeBlock(blockPosToPlace);
            if (blockLongDistancePipe != null) {
                pipeStack = getInWorldPipeStack(blockLongDistancePipe);
            }
        }

        if (pipeStack == null) return false;

        if (GTTransferUtils.insertItem(pipePlacer.getImportItems(), pipeStack, true).isEmpty()) {
            GTTransferUtils.insertItem(pipePlacer.getImportItems(), pipeStack, false);
            pipePlacer.getWorld().destroyBlock(blockPosToPlace, false);
            return true;
        }
        return false;
    }

    private void setBlockedFace(EnumFacing blockingPipeFace) {
        TileEntityPipeBase<?, ?> tileEntityPipeBase = getPipeEntity();
        if (tileEntityPipeBase != null) {
            tileEntityPipeBase.setFaceBlocked(blockingPipeFace, true);
        }
    }

    @Nullable
    private BlockLongDistancePipe getPipeBlock(BlockPos blockPos) {
        Block block = pipePlacer.getWorld().getBlockState(blockPos).getBlock();
        if (block instanceof BlockLongDistancePipe blockLongDistancePipe) {
            return blockLongDistancePipe;
        } else return null;
    }

    @Nullable
    private TileEntityPipeBase<?, ?> getPipeEntity(BlockPos blockPos) {
        TileEntity tileEntity = pipePlacer.getWorld().getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityPipeBase<?, ?>tileEntityPipeBase) {
            return tileEntityPipeBase;
        } else return null;
    }

    @Nullable
    private TileEntityPipeBase<?, ?> getPipeEntity() {
        return getPipeEntity(blockPosToPlace);
    }
}
