package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.util.Chrono;

import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

public class UserDocumentRepository {

    public static final Comparator<UserDocument> YOUNGEST_FIRST =
            Comparator.comparing(UserDocument::getPublishDate).reversed();

    private final Map<Key<User>, SortedSet<UserDocument>> map;

    public UserDocumentRepository(Map<Key<User>, SortedSet<UserDocument>> map) {
        this.map = Objects.requireNonNull(map);
    }

    public SortedSet<UserDocument> getAllKeys(Key<User> user) {
        return getOrCreateSetForUser(user);
    }

    private SortedSet<UserDocument> getOrCreateSetForUser(Key<User> userKey) {
        if (!map.containsKey(userKey)) {
            map.put(userKey, new TreeSet<>(YOUNGEST_FIRST));
        }
        return map.get(userKey);
    }

    public void put(UserDocument userDocument) {
        getOrCreateSetForUser(userDocument.getUserKey()).add(userDocument);
    }

    public Optional<UserDocument> find(Key<User> userKey, Key<UserDocument> userDocumentKey) {
        return getOrCreateSetForUser(userKey).stream()
                .filter(ud -> ud.getKey().equals(userDocumentKey))
                .findAny();
    }

    public long purgeOlderThan(Duration duration) {

        int removed = 0;
        for (SortedSet<UserDocument> set : map.values()) {
            int sizeBefore = set.size();
            set.removeIf(olderThan(duration));
            int sizeAfter = set.size();
            removed += sizeBefore-sizeAfter;
        }
        return removed;
    }

    public static Predicate<? super UserDocument> olderThan(Duration duration) {
        return userDocument -> {
            boolean isOlder = Chrono.isOlderThan(duration, userDocument.getPublishDate());
            return isOlder;
        };
    }

    public void remove(UserDocument userDocument) {
        getOrCreateSetForUser(userDocument.getUserKey()).remove(userDocument);
    }

    public Map<Key<User>, SortedSet<UserDocument>> unwrapMap() {
        return map;
    }
}
