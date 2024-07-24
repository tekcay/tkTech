package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonUnits {

    empty(' '),
    meter('m'),
    liter('L'),
    kelvin('L'),
    joule('J'),
    watt('W'),
    ampere('A'),
    volt('V');

    final char unit;

    @Override
    public String toString() {
        return String.valueOf(this.unit);
    }
}
