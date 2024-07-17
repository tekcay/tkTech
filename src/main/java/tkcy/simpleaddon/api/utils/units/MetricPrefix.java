package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetricPrefix {

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

    @Override
    public String toString() {
        return String.valueOf(this.prefix);
    }
}
