package tkcy.tktech.api.utils;

import java.util.Arrays;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BooleanHelper {

    @SafeVarargs
    public static <T> boolean doesAnyNotMatch(T base, T... toCompareWith) {
        return Arrays.stream(toCompareWith)
                .anyMatch(toCompare -> toCompare != base);
    }

    @SafeVarargs
    public static <T> boolean doesAnyMatch(T base, T... toCompareWith) {
        return Arrays.stream(toCompareWith)
                .anyMatch(toCompare -> toCompare == base);
    }

    @SafeVarargs
    public static <T> boolean doesAnyMatch(@Nullable T base, Predicate<T> predicate, T... toCompareWith) {
        return base == null || Arrays
                .stream(toCompareWith)
                .anyMatch(t -> predicate.test(base));
    }

    @SafeVarargs
    public static <T> boolean doesAnyMatch(Predicate<T> predicate, T... toCompareWith) {
        return Arrays.stream(toCompareWith)
                .anyMatch(predicate);
    }

    public static <T> boolean allMatch(T base, T... toCompareWith) {
        return Arrays.stream(toCompareWith)
                .allMatch(toCompare -> toCompare == base);
    }
}
