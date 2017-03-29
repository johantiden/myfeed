package se.johantiden.myfeed.util;

import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class JPredicates {
    public static <T> Predicate<T> and(Predicate<T> a, Predicate<T> b) {
        return t -> a.test(t) && b.test(t);
    }

    public static <T> BinaryOperator<Predicate<T>> and() {
        return JPredicates::and;
    }
}
