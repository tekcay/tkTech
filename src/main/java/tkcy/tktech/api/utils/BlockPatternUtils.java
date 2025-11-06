package tkcy.tktech.api.utils;

import org.apache.commons.lang3.ArrayUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlockPatternUtils {

    /**
     * {@code "A#A" -> "A###A"}
     */
    public static String growSubAisle(String subAisle, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        int subAisleMiddleIndex = subAisle.length() / 2;

        for (int index = 0; index < subAisle.length(); index++) {
            char c = subAisle.charAt(index);
            stringBuilder.append(c);
            if (index == subAisleMiddleIndex) {
                StreamHelper.initIntStream(size * 2).forEach(i -> stringBuilder.append(c));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * {@code { "BBB", "AAA", "CCC" }}
     * <br>
     * ->
     * <br>
     * {@code { "BBB", "AAA", "AAA", "CCC" }}
     */
    public static String[] addSubAisles(String[] aisle, int size) {
        if (size == 0) return aisle;
        size--;
        int subAisleIndexToRepeat = aisle.length / 2;
        String subAisleToRepeat = aisle[subAisleIndexToRepeat];
        String[] result = ArrayUtils.add(aisle, subAisleIndexToRepeat, subAisleToRepeat);
        return addSubAisles(result, size);
    }

    /**
     * {@code { "BBB", "A#A", "CCC }}
     * <br>
     * ->
     * <br>
     * {@code { "BBB", "A#A", "A#A", "A#A", "CCC" }}
     */
    public static String[] addSubAisles2(String[] aisle, int size) {
        int subAisleIndexToRepeat = aisle.length / 2;
        int finalLength = aisle.length + size * 2;
        String toRepeat = aisle[subAisleIndexToRepeat];
        String[] result = new String[finalLength];

        for (int index = 0; index < subAisleIndexToRepeat; index++) {
            result[index] = aisle[index];
            result[result.length - index - 1] = aisle[aisle.length - index - 1];
        }

        int resultMiddleIndex = finalLength / 2;
        result[resultMiddleIndex] = toRepeat;

        for (int repeat = 0; repeat <= size; repeat++) {
            result[resultMiddleIndex + repeat] = toRepeat;
            result[resultMiddleIndex - repeat] = toRepeat;
        }

        return result;
    }

    /**
     * {@code { "BBB", "A#A", "CCC }}
     * <br>
     * ->
     * <br>
     * {@code { "BBBBB", "A###A", "A###A", "A###A", "CCCCC" }}
     */
    public static String[] growAisle(String[] aisle, int size) {
        String[] preResult = addSubAisles2(aisle, size);
        StreamHelper.initIntStream(preResult.length).forEach(i -> preResult[i] = growSubAisle(preResult[i], size));
        return preResult;
    }

    /**
     * {@code { "BBB", "A#A", "CCC }}
     * <br>
     * ->
     * <br>
     * {@code { "BBBBB", "A###A", "A###A", "A###A", "CCCCC" }}
     */
    public static String[] growAisle(int size, String... subAisles) {
        return growAisle(subAisles, size);
    }
}
