package tkcy.simpleaddon.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ListHelper {

    @SafeVarargs
    public static <T> List<T> buildList(T... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

    @SafeVarargs
    public static <T> List<T> buildList(List<T> tList, T... ts) {
        tList.addAll(buildList(ts));
        return tList;
    }
}
