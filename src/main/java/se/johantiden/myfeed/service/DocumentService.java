package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.List;
import java.util.Optional;

public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private DocumentRepository documentRepository;

    public void resetSubjects() {
//        Objects.requireNonNull(documentRepository);
//
//        documentRepository.findAll().forEach(d -> {
//            d.getSubjects().clear();
//            d.getTabs().clear();
//            d.setTabsParsed(false);
//            d.setSubjectsParsed(false);
//        });
        log.warn("resetSubjects NOT IMPLEMENTED");
    }

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        if (document.getId() != null) {
            Optional<Document> optional = Optional.ofNullable(documentRepository.findOne(document.getId()));
            if (optional.isPresent()) {
                merge(optional.get(), document);
            }
        }
        documentRepository.save(document);
    }

    private void merge(Document existing, Document newDocument) {
        documentRepository.save(newDocument);
    }

    public boolean hasDocument(long documentId) {
        return find(documentId).isPresent();
    }

    public Optional<Document> find(long documentId) {
        return Optional.ofNullable(documentRepository.findOne(documentId));
    }

    public List<Document> findDocumentsNotParsedSubjects() {
        return documentRepository.findDocumentsNotParsedSubjects();
    }

    public List<Document> findDocumentsNotParsedTabs() {
        return documentRepository.findDocumentsNotParsedTabs();
    }


//    public long purgeOlderThan(Duration duration) {
//
//        List<Map.Entry<Key<Document>, Document>> toBeRemoved = db.documents.entrySet().stream()
//                                                               .filter(isOlderThan(duration))
//                                                               .collect(Collectors.toList());
//
//        toBeRemoved.forEach(e -> db.documents.remove(e.getKey()));
//
//        return toBeRemoved.size();
//    }

//    private static Predicate<? super Map.Entry<Key<Document>, Document>> isOlderThan(Duration duration) {
//        return e -> {
//            Instant publishDate = e.getValue().getPublishDate();
//            return Chrono.isOlderThan(duration, publishDate);
//        };
//    }
}
