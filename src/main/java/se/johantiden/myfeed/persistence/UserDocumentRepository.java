package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.util.Chrono;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class UserDocumentRepository implements Serializable {

    private static final long serialVersionUID = -5559800426834360178L;

    public static final transient Comparator<UserDocument> YOUNGEST_FIRST =
            Comparator.comparing((Function<UserDocument, Instant> & Serializable)UserDocument::getPublishDate).reversed();

    private final HashMap<Username, SortedSet<UserDocument>> map;

    public UserDocumentRepository() {
        map = new HashMap<>();
    }

    public final SortedSet<UserDocument> getAllKeys(Username user) {
        return getOrCreateSetForUser(user);
    }

    private SortedSet<UserDocument> getOrCreateSetForUser(Username userKey) {
        if (!map.containsKey(userKey)) {
            map.put(userKey, createNewUserSet());
        }
        return map.get(userKey);
    }

    public static TreeSet<UserDocument> createNewUserSet() {
        return new TreeSet<>(YOUNGEST_FIRST);
    }

    public final void put(UserDocument userDocument) {
        getOrCreateSetForUser(userDocument.getUserKey()).add(userDocument);
    }

    public final Optional<UserDocument> find(Username userKey, Key<UserDocument> userDocumentKey) {
        return getOrCreateSetForUser(userKey).stream()
                .filter((Predicate<UserDocument> & Serializable) ud -> ud.getKey().equals(userDocumentKey))
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

    public final void remove(UserDocument userDocument) {
        getOrCreateSetForUser(userDocument.getUserKey()).remove(userDocument);
    }

    @Override
    public final String toString() {
        int size = map.isEmpty() ? 0 : map.values().iterator().next().size();
        return "UserDocumentRepository{" +
                " users: " + map.size()+
                ", size:" + size +
                '}';
    }
}
