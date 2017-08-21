package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface DocumentRepository extends CrudRepository<Document, Long> {
    List<Document> findDocumentsNotParsedSubjects();
    List<Document> findDocumentsNotParsedTabs();
    Optional<Document> findOneByPageUrl(String pageUrl);
}
