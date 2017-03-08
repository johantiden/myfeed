package se.johantiden.myfeed.util;

import java.util.Collection;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class JCollections {

    private JCollections() {
    }

    public static <T, R> List<R> flatMap(Collection<T> list, Function<T, Collection<R>> children) {
        return list.stream()
                .flatMap(e -> children.apply(e).stream())
                .collect(Collectors.toList());

    }
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> mapper) {
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <E> List<E> filter(Collection<E> collection, Collection<Predicate<E>> filters) {
        return collection.stream()
                .filter(and(filters))
                .collect(Collectors.toList());
    }

    public static <E> Predicate<E> and(Collection<Predicate<E>> filters) {
        return reduce(filters, and(), e -> true);
    }

    private static <T> BinaryOperator<Predicate<T>> and() {
        return (a, b) -> e -> a.test(e) && b.test(e);
    }

    public static <E> E reduce(Collection<E> collection, BinaryOperator<E> reducer, E emptyObject) {
        return collection.stream()
                .reduce(reducer)
                .orElse(emptyObject);
    }

}
