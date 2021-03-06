package se.johantiden.myfeed.util;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class JPredicates {
    public static final Predicate<?> TRUE = o -> true;

    public static <T> Predicate<T> and(Predicate<T> a, Predicate<T> b) {
        return t -> a.test(t) && b.test(t);
    }

    public static <T> BinaryOperator<Predicate<T>> and() {
        return Predicate::and;
    }

    public static <T> Predicate<T> and(List<Predicate<T>> predicates) {
        return t -> predicates.stream().allMatch(p -> p.test(t));
    }

    public static <T> Predicate<T> not(Predicate<T> a) {
        return t -> !a.test(t);
    }

    public static <T> Predicate<T> or(List<Predicate<T>> predicates) {
        return t -> predicates.stream().anyMatch(p -> p.test(t));
    }

}
