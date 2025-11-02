package tkcy.tktech.common.metatileentities.multiprimitive;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tkcy.tktech.api.utils.BlockPatternUtils.*;

import org.junit.jupiter.api.Test;

class FluidPrimitiveBlastFurnaceTest {

    @Test
    void testGrowSubAisle() {
        assertEquals("A###A", growSubAisle2("A#A", 1));
    }

    @Test
    void testGrowSubAisle2() {
        assertEquals("A#####A", growSubAisle2("A#A", 2));
    }

    @Test
    void testG1() {
        String[] st = new String[] { "BBB", "AAA", "CCC" };
        String[] st2 = new String[] { "BBB", "AAA", "AAA", "CCC" };
        assertArrayEquals(st2, growAisle(st, 1));
    }

    @Test
    void testG2() {
        String[] st = new String[] { "BBB", "AAA", "CCC" };
        String[] st2 = new String[] { "BBB", "AAA", "AAA", "AAA", "CCC" };
        assertArrayEquals(st2, growAisle(st, 2));
    }

    @Test
    void testG2b() {
        String[] st = new String[] { "BBB", "A#A", "CCC" };
        String[] st2 = new String[] { "BBB", "A#A", "A#A", "A#A", "CCC" };
        assertArrayEquals(st2, growAisle2(st, 1));
    }

    @Test
    void testGrowGrow() {
        String[] st = new String[] { "BBB", "A#A", "CCC" };
        String[] st2 = new String[] { "BBBBB", "A###A", "A###A", "A###A", "CCCCC" };
        assertArrayEquals(growGrow(st, 1), st2);

        //
        // String[] st3 = new String[] {"AAA", "XXX", "XXX", "BBB"};
        // String[] st4 = new String[] {"AAAAA", "XXXXX", "XXXXX", "XXXXX", "BBBBB"};
        // String[] st5 = new String[] {"AAAAAAA", "XXXXXXX", "XXXXXXX", "XXXXXXX", "XXXXXXX", "BBBBBBB"};
        // assertArrayEquals(growGrow(1, st3), st4);
        // assertArrayEquals(growGrow(2, st3), st5);
        //
    }
}
