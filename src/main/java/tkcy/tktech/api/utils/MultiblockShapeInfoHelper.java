package tkcy.tktech.api.utils;

import static tkcy.tktech.api.utils.BlockPatternUtils.growGrow;

import java.util.ArrayList;
import java.util.List;

import gregtech.api.pattern.MultiblockShapeInfo;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MultiblockShapeInfoHelper {

    public static MultiblockShapeInfo.Builder addAisle(MultiblockShapeInfo.Builder builder, int size, String... aisle) {
        StreamHelper.initIntStream(1 + size * 2)
                .forEach(i -> builder.aisle(aisle));
        return builder;
    }

    public static List<MultiblockShapeInfo> generateMultiblockShapeInfos(MultiblockShapeInfo.Builder baseBuilder,
                                                                         int maxSize, String[] firstAisle,
                                                                         String[] repeatableAisle, String[] lastAisle) {
        List<MultiblockShapeInfo> shapeInfos = new ArrayList<>();

        for (int size = 0; size <= maxSize; size++) {
            MultiblockShapeInfo.Builder builder = baseBuilder.shallowCopy();
            builder.aisle(growGrow(size, firstAisle));
            addAisle(builder, size, growGrow(repeatableAisle, size))
                    .aisle(growGrow(size, lastAisle));
            shapeInfos.add(builder.build());
        }

        return shapeInfos;
    }
}
