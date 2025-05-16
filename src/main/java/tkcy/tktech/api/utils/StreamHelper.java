package tkcy.tktech.api.utils;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamHelper {

    public static Stream<Integer> initIntStream(int max) {
        return initIntStream(0, max);
    }

    public static Stream<Integer> initIntStream(int min, int max) {
        return IntStream.range(0, max)
                .boxed();
    }
}
