package se.johantiden.myfeed.persistence.redis;

import java.util.Objects;

public class Key<T> {

    private final String key;

    public Key(String key) {
        this.key = Objects.requireNonNull(key);
    }

    @Override
    public String toString() {
        return key;
    }

    public static <T> Key<T> create(String fullKey) {
        return new Key<T>(fullKey);
    }

    @Override
    public boolean equals(Object o) {
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
    public int hashCode() {
        return key.hashCode();
    }
}
