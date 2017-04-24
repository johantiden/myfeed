package se.johantiden.myfeed.persistence.redis;

import java.io.Serializable;
import java.util.Objects;

public class Key<T> implements Serializable {

    private static final long serialVersionUID = -8996985495464136436L;

    private final String key;

    public Key(String key) {
        this.key = Objects.requireNonNull(key);
    }

    @Override
    public final String toString() {
        return key;
    }

    public static <T> Key<T> create(String fullKey) {
        return new Key<>(fullKey);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Key<?> key1 = (Key<?>) o;

        return key.equals(key1.key);

    }

    @Override
    public final int hashCode() {
        return key.hashCode();
    }
}
