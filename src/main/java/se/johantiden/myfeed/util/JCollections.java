package se.johantiden.myfeed.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class JCollections {

    private JCollections() {
    }

    public static <T, R> List<R> flatMap(Collection<T> list, Function<T, Collection<R>> children) {

        return list.stream().flatMap(e -> children.apply(e).stream()).collect(Collectors.toList());

    }
    public static <T, R> List<R> map(Collection<T> list, Function<T, R> mapper) {
        return list.stream().map(mapper).collect(Collectors.toList());
    }

}
