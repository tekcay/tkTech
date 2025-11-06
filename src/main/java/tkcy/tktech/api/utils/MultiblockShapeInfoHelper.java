package tkcy.tktech.api.utils;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MultiblockShapeInfoHelper {

    /**
     * Same as {@link MultiblockControllerBase#repetitionDFS(List, int[][], Stack)}
     */
    public static List<MultiblockShapeInfo> repetitionDFS(List<MultiblockShapeInfo> pages, int[][] aisleRepetitions,
                                                          Stack<Integer> repetitionStack,
                                                          BlockPattern structurePattern) {
        if (repetitionStack.size() == aisleRepetitions.length) {
            int[] repetition = new int[repetitionStack.size()];
            for (int i = 0; i < repetitionStack.size(); i++) {
                repetition[i] = repetitionStack.get(i);
            }
            pages.add(new MultiblockShapeInfo(Objects.requireNonNull(structurePattern).getPreview(repetition)));
        } else {
            for (int i = aisleRepetitions[repetitionStack.size()][0]; i <=
                    aisleRepetitions[repetitionStack.size()][1]; i++) {
                repetitionStack.push(i);
                repetitionDFS(pages, aisleRepetitions, repetitionStack, structurePattern);
                repetitionStack.pop();
            }
        }
        return pages;
    }
}
