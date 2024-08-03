package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonUnits {

    empty("", true),
    stack("stacks", true),
    bar("bar"),
    meter("m"),
    liter("L"),
    kelvin("K"),
    joule("J"),
    watt("W"),
    ampere("A"),
    volt("V");

    private final String unit;
    private boolean spaceRequiredAfterMetrix;

    CommonUnits(String unit) {
        this.unit = unit;
    }

    public boolean isEmpty() {
        return this == empty;
    }

    @Override
    public String toString() {
        return String.valueOf(this.unit);
    }
}
