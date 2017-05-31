package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.util.Chrono;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class UserDocumentRepository implements Serializable {

    private static final long serialVersionUID = -5559800426834360178L;

    public static final transient Comparator<UserDocument> YOUNGEST_FIRST =
            Comparator.comparing((Function<UserDocument, Instant> & Serializable)UserDocument::getPublishDate).reversed();

    @Autowired
    public DB db;

    public final SortedSet<UserDocument> getAllKeys(Username user) {
        return getOrCreateSetForUser(user);
    }

    private SortedSet<UserDocument> getOrCreateSetForUser(Username userKey) {
        if (!db.userDocuments.containsKey(userKey)) {
            db.userDocuments.put(userKey, createNewUserSet());
        }
        return db.userDocuments.get(userKey);
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
        for (SortedSet<UserDocument> set : db.userDocuments.values()) {
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
        int size = db.userDocuments.isEmpty() ? 0 : db.userDocuments.values().iterator().next().size();
        return "UserDocumentRepository{" +
                " users: " + db.userDocuments.size()+
                ", size:" + size +
                '}';
    }
}
