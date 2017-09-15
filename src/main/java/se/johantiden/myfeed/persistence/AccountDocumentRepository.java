package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Set;

@Transactional
public interface AccountDocumentRepository extends CrudRepository<AccountDocument, Long> {

//    public static final transient Comparator<AccountDocument> YOUNGEST_FIRST =
//            Comparator.comparing((Function<AccountDocument, Instant> & Serializable)AccountDocument::getPublishDate).reversed();

    Set<Long> getReadyAccountDocumentIdsForAccount(@Param("accountId") long accountId);

    Set<AccountDocument> findAllRead();

//    public long purgeOlderThan(Duration duration) {
//
//        int removed = 0;
//        for (SortedSet<AccountDocument> set : db.accountDocuments.values()) {
//            int sizeBefore = set.size();
//            set.removeIf(olderThan(duration));
//            int sizeAfter = set.size();
//            removed += sizeBefore-sizeAfter;
//        }
//        return removed;
//    }

//    public static Predicate<? super AccountDocument> olderThan(Duration duration) {
//        return accountDocument -> {
//            boolean isOlder = Chrono.isOlderThan(duration, accountDocument.getPublishDate());
//            return isOlder;
//        };
//    }
}
