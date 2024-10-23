package tkcy.simpleaddon.api.recipes.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tkcy.simpleaddon.api.utils.units.CommonUnits;

@Getter
@AllArgsConstructor
public enum RecipePropertiesKeys {

    HEAT_INPUT(CommonUnits.joule.getUnit()),
    HEAT_OUTPUT(CommonUnits.joule.getUnit()),
    TEMPERATURE(CommonUnits.kelvin.getUnit());

    private final String unit;
}
