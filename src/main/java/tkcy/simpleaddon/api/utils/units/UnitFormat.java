package tkcy.simpleaddon.api.utils.units;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitFormat {

    public static String formatValueWithUnit(double value, String unit) {
        return String.format("%3.1f %s", value, unit);
    }

    public static String formatValueWithUnit(int value, String unit) {
        return String.format("%d %s", value, unit);
    }

    public static String formatValueWithUnit(double value, CommonUnits unit) {
        return String.format("%3.1f %s", value, unit);
    }

    public static String formatValueWithUnit(int value, CommonUnits unit) {
        return unit == CommonUnits.empty ? String.format("%d", value) : String.format("%d %s", value, unit);
    }

    public static String formatValueWithUnit(int value, String unit, char metricPrefix) {
        return String.format("%d %c%s", value, metricPrefix, unit);
    }

    public static String formatValueWithUnit(double value, String unit, char metricPrefix) {
        return String.format("%3.1f %c%s", value, metricPrefix, unit);
    }

    public static String buildUnit(MetricPrefix metricPrefix, CommonUnits unit) {
        return new StringBuilder()
                .append(metricPrefix.getPrefix())
                .append(unit.getUnit())
                .toString();
    }
}
