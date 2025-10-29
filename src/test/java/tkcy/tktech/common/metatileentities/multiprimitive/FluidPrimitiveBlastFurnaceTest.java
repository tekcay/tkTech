package tkcy.tktech.common.metatileentities.multiprimitive;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;
import tkcy.tktech.api.utils.StreamHelper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FluidPrimitiveBlastFurnaceTest {


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

    @Test
    void testGrow() {

        assertEquals("AAAAA", growSubAisle("AAA", 1));
    }

    @Test
    void testG1() {
        String[] st = new String[] {"BBB", "AAA", "CCC"};
        String[] st2 = new String[] {"BBB", "AAA", "AAA", "CCC"};
        assertArrayEquals(st2, growAisle2(st, 1));
    }
    @Test
    void testG2() {
        String[] st = new String[] {"BBB", "AAA", "CCC"};
        String[] st2 = new String[] {"BBB", "AAA", "AAA", "AAA", "CCC"};
        assertArrayEquals(st2, growAisle2(st, 2));
    }

    @Test
    void testGrowGrow() {
        String[] st = new String[] {"BBB", "AAA", "CCC"};
        String[] st2 = new String[] {"BBBBB", "AAAAA", "AAAAA", "CCCCC"};
        assertArrayEquals(growGrow(st, 1), st2);


        String[] st3 = new String[] {"AAA", "XXX", "XXX", "BBB"};
        String[] st4 = new String[] {"AAAAA", "XXXXX", "XXXXX", "XXXXX", "BBBBB"};
        String[] st5 = new String[] {"AAAAAAA", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "BBBBBBB"};
        assertArrayEquals(growGrow(1, st3), st4);
        assertArrayEquals(growGrow(2, st3), st5);
    }

}
