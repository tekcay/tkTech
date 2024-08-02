package tkcy.simpleaddon.api.utils.units;

import static org.junit.jupiter.api.Assertions.*;
import static tkcy.simpleaddon.api.utils.units.UnitFormat.buildUnit;
import static tkcy.simpleaddon.api.utils.units.UnitFormat.formatValueWithUnit;

import org.junit.jupiter.api.Test;

import gregtech.api.util.GTLog;

class UnitsConversionsTest {

    @Test
    void convertAndFormatToSizeOfOrderTest() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 10.0);
        GTLog.logger.info(toTest);
        assertEquals("10.0 bar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTest2() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 10);
        GTLog.logger.info(toTest);
        assertEquals("10 bar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestEmpty() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.empty, 10);
        GTLog.logger.info(toTest);
        assertEquals("10", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestK() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 1000);
        GTLog.logger.info(toTest);
        assertEquals("1.0 kbar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestM() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 1002000);
        GTLog.logger.info(toTest);
        assertEquals("1.0 Mbar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestMDot() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 1010000);
        GTLog.logger.info(toTest);
        assertEquals("1.0 Mbar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestMDotClose() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 1210000);
        GTLog.logger.info(toTest);
        assertEquals("1.2 Mbar", toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestKEmpty() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.empty, 1000);
        GTLog.logger.info(toTest);
        assertEquals("1.0 k", toTest);
    }

    @Test
    void formatValueWithUnitTest() {
        String toTest = formatValueWithUnit(10, CommonUnits.bar);
        GTLog.logger.info(toTest);
        assertEquals("10 bar", toTest);
    }

    @Test
    void formatValueWithUnitTestEmpty() {
        String toTest = formatValueWithUnit(10, CommonUnits.empty);
        GTLog.logger.info(toTest);
        assertEquals("10", toTest);
    }
}
