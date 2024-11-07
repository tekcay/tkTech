package tkcy.simpleaddon.api.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BooleanHelperTest {

    @Test
    public void doesAnyNotMatch() {
        assertTrue(BooleanHelper.doesAnyNotMatch(1, 2, 3, 4));
    }

    @Test
    public void doesAnyMatch() {
        assertFalse(BooleanHelper.doesAnyMatch(1, 2, 3, 4));
    }

    @Test
    public void ajlzbf() {
        assertTrue(127 / 64 == 1);
    }
}
