package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetricPrefix {

    none(' ', 0),
    femto('f', -15),
    pico('p', -12),
    nano('n', -9),
    micro('µ', -6),
    milli('m', -3),
    kilo('k', 3),
    mega('M', 6),
    giga('G', 9),
    tera('T', 12),
    peta('P', 15),
    exa('E', 18);

    final char prefix;
    final int exponent;

    public int getNextExponent() {
        return this.getExponent() + 3;
    }

    public int getPreviousExponent() {
        return this.getExponent() - 3;
    }

    public boolean isNone() {
        return this == none;
    }

    @Override
    public String toString() {
        return this == none ? "" : String.valueOf(this.prefix);
    }
}
