package tkcy.simpleaddon.api.utils.units;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitFormat {

    public static String formatValueWithUnit(double value, String unit) {
        return String.format("%3.1f%s", value, unit);
    }

    public static String formatValueWithUnit(int value, String unit) {
        return String.format("%d%s", value, unit);
    }

    public static String formatValueWithUnit(double value, CommonUnits unit) {
        return String.format("%3.1f%s", value, buildUnit(unit));
    }

    public static String formatValueWithUnit(int value, CommonUnits unit) {
        return String.format("%d%s", value, buildUnit(unit));
    }

    public static String formatValueWithUnitAndPrefix(MetricPrefix prefix, CommonUnits unit, int value) {
        return String.format("%d%s", value, buildUnit(unit, prefix));
    }

    public static String formatValueWithUnitAndPrefix(MetricPrefix prefix, CommonUnits unit, double value) {
        return String.format("%3.1f%s", value, buildUnit(unit, prefix));
    }

    public static String buildUnit(CommonUnits unit) {
        return buildUnit(unit, MetricPrefix.none);
    }

    public static String buildUnit(CommonUnits unit, MetricPrefix metricPrefix) {
        String space = CommonUnits.empty.getUnit();

        if (!unit.isEmpty() && metricPrefix.isNone()) space = " ";
        if (unit.isSpaceRequiredAfterMetrix() && !metricPrefix.isNone()) space = " ";
        if (unit.isEmpty()) space = "";

        return new StringBuilder()
                .append(metricPrefix.isNone() ? "" : " ")
                .append(metricPrefix)
                .append(space)
                .append(unit)
                .toString();
    }
}
