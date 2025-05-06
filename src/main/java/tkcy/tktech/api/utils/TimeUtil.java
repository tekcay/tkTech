package tkcy.tktech.api.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtil {

    /**
     * @return the provided value in {@code ticks}.
     */
    public static int seconds(int seconds) {
        return seconds * 20;
    }

    /**
     * @return the provided value in {@code ticks}.
     */
    public static int minutes(int minutes) {
        return seconds(minutes) * 60;
    }

    /**
     * @return the provided value in {@code ticks}.
     */
    public static int hours(int hours) {
        return minutes(hours) * 60;
    }
}
