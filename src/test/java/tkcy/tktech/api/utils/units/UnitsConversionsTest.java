package tkcy.tktech.api.utils.units;

import static org.junit.jupiter.api.Assertions.*;
import static tkcy.tktech.api.utils.units.UnitFormat.formatValueWithUnit;
import static tkcy.tktech.api.utils.units.UnitFormat.formatValueWithUnitAndPrefix;

import org.junit.jupiter.api.Test;

import gregtech.api.util.GTLog;

class UnitsConversionsTest {

    private static final String content = " of trucs";

    @Test
    void convertAndFormatToSizeOfOrderTest() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 10.0) + content;
        GTLog.logger.info(toTest);
        assertEquals("10.0 bar" + content, toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTest2() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.bar, 10);
        GTLog.logger.info(toTest);
        assertEquals("10 bar", toTest);
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
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.empty, 1000) + content;
        GTLog.logger.info(toTest);
        assertEquals("1.0 k" + content, toTest);
    }

    @Test
    void convertAndFormatToSizeOfOrderTestEmpty() {
        String toTest = UnitsConversions.convertAndFormatToSizeOfOrder(CommonUnits.empty, 100) + content;
        GTLog.logger.info(toTest);
        assertEquals("100" + content, toTest);
    }

    @Test
    void formatValueWithUnitTest() {
        String toTest = formatValueWithUnit(10, CommonUnits.bar) + content;
        GTLog.logger.info(toTest);
        assertEquals("10 bar" + content, toTest);
    }

    @Test
    void formatValueWithUnitKTest() {
        String toTest = formatValueWithUnitAndPrefix(MetricPrefix.kilo, CommonUnits.bar, 10) + content;
        GTLog.logger.info(toTest);
        assertEquals("10 kbar" + content, toTest);
    }

    @Test
    void formatValueWithUnitTestEmpty() {
        String toTest = formatValueWithUnit(10, CommonUnits.empty) + content;
        GTLog.logger.info(toTest);
        assertEquals("10" + content, toTest);
    }
}
