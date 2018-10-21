package se.johantiden.myfeed.persistence;

import com.google.common.collect.ImmutableSet;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseRepository<T extends BaseEntity<T>> {

    private static final Comparator<BaseEntity<?>> COMPARATOR = Comparator.comparing(BaseEntity::getId);

    private final TreeSet<T> data = new TreeSet<>(COMPARATOR);
    private final AtomicLong sequence = new AtomicLong(0L);

    @Nonnull
    public T save(T t) {
        if (t.hasId()) {
            T existing = findOne(t.getId());
            return merge(t, existing);
        } else {
            return add(t);
        }
    }

    @Nonnull
    private T add(T t) {
        long id = sequence.incrementAndGet();
        t.setId(id);
        t.setCreated(Instant.now());
        data.add(t);
        return t;
    }

    @Nonnull
    public T merge(T newItem, T oldItem) {
        verifyMergingHack(newItem, oldItem);
        return oldItem;
    }

    public void verifyMergingHack(T fromRepo, T other) {
        if (fromRepo != other) {
            throw new RuntimeException("The objects are not the same reference. Implement a custom merge!");
        }
    }

    @Nonnull
    public T findOne(long id) {
        return findOne(t -> t.getId() == id);
    }

    @Nonnull
    public T findOne(Predicate<T> filterById) {
        return tryFindOne(filterById)
                .orElseThrow(() -> new IllegalStateException("Could not find element"));
    }

    public Optional<T> tryFindOne(long id) {
        return tryFindOne(t -> t.getId() == id);
    }

    protected Optional<T> tryFindOne(Predicate<T>... filters) {

        Set<T> founds = find(filters);

        if (founds.size() > 1) {
            throw new IllegalStateException("findOne must return exactly one or zero results!");
        }

        if (founds.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(founds.iterator().next());
        }
    }

    protected Set<T> find(Predicate<T>... filters) {
        Stream<T> stream = findStream(filters);

        return stream.collect(Collectors.toSet());
    }

    private Stream<T> findStream(Predicate<T>[] filters) {
        Stream<T> stream = data.stream();

        for (Predicate<T> filter : filters) {
            stream = stream.filter(filter);
        }
        return stream;
    }

    protected <R> Set<R> find(Function<T, R> mapper, Predicate<T>... filters) {
        Stream<T> stream = findStream(filters);
        return stream
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public Set<T> findAll() {
        return ImmutableSet.copyOf(data);
    }

    public void delete(long id) {
        T t = findOne(id);
        data.remove(t);
    }
}
