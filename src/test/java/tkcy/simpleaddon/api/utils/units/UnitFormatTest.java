package tkcy.simpleaddon.api.utils.units;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UnitFormatTest {

    @Test
    void buildUnit() {
        String str = UnitFormat.buildUnit(CommonUnits.bar);
        assertEquals("bar", str);
    }

    @Test
    void buildUnitNone() {
        String str = UnitFormat.buildUnit(CommonUnits.bar, MetricPrefix.none);
        assertEquals("bar", str);
    }

    @Test
    void buildUnitKilo() {
        String str = UnitFormat.buildUnit(CommonUnits.bar, MetricPrefix.kilo);
        assertEquals("kbar", str);
    }

    @Test
    void buildUnitEmptyKilo() {
        String str = UnitFormat.buildUnit(CommonUnits.empty, MetricPrefix.kilo);
        assertEquals("k", str);
    }

    @Test
    void buildUnitEmpty() {
        String str = UnitFormat.buildUnit(CommonUnits.empty);
        assertEquals("< >", '<' + str + '>');
    }

    @Test
    void buildUnitEmptyKiloStack() {
        String str = UnitFormat.buildUnit(CommonUnits.stack, MetricPrefix.kilo);
        assertEquals("k stacks", str);
    }

    @Test
    void testUnitPrefix() {
        assertTrue(tr(MetricPrefix.none, CommonUnits.empty));


    }

    private boolean tr(MetricPrefix metricPrefix, CommonUnits unit) {
        return !metricPrefix.isNone() && unit.isSpaceRequiredAfterMetrix();
    }
}
