package se.johantiden.myfeed.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

    public static <T, R> List<R> map(Function<T, R> mapper, Collection<T> list) {
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <E> E reduce(Collection<E> collection, BinaryOperator<E> reducer, E valueIfEmpty) {
        return collection.stream()
                .reduce(reducer)
                .orElse(valueIfEmpty);
    }

    public static <E> E getSingle(Collection<E> collection) {
        Objects.requireNonNull(collection);
        Preconditions.checkArgument(collection.size() == 1, "There must be 1 and only 1 element!");
        return collection.iterator().next();
    }
}
