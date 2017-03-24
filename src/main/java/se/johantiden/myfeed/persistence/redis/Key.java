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
}
