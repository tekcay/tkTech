package tkcy.tktech.api.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringsHelper {

    /**
     * "oneExample" -> "One Example".
     */
    public static String convertCamelToTitleCase(String string) {
        String worked = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(string), " ");
        return StringUtils.capitalize(worked);
    }

    /**
     * "ONE_EXAMPLE" -> "One Example".
     */
    public static String convertScreamingSnakeToTileCase(String string) {
        return WordUtils.capitalizeFully(string.replace('_', ' '));
    }
}
