package tkcy.tktech.api.utils;

import java.util.stream.IntStream;

public class MathsHelper {

    public static int max(int... ints) {
        return IntStream.of(ints)
                .max()
                .orElse(ints[0]);
    }

    public static int min(int... ints) {
        return IntStream.of(ints)
                .min()
                .orElse(ints[0]);
    }
}
