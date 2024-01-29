package tkcy.simpleaddon.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CollectionHelper {

    @SafeVarargs
    public static <T> List<T> buildList(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    @SafeVarargs
    public static <T> List<T> buildList(List<T> tList, T... ts) {
        tList.addAll(buildList(ts));
        return tList;
    }

    public static <T> Map<T, T> buildMap(List<T> keys, List<T> values) throws IllegalArgumentException {
        if (keys.size() != values.size()) throw new IllegalArgumentException("Input lists size does not match !");
        return IntStream.range(0, keys.size())
                .boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }
}
