package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
public interface UserDocumentRepository extends CrudRepository<UserDocument, Long> {

//    public static final transient Comparator<UserDocument> YOUNGEST_FIRST =
//            Comparator.comparing((Function<UserDocument, Instant> & Serializable)UserDocument::getPublishDate).reversed();

    Set<Long> getAllKeysForUser(long userId);

    Set<UserDocument> findAllWithRead(long userId, boolean read);

//    public long purgeOlderThan(Duration duration) {
//
//        int removed = 0;
//        for (SortedSet<UserDocument> set : db.userDocuments.values()) {
//            int sizeBefore = set.size();
//            set.removeIf(olderThan(duration));
//            int sizeAfter = set.size();
//            removed += sizeBefore-sizeAfter;
//        }
//        return removed;
//    }

//    public static Predicate<? super UserDocument> olderThan(Duration duration) {
//        return userDocument -> {
//            boolean isOlder = Chrono.isOlderThan(duration, userDocument.getPublishDate());
//            return isOlder;
//        };
//    }
}
