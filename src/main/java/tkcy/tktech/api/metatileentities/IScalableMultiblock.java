package tkcy.tktech.api.metatileentities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

import org.jetbrains.annotations.NotNull;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;

import tkcy.tktech.api.utils.MultiblockShapeInfoHelper;

public interface IScalableMultiblock {

    int getSize();

    int getMaxSize();

    void setSize(int size);

    MultiblockControllerBase multiblock();

    @NotNull
    BlockPattern createStructurePattern(int size);

    default void setSizeOnToolClick(EntityPlayer playerIn) {
        if (playerIn.isSneaking()) {
            setSize(Math.max(0, getSize() - 1));
        } else setSize(Math.min(getSize() + 1, getMaxSize()));
        playerIn.sendMessage(new TextComponentString("Size : " + getSize()));
    }

    default List<MultiblockShapeInfo> getMatchingShapes(BlockPattern structurePattern) {
        List<MultiblockShapeInfo> matchingShapes = new ArrayList<>();
        for (int sizee = 0; sizee <= getMaxSize(); sizee++) {
            structurePattern = createStructurePattern(sizee);
            int[][] aisleRepetitions = structurePattern.aisleRepetitions;
            matchingShapes.addAll(MultiblockShapeInfoHelper.repetitionDFS(new ArrayList<>(), aisleRepetitions,
                    new Stack<>(), structurePattern));
        }
        return matchingShapes;
    }

    default void addInformation(@NotNull List<String> tooltip) {
        tooltip.add(I18n.format("tktech.machine.scale_multi.file_click.1"));
        tooltip.add(I18n.format("tktech.machine.scale_multi.file_click.2"));
    }
}
