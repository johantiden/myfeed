package se.johantiden.myfeed.persistence;


import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class DocumentRepository extends BaseRepository<Document> {

    public Set<Document> findDocumentsNotParsedSubjects() {
        return find((Predicate<Document>)  d -> !d.isSubjectsParsed());
    }

    public Optional<Document> findOneByPageUrl(String pageUrl) {
        return tryFindOne(d -> d.getPageUrl().equals(pageUrl));
    }

    public Set<Long> getReadyDocumentIds() {
        return find(Document::getId, d -> !d.isRead(),
                Document::isSubjectsParsed
        );
    }

    public Set<Document> findAllRead() {
        return find((Predicate<Document>) Document::isRead);
    }
}
