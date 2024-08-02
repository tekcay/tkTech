package tkcy.simpleaddon.api.utils.units;

import static tkcy.simpleaddon.api.utils.number.IsBetweenUtils.isBetweenEndExclusive;
import static tkcy.simpleaddon.api.utils.number.IsBetweenUtils.isBetweenEndExclusiveExponents;
import static tkcy.simpleaddon.api.utils.units.UnitFormat.formatValueWithUnit;

import java.util.Arrays;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitsConversions {

    /**
     * Converts and formats to the closest size of order a numeric value with a unit.
     * 
     * @param value the raw value to convert.
     * @param unit  a standard unit without a metric prefix. See {@link CommonUnits}.
     * @return a {@link String}
     *         <br>
     *         <br>
     *         {@code Use example:}
     *         <br>
     *         <br>
     *         {@code (0.04,} {@link CommonUnits#joule}) -> "40.0 mJ"
     *         <br>
     *         {@code (120100,} {@link CommonUnits#bar}) -> "120.1 kbar"
     */
    public static String convertAndFormatToSizeOfOrder(CommonUnits unit, double value) {
        return Arrays.stream(MetricPrefix.values())
                .filter(metricPrefix -> !isBetweenEndExclusive(1, 1000, value))
                .filter(metricPrefix -> isBetweenEndExclusiveExponents(
                        metricPrefix.getExponent(), metricPrefix.getExponent() + 3, value))
                .map(metricPrefix -> formatValueWithUnit(
                        value / Math.pow(10, metricPrefix.getExponent()),
                        UnitFormat.buildUnit(metricPrefix, unit)))
                .findAny()
                .orElse(formatValueWithUnit(value, unit));
    }

    public static String convertAndFormatToSizeOfOrder(CommonUnits unit, int value) {
        return Arrays.stream(MetricPrefix.values())
                .filter(metricPrefix -> !isBetweenEndExclusive(1, 1000, value))
                .filter(metricPrefix -> isBetweenEndExclusiveExponents(
                        metricPrefix.getExponent(), metricPrefix.getExponent() + 3, value))
                .map(metricPrefix -> formatValueWithUnit(
                        value / Math.pow(10, metricPrefix.getExponent()),
                        UnitFormat.buildUnit(metricPrefix, unit)))
                .findAny()
                .orElse(formatValueWithUnit(value, unit));
    }
}
