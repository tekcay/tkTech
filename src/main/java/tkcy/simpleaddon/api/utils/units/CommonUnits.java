package tkcy.simpleaddon.api.utils.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum CommonUnits {

    meter('m'),
    liter('L'),
    kelvin('L'),
    joule('J'),
    watt('W'),
    ampere('A'),
    volt('V');

    final char unit;
}
