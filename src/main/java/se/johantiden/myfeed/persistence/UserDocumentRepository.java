package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class UserDocumentRepository {

    public static final Comparator<UserDocument> YOUNGEST_FIRST =
            Comparator.comparing(UserDocument::getPublishDate).reversed();

    private final Map<Key<User>, SortedSet<UserDocument>> map = new HashMap<>();

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

    private static Function<Instant, Double> youngestFirstInstant() {
        return i -> (double) -i.toEpochMilli();
    }

    public long purgeOlderThan(Duration duration) {

        int removed = 0;
        for (SortedSet<UserDocument> set : map.values()) {
            int sizeBefore = set.size();
            set.removeIf(isOlderThan(duration));
            int sizeAfter = set.size();
            removed += sizeBefore-sizeAfter;
        }
        return removed;
    }

    private Predicate<? super UserDocument> isOlderThan(Duration duration) {
        return userDocument -> userDocument.getPublishDate().plus(duration).isAfter(Instant.now());
    }

    public void remove(UserDocument userDocument) {
        getOrCreateSetForUser(userDocument.getUserKey()).remove(userDocument);
    }
}
