package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonUnits {

    empty(""),
    bar("bar"),
    meter("m"),
    liter("L"),
    kelvin("K"),
    joule("J"),
    watt("W"),
    ampere("A"),
    volt("V");

    private final String unit;

    @Override
    public String toString() {
        return String.valueOf(this.unit);
    }
}
